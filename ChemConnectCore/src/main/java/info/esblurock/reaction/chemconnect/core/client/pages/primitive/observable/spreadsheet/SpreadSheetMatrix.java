package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.MaterialInfiniteDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.data.infinite.InfiniteDataView;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

import javax.inject.Inject;

public class SpreadSheetMatrix extends Composite {

	private static SpreadSheetMatrixUiBinder uiBinder = GWT.create(SpreadSheetMatrixUiBinder.class);

	interface SpreadSheetMatrixUiBinder extends UiBinder<Widget, SpreadSheetMatrix> {
	}

	@UiField
	MaterialPanel panel;
	@UiField
	MaterialLink startcountlabel;
	@UiField
	MaterialLink startcount;
	@UiField
	MaterialLink showinglabel;
	@UiField
	MaterialLink showing;
	@UiField
	MaterialLink totalcountlabel;
	@UiField
	MaterialLink totalcount;
	@UiField
	MaterialPanel tablepanel;
	@UiField
	MaterialLink delete;

	MaterialInfiniteDataTable<ObservationValueRow> inftable;
	MaterialDataTable<ObservationValueRow> table;
	ArrayList<ObservationValueRow> matrix;
	String obstitle;
	String parent;
	int numbercolumns;
	boolean infinitetable = false;

	ListDataSource<ObservationValueRow> dataSource;

	public SpreadSheetMatrix() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Inject
	public SpreadSheetMatrix(String title, String parent, int numbercolumns, int totalcount) {
		initWidget(uiBinder.createAndBindUi(this));

		SpreadSheetServicesAsync spreadsheet = SpreadSheetServices.Util.getInstance();
		init();
		this.parent = parent;
		this.numbercolumns = numbercolumns;
		this.totalcount.setText(String.valueOf(totalcount));
		obstitle = title;

		if (infinitetable) {
			SpreadSheetDataSource source = new SpreadSheetDataSource(parent, totalcount, spreadsheet);
			inftable = new MaterialInfiniteDataTable<>(20, InfiniteDataView.DYNAMIC_VIEW, source);
			setUpGenericColumns(table);
			table.setUseCategories(false);
			tablepanel.add(table);
		} else {
			table = new MaterialDataTable<ObservationValueRow>();
			table.setTitle(obstitle);
			panel.add(table);
			//getSectionOfData();
			setUpGenericColumns(table);
		}
	}

	private void init() {
		startcount.setText("0");
		showing.setText("300");
		totalcount.setText("0");
		startcountlabel.setText("Start");
		showinglabel.setText("Show");
		totalcountlabel.setText("Total");
	}

	@UiHandler("delete")
	public void onDeleteClick(ClickEvent event) {
		deleteSpreadSheet();
	}
	
	public void deleteSpreadSheet() {
		String filename = table.getTableTitle().getText();
		Window.alert("Delete Spreadsheet: " + filename);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		DeleteObjectCallback callback = new DeleteObjectCallback(filename);
		async.deleteSpreadSheetTransaction(filename,callback);
		this.removeFromParent();		
	}
	
	public void getSectionOfData() {
		int startI = Integer.valueOf(startcount.getText());
		int numberI = Integer.valueOf(showing.getText());
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		SpreadSheetSectionCallback asyncallback = new SpreadSheetSectionCallback(this);
		async.getSpreadSheetRows(parent, startI, numberI, asyncallback);
	}

	public void setUpResultMatrix(ArrayList<ObservationValueRow> origmatrix) {
		matrix = new ArrayList<ObservationValueRow>(origmatrix);
		if (table.getTableTitle() != null) {
			table.getTableTitle().setText(obstitle);
		} else {
			table.setTitle(obstitle);
		}
		setNumberOfColumns();
		setUpGenericColumns(table);
		try {
			table.setVisibleRange(0, 500);
			dataSource = new ListDataSource<ObservationValueRow>(matrix);
			table.setRowData(0, matrix);
		} catch(Exception e) {
			Window.alert("setUpResultMatrix    " + e.toString());
		}
	}

	void setNumberOfColumns() {
		numbercolumns = 0;
		for(ObservationValueRow row : matrix) {
			if(row.getRow().size() > numbercolumns) {
				numbercolumns = row.getRow().size();
			}
		}
		Window.alert("setNumberOfColumns: " + numbercolumns);
	}
	
	void setUpGenericColumns(MaterialDataTable<ObservationValueRow> datatable) {
		for (int i = 0; i < numbercolumns; i++) {
			String name = "Col:" + i;
			addColumn(i, name, datatable);
		}

	}

	void addColumn(int columnnumber, String columnname, MaterialDataTable<ObservationValueRow> datatable) {
		int number = columnnumber;
		TextColumn<ObservationValueRow> cell = new TextColumn<ObservationValueRow>() {
			@Override
			public String getValue(ObservationValueRow object) {
				String ans = "---";
				ArrayList<String> lst = object.getRow();
				if (lst.size() > number) {
					ans = lst.get(number);
				}
				return ans;
			}
		};
		datatable.addColumn(cell, columnname);
	}
/*
	@Override
	protected void onAttach() {
		Window.alert("SpreadSheetMatrix:  onAttach");
		try {
			super.onAttach();
			table.getTableTitle().setText("Infinite Table");
			table.clearRowsAndCategories(true);
			table.getView().setLoadMask(false);
			Window.alert("SpreadSheetMatrix:  onAttach end");
		} catch (Exception ex) {
			Window.alert(ex.toString());
		}
	}
*/
	/*
	 * @UiHandler("table") void onRowClick(RowSelectEvent<SpreadSheetRow> event) {
	 * Window.alert(event.getModel().toString()); }
	 */

}
