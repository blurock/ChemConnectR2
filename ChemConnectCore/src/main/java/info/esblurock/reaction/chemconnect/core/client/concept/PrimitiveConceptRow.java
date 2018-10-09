package info.esblurock.reaction.chemconnect.core.client.concept;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchyFromDefinition;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class PrimitiveConceptRow extends Composite implements ChooseFromConceptHeirarchy {

	private static PrimitiveConceptRowUiBinder uiBinder = GWT.create(PrimitiveConceptRowUiBinder.class);

	interface PrimitiveConceptRowUiBinder extends UiBinder<Widget, PrimitiveConceptRow> {
	}

	//@UiField
	//MaterialLink label;
	@UiField
	MaterialLink purpose;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialPanel modalpanel;
	//@UiField
	//MaterialTooltip tip;
	@UiField
	MaterialTooltip purposetip;
	@UiField
	MaterialTooltip concepttip;
	
	boolean conceptSet;
	String identifier;
	String typeWithNamespace;
	boolean chooseConcept;
	
	PurposeConceptPair conceptobj;
	
	public PrimitiveConceptRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		purpose.setText("choose purpose");
		concept.setText("choose concept");
	}
	public PrimitiveConceptRow(DatabaseObject cobject) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		conceptobj = (PurposeConceptPair) cobject;
		typeWithNamespace = conceptobj.getConcept();
		identifier = conceptobj.getIdentifier();
		TextUtilities.setText(purpose,TextUtilities.removeNamespace(conceptobj.getPurpose()),  "Set purpose");
		TextUtilities.setText(concept,TextUtilities.removeNamespace(conceptobj.getConcept()),  "Set concept");
		conceptSet = true;
	}

	private void init() {
		purposetip.setText("Purpose");
		concepttip.setText("Concept");
		conceptSet = false;
	}
	
	public void setConcept(String conceptText) {
		concept.setText(conceptText);
	}
	
	@UiHandler("concept")
	void onClick(ClickEvent e) {
			ArrayList<String> choices = new ArrayList<String>();
			choices.add(MetaDataKeywords.dataTypeConcept);
			ChooseFromConceptHierarchyFromDefinition choosedevice = new ChooseFromConceptHierarchyFromDefinition(choices,this);
			modalpanel.add(choosedevice);
			chooseConcept=true;
			choosedevice.open();
	}
	@UiHandler("purpose")
	void onClickPurpose(ClickEvent e) {
			ArrayList<String> choices = new ArrayList<String>();
			choices.add(MetaDataKeywords.purposeKeyword);
			ChooseFromConceptHierarchyFromDefinition choosedevice = new ChooseFromConceptHierarchyFromDefinition(choices,this);
			modalpanel.add(choosedevice);
			chooseConcept=false;
			choosedevice.open();
	}
	@Override
	public void conceptChosen(String topconcept, String conceptText, ArrayList<String> path) {
		if(chooseConcept) {
			concept.setText(TextUtilities.removeNamespace(conceptText));
			conceptobj.setConcept(conceptText);
		} else {
			purpose.setText(TextUtilities.removeNamespace(conceptText));
			conceptobj.setPurpose(conceptText);
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
	public boolean updateData() {
		return false;
	}

}
