package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetDataSource;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SpreadSheetBlockMatrix extends Composite {

	private static SpreadSheetBlockMatrixUiBinder uiBinder = GWT.create(SpreadSheetBlockMatrixUiBinder.class);
	

	interface SpreadSheetBlockMatrixUiBinder extends UiBinder<Widget, SpreadSheetBlockMatrix> {
	}
	
 
	public SpreadSheetBlockMatrix() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialPanel tablepanel;
	
	MaterialDataTable<ObservationValueRow> table;

	private MaterialDataPager<ObservationValueRow> pager;
	
	DatabaseObjectHierarchy hierarchy;
	ObservationMatrixValues values;
	ListDataSource<ObservationValueRow> dataSource;
	ArrayList<ObservationValueRow> matrix;
	int numbercolumns;
	
	public SpreadSheetBlockMatrix(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		hierarchy = item.getHierarchy();
		setupTableFromObservationMatrixValues(hierarchy);
	}
		
	public SpreadSheetBlockMatrix(DatabaseObjectHierarchy hierarchy) {
		initWidget(uiBinder.createAndBindUi(this));
		setupTableFromObservationsFromSpreadSheet(hierarchy);
	}
	private void setupTableFromObservationsFromSpreadSheet(DatabaseObjectHierarchy hierarchy) {
		ObservationsFromSpreadSheetFull sheet = (ObservationsFromSpreadSheetFull) hierarchy.getObject(); 
		DatabaseObjectHierarchy valueshierarchy = hierarchy.getSubObject(sheet.getObservationMatrixValues());
		setupTableFromObservationMatrixValues(valueshierarchy);
	}
	private void setupTableFromObservationMatrixValues(DatabaseObjectHierarchy valueshierarchy) {
		setupTableFromObservationMatrixValuesWithTitles(valueshierarchy,null);
	}
	
	public void setupTableFromObservationMatrixValuesWithTitles(DatabaseObjectHierarchy valueshierarchy, ObservationValueRowTitle titles) {
		tablepanel.clear();
		values = (ObservationMatrixValues) valueshierarchy.getObject(); 
		DatabaseObjectHierarchy rowvalueshier = valueshierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) rowvalueshier.getObject();
		table = new MaterialDataTable<ObservationValueRow>();
		matrix = new ArrayList<ObservationValueRow>();
		if(titles == null) {
			numbercolumns = Integer.valueOf(values.getNumberOfColumns());
			/*
			if(rowvalueshier.getSubobjects().size() > 0) {
				DatabaseObjectHierarchy subhier = rowvalueshier.getSubobjects().get(0);
				ObservationValueRow row = (ObservationValueRow) subhier.getObject();
				numbercolumns = row.getRow().size();
			}
			*/
			for (int i = 0; i < numbercolumns; i++) {
				String name = "Col:" + i;
				addColumn(i, name);
			}
		} else {
			ArrayList<String> coltitles = titles.getParameterLabel();
			int count = 0;
			for(String coltitle : coltitles) {
				addColumn(count++, coltitle);
			}
		}
		try {
			String parent = multiple.getIdentifier();
			int totalcount = rowvalueshier.getSubobjects().size();		
			SpreadSheetServicesAsync spreadsheet = SpreadSheetServices.Util.getInstance();
			SpreadSheetDataSource source = new SpreadSheetDataSource(parent, totalcount, spreadsheet);
			pager = new MaterialDataPager<>(table, source);
			pager.setLimitOptions(10, 20,40);
			table.setVisibleRange(0, 25);
			table.add(pager);
			table.setDataSource(source);
			tablepanel.add(table);
			tablepanel.add(pager);
		} catch(Exception e) {
			Window.alert("Problem in setting up Matrix:\n" + e.toString());
		}
	}
	void addColumn(int columnnumber, String columnname) {
		int number = columnnumber;
		TextColumn<ObservationValueRow> cell = new TextColumn<ObservationValueRow>() {
			@Override
			public String getValue(ObservationValueRow object) {
				String ans = "empty";
				if(object.getRow().size() > number) {
					ans = object.getRow().get(number);
				}
				return ans;
			}
			
		};
		table.addColumn(cell,columnname);
	}
}
