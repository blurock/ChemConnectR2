package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

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
	MaterialDataTable<ObservationValueRow> table;
	
	DatabaseObjectHierarchy hierarchy;
	ObservationMatrixValues values;
	
	public SpreadSheetBlockMatrix(StandardDatasetObjectHierarchyItem item) {
		hierarchy = item.getHierarchy();
		Window.alert("SpreadSheetBlockMatrix: " + hierarchy.getObject().toString());
		values = (ObservationMatrixValues) hierarchy.getObject(); 
		DatabaseObjectHierarchy titleshier = hierarchy.getSubObject(values.getObservationRowValueTitles());
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titleshier.getObject();
		Window.alert("SpreadSheetBlockMatrix: 1");
		Window.alert("SpreadSheetBlockMatrix: 1.1: " + titleshier.getObject());
		ArrayList<String> titlesS = titles.getParameterLabel();
		Window.alert("SpreadSheetBlockMatrix: 1.2: " + titlesS);
		int i = 0;
		for(String title: titlesS) {
			addColumn(i++,title);
		}
		Window.alert("SpreadSheetBlockMatrix: 2");
		table.setTitle("Block of Data: ");
		DatabaseObjectHierarchy rowvalueshier = hierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) rowvalueshier.getObject();
		HashSet<String> ids = multiple.getIds();
		ArrayList<ObservationValueRow> rows = new ArrayList<ObservationValueRow>();
		Window.alert("SpreadSheetBlockMatrix: 3");
		for(String id: ids) {
			DatabaseObjectHierarchy subhier = rowvalueshier.getSubObject(id);
			ObservationValueRow row = (ObservationValueRow) subhier.getObject();
			rows.add(row);
		}
		Window.alert("SpreadSheetBlockMatrix: 4");
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
