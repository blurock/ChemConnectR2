package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.ibm.icu.impl.LocaleDisplayNamesImpl.DataTable;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.BaseRenderer;
import gwt.material.design.client.data.SortContext;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.Component;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.factory.RowComponentFactory;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.Table;
import gwt.material.design.client.ui.table.TableData;
import gwt.material.design.client.ui.table.TableHeader;
import gwt.material.design.client.ui.table.TableRow;
import gwt.material.design.client.ui.table.TableSubHeader;
import gwt.material.design.client.ui.table.cell.Column;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetCell;
import gwt.material.design.client.ui.table.cell.WidgetColumn;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;

public class SpreadSheetBlockMatrix extends Composite {

	private static SpreadSheetBlockMatrixUiBinder uiBinder = GWT.create(SpreadSheetBlockMatrixUiBinder.class);
	

	interface SpreadSheetBlockMatrixUiBinder extends UiBinder<Widget, SpreadSheetBlockMatrix> {
	}
	
 
	public SpreadSheetBlockMatrix() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDataTable<ArrayList<String>> table;
	
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
		
		TextColumn<ArrayList<String>> cell = new TextColumn<ArrayList<String>>() {

			@Override
			public String getValue(ArrayList<String> object) {
				String ans = "empty";
				if(object.size() > number) {
					ans = object.get(number);
				}
				return ans;
			}
			
		};
		Comparator<? super RowComponent<ArrayList<String>>> sortComparator = 
				new Comparator<RowComponent<ArrayList<String>>>() {

					@Override
					public int compare(RowComponent<ArrayList<String>> o1, RowComponent<ArrayList<String>> o2) {
						return 0;
					}

		};
		table.addColumn(cell,columnname);
	}
}
