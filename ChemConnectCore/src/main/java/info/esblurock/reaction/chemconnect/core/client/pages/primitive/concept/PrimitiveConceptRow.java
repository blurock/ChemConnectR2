package info.esblurock.reaction.chemconnect.core.client.pages.primitive.concept;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
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
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveConceptRow extends Composite implements HasText, ChooseFromConceptHeirarchy {

	private static PrimitiveConceptRowUiBinder uiBinder = GWT.create(PrimitiveConceptRowUiBinder.class);

	interface PrimitiveConceptRowUiBinder extends UiBinder<Widget, PrimitiveConceptRow> {
	}

	public PrimitiveConceptRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiField
	MaterialLink type;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialIcon info;
	
	boolean conceptSet;
	String identifier;
	String typeWithNamespace;
	
	public PrimitiveConceptRow(PrimitiveDataStructureInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		typeWithNamespace = info.getType();
		identifier = info.getIdentifier();
		type.setText(TextUtilities.removeNamespace(typeWithNamespace));
		tip.setText(identifier);
		concept.setText(info.getValue());
		conceptSet = true;
	}

	private void init() {
		type.setTextColor(Color.BLACK);
		concept.setTextColor(Color.BLACK);
		conceptSet = false;
	}
	
	public void setConcept(String conceptText) {
		concept.setText(conceptText);
	}
	
	@UiHandler("concept")
	void onClick(ClickEvent e) {
			ArrayList<String> choices = new ArrayList<String>();
			choices.add(typeWithNamespace);
			ChooseFromConceptHierarchyFromDefinition choosedevice = new ChooseFromConceptHierarchyFromDefinition(choices,this);
			modalpanel.add(choosedevice);
			choosedevice.open();
	}
	@UiHandler("info")
	void onClickType(ClickEvent e) {
		MaterialToast.fireToast("ID: " + identifier);
	}
	
	
	@Override
	public void conceptChosen(String topconcept, String conceptText) {
		concept.setText(conceptText);
	}

	public String getTypeWithNamespace() {
		return typeWithNamespace;
	}

	public void setText(String text) {
		concept.setText(text);
	}

	public String getText() {
		return concept.getText();
	}

}
