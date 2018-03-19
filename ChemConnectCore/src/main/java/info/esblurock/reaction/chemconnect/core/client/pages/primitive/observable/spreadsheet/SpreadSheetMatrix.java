package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.data.events.RowSelectEvent;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

public class SpreadSheetMatrix extends Composite  {

	private static SpreadSheetMatrixUiBinder uiBinder = GWT.create(SpreadSheetMatrixUiBinder.class);

	interface SpreadSheetMatrixUiBinder extends UiBinder<Widget, SpreadSheetMatrix> {
	}

	public SpreadSheetMatrix() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDataTable<SpreadSheetRow> table;
	
	ArrayList<SpreadSheetRow> matrix;
	String obstitle;
	int maxcount;

	public SpreadSheetMatrix(String title, ArrayList<SpreadSheetRow> origmatrix) {
		initWidget(uiBinder.createAndBindUi(this));
		obstitle = title;
		setUpResultMatrix(origmatrix);
	}
	
	@UiHandler("table")
	void onRowClick(RowSelectEvent<SpreadSheetRow> event) {
		Window.alert(event.getModel().toString());
	}
	public void setUpResultMatrix(ArrayList<SpreadSheetRow> origmatrix) {
		matrix = new ArrayList<SpreadSheetRow>(origmatrix);
		maxcount = 0;
		for (SpreadSheetRow row : matrix) {
			ArrayList<String> lst = row.getRow();
			if (maxcount < lst.size()) {
				maxcount = lst.size();
			}
		}
		for (int i = 0; i < maxcount + 1; i++) {
			String name = "Col:" + i;
			addColumn(i, name);
		}
		table.setTextColor(Color.BLACK);
		table.setVisibleRange(0, matrix.size());
		table.setRowData(0, matrix);
		table.getView().refresh();
		if(table.getTableTitle() != null) {
			table.getTableTitle().setText(obstitle);
		} else {
			table.setTitle(obstitle);
		}
	}

	void addColumn(int columnnumber, String columnname) {
		int number = columnnumber;
		TextColumn<SpreadSheetRow> cell = new TextColumn<SpreadSheetRow>() {
			@Override
			public String getValue(SpreadSheetRow object) {
				String ans = "---";
				ArrayList<String> lst = object.getRow();
				if (lst.size() > number) {
					ans = lst.get(number);
				}
				return ans;
			}
		};
		table.addColumn(cell, columnname);
	}

}
