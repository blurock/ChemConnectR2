package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterSpecificationInformation;

public class ObservableSpecificationRow extends Composite implements HasText {

	private static ObservableSpecificationRowUiBinder uiBinder = GWT.create(ObservableSpecificationRowUiBinder.class);

	interface ObservableSpecificationRowUiBinder extends UiBinder<Widget, ObservableSpecificationRow> {
	}


	@UiField
	MaterialLink parameterLabel;
	@UiField
	MaterialLink unitclass;
	@UiField
	MaterialLink unit;
	@UiField
	MaterialSwitch toggleinfo;
	@UiField
	MaterialLink purpose;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialLink uncertaintyclass;
	@UiField
	MaterialRow extrainfo;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialRow info;
	@UiField
	MaterialTooltip identifiertip;
	
	String propertyType;
	boolean rowVisible;
	DatabaseObject obj;
	public ObservableSpecificationRow() {
		initWidget(uiBinder.createAndBindUi(this));
		PrimitiveParameterSpecificationInformation info = new PrimitiveParameterSpecificationInformation();
		obj = new DatabaseObject();
		fill(info);
	}
	
	public ObservableSpecificationRow(String label) {
		initWidget(uiBinder.createAndBindUi(this));
		PrimitiveParameterSpecificationInformation info = new PrimitiveParameterSpecificationInformation();
		obj = new DatabaseObject(info);
		fill(info);
		parameterLabel.setText(label);
	}
	
	
	public ObservableSpecificationRow(PrimitiveParameterSpecificationInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		obj = new DatabaseObject(info);
		fill(info);
	}
	
	void fill(PrimitiveDataStructureInformation info) {
		PrimitiveParameterSpecificationInformation paraminfo = (PrimitiveParameterSpecificationInformation) info;
		obj = new DatabaseObject(info);
		this.propertyType = paraminfo.getPropertyType();
		toppanel.setBackgroundColor(Color.GREY_LIGHTEN_1);
		identifiertip.setText(info.getIdentifier());
		if(paraminfo.getPropertyType() != null) {
			parameterLabel.setText(TextUtilities.removeNamespace(paraminfo.getPropertyType()));
		} else {
			parameterLabel.setText("Label");
		}
		if(paraminfo.getUnit() != null) {
			unit.setText(TextUtilities.removeNamespace(paraminfo.getUnit()));
		} else {
			unit.setText("Unit");
		}
		if(paraminfo.getPurpose() != null) {
			purpose.setText(TextUtilities.removeNamespace(paraminfo.getPurpose()));
		} else {
			purpose.setText("Purpose");
		}
		if(paraminfo.getConcept() != null) {
			concept.setText(TextUtilities.removeNamespace(paraminfo.getConcept()));
		} else {
			concept.setText("Concept");
		}
		if(paraminfo.getUnitclass() != null) {
			unitclass.setText(TextUtilities.removeNamespace(paraminfo.getUnitclass()));
		} else {
			unitclass.setText("Units");
		}
		if(paraminfo.getUncertaintyType() != null) {
			uncertaintyclass.setText(TextUtilities.removeNamespace(paraminfo.getUncertaintyType()));
		} else {
			uncertaintyclass.setText("Uncertainty");
		}
		
		extrainfo.setVisible(false);
		rowVisible = false;
	}
	@UiHandler("toggleinfo") 
	void onValueChange(ValueChangeEvent<Boolean> e) { 
		if(rowVisible) {
			rowVisible = false;
		} else {
			rowVisible = true;
		}
		extrainfo.setVisible(rowVisible);
		}


	public void setText(String text) {
		parameterLabel.setText(text);
	}

	public String getText() {
		return parameterLabel.getText();
	}
	public void setIdentifier(DatabaseObject obj) {
		this.obj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-" + parameterLabel.getText();
		this.obj.setIdentifier(id);
		identifiertip.setText(this.obj.getIdentifier());
	}

}
