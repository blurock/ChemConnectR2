package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

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
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
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
		table = new MaterialDataTable<ObservationValueRow>();
		
		hierarchy = item.getHierarchy();
		values = (ObservationMatrixValues) hierarchy.getObject(); 
		DatabaseObjectHierarchy titleshier = hierarchy.getSubObject(values.getObservationRowValueTitles());
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titleshier.getObject();
		ArrayList<String> titlesS = titles.getParameterLabel();
		table.getTableTitle().setText("Block of Data: ");
		DatabaseObjectHierarchy rowvalueshier = hierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) rowvalueshier.getObject();
		matrix = new ArrayList<ObservationValueRow>();
		numbercolumns = 0;

		for(DatabaseObjectHierarchy subhier: rowvalueshier.getSubobjects()) {
			ObservationValueRow row = (ObservationValueRow) subhier.getObject();
			matrix.add(row);
			if(row.getRow().size() > numbercolumns) {
				numbercolumns = row.getRow().size();
			}
		}
		for (int i = 0; i < numbercolumns; i++) {
			String name = "Col:" + i;
			if(titlesS.size() > i) {
				name = titlesS.get(i);
			}
				addColumn(i, name);
		}
		
		Collections.sort(matrix, new Comparator<ObservationValueRow>() {
		    public int compare(ObservationValueRow lhs, ObservationValueRow rhs) {
		        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
		        return lhs.getRowNumber() - rhs.getRowNumber();
		    }
		});
		
		try {
			dataSource = new ListDataSource<ObservationValueRow>(matrix);
			pager = new MaterialDataPager<>(table, dataSource);
			pager.setLimitOptions(5, 10, 20,40);
			table.setVisibleRange(1, 30);
			table.add(pager);
			table.setDataSource(dataSource);
			//table.setRowData(0, matrix);
			tablepanel.add(table);
			tablepanel.add(pager);
		} catch(Exception e) {
			Window.alert("setUpResultMatrix    " + e.toString());
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
		/*
		Comparator<? super RowComponent<ArrayList<String>>> sortComparator = 
				new Comparator<RowComponent<ArrayList<String>>>() {

					@Override
					public int compare(RowComponent<ArrayList<String>> o1, RowComponent<ArrayList<String>> o2) {
						return 0;
					}

		};
		*/
		table.addColumn(cell,columnname);
	}
}
