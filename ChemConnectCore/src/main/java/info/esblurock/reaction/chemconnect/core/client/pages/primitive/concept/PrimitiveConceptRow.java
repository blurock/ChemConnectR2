package info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchyFromDefinition;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;

public class PrimitiveConceptRow extends Composite implements ChooseFromConceptHeirarchy {

	private static PrimitiveConceptRowUiBinder uiBinder = GWT.create(PrimitiveConceptRowUiBinder.class);

	interface PrimitiveConceptRowUiBinder extends UiBinder<Widget, PrimitiveConceptRow> {
	}

	@UiField
	MaterialLink label;
	@UiField
	MaterialLink purpose;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialTooltip purposetip;
	@UiField
	MaterialTooltip concepttip;
	@UiField
	MaterialIcon info;
	
	boolean conceptSet;
	String identifier;
	String typeWithNamespace;
	boolean chooseConcept;
	
	public PrimitiveConceptRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		purpose.setText("choose purpose");
		concept.setText("choose concept");
	}
	public PrimitiveConceptRow(DatabaseObject cobject) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		PurposeConceptPair conceptobj = (PurposeConceptPair) cobject;
		typeWithNamespace = conceptobj.getConcept();
		identifier = conceptobj.getIdentifier();
		purpose.setText(TextUtilities.removeNamespace(conceptobj.getPurpose()));
		concept.setText(TextUtilities.removeNamespace(conceptobj.getConcept()));
		tip.setText(identifier);
		conceptSet = true;
	}

	private void init() {
		label.setText("Pupose");
		label.setTextColor(Color.BLACK);
		purposetip.setText("Purpose");
		concepttip.setText("Concept");
		purpose.setTextColor(Color.BLACK);
		concept.setTextColor(Color.BLACK);
		conceptSet = false;
	}
	
	public void setConcept(String conceptText) {
		concept.setText(conceptText);
	}
	
	@UiHandler("concept")
	void onClick(ClickEvent e) {
			ArrayList<String> choices = new ArrayList<String>();
			choices.add("dataset:DataTypeConcept");
			ChooseFromConceptHierarchyFromDefinition choosedevice = new ChooseFromConceptHierarchyFromDefinition(choices,this);
			modalpanel.add(choosedevice);
			chooseConcept=true;
			choosedevice.open();
	}
	@UiHandler("purpose")
	void onClickPurpose(ClickEvent e) {
			ArrayList<String> choices = new ArrayList<String>();
			choices.add("dataset:PurposeKeyword");
			ChooseFromConceptHierarchyFromDefinition choosedevice = new ChooseFromConceptHierarchyFromDefinition(choices,this);
			modalpanel.add(choosedevice);
			chooseConcept=false;
			choosedevice.open();
	}
	@UiHandler("info")
	void onClickType(ClickEvent e) {
		MaterialToast.fireToast("ID: " + identifier);
	}
	
	
	@Override
	public void conceptChosen(String topconcept, String conceptText) {
		if(chooseConcept) {
			concept.setText(TextUtilities.removeNamespace(conceptText));
		} else {
			purpose.setText(TextUtilities.removeNamespace(conceptText));
		}
	}

	public String getPurpose() {
		return purpose.getText();
	}
	public String getConcept() {
		return concept.getText();
	}
	
	
	public String getTypeWithNamespace() {
		return typeWithNamespace;
	}

}
