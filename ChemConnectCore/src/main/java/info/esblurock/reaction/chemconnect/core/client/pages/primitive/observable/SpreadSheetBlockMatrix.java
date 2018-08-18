package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class SpreadSheetBlockMatrix extends Composite {

	private static SpreadSheetBlockMatrixUiBinder uiBinder = GWT.create(SpreadSheetBlockMatrixUiBinder.class);
	

	interface SpreadSheetBlockMatrixUiBinder extends UiBinder<Widget, SpreadSheetBlockMatrix> {
	}
	
 
	public SpreadSheetBlockMatrix() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDataTable<ObservationValueRow> table;
	
	ArrayList<String> row;
	
	PrimitiveObservationVauesWithSpecificationRow top;
	SpreadSheetBlockInformation block;
	public SpreadSheetBlockMatrix(SpreadSheetBlockInformation block, PrimitiveObservationVauesWithSpecificationRow top) {
		initWidget(uiBinder.createAndBindUi(this));
		this.top = top;
		this.block = block;
		init();
		fill();
	}
	void init() {
		
	}
	void fill() {
		for(int i=0;i<block.getMaxNumberOfColumns();i++) {
			String name = "Col:" + i;
			addColumn(i,name);
		}
		table.setTitle("Block of Data: ");
		table.setRowData(0, block.getRows());
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
