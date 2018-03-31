package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;

public class ColumnToParameterCorrespondenceRow extends Composite {

	private static ColumnToParameterCorrespondenceRowUiBinder uiBinder = GWT
			.create(ColumnToParameterCorrespondenceRowUiBinder.class);

	interface ColumnToParameterCorrespondenceRowUiBinder extends UiBinder<Widget, ColumnToParameterCorrespondenceRow> {
	}

	public ColumnToParameterCorrespondenceRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink parameter;
	@UiField
	MaterialLink button;
	@UiField
	MaterialDropDown column;
	
	AssignParameterToColumnPanel top;
	boolean errorParameter;
	int columnNumber;
	MaterialPanel modaltop;
	boolean error;
	ArrayList<String> columns;
	

	public ColumnToParameterCorrespondenceRow(String parameter, 
			int columnNumber, boolean errorParameter,
			ArrayList<String> columns, 
			boolean error, 
			AssignParameterToColumnPanel top,
			MaterialPanel modaltop) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.parameter.setText(parameter);
		this.columnNumber = columnNumber;
		this.errorParameter = errorParameter;
		this.columns = columns;
		this.error = error;
		this.top = top;
		this.modaltop = modaltop;
		String act = "dp-corr" + columnNumber;
		if(errorParameter) {
			act = "dp-corr" + columnNumber + "-error";
		}
		button.setActivates(act);
		column.setActivator(act);
		button.setText(act);
		for(String param : columns) {
			MaterialLink link = new MaterialLink(param);
			column.add(link);
		}		
	}

	public void init() {
		button.setText("Select Column");
	}
	@UiHandler("column")
	void onClick(SelectionEvent<Widget>  callback) {
		MaterialLink link = (MaterialLink) callback.getSelectedItem();
		String selected = link.getText();
		button.setText(selected);
	}
	
	public SpreadSheetTitleRowCorrespondence getCorrespondence(DatabaseObject obj, String parent) {
		DatabaseObject subobj = new DatabaseObject(obj);
		subobj.setIdentifier(obj.getIdentifier() + "-corr-" + parameter);
		SpreadSheetTitleRowCorrespondence corr = new SpreadSheetTitleRowCorrespondence(
				subobj, parent, 
				columnNumber,button.getText(),
				parameter.getText(),errorParameter);
		return corr;
	}

	
	
	public String getSelected() {
		return button.getText();
	}
	public String getParameter() {
		return button.getText();
	}

}
