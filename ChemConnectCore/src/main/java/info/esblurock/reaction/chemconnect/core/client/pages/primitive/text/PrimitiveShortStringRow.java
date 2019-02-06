package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

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
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveShortStringRow extends Composite implements HasText, ChooseFromConceptHeirarchy, SetLineContentInterface {

	private static PrimitiveShortStringRowUiBinder uiBinder = GWT.create(PrimitiveShortStringRowUiBinder.class);

	interface PrimitiveShortStringRowUiBinder extends UiBinder<Widget, PrimitiveShortStringRow> {
	}

	String chemconnectPropertiesS = MetaDataKeywords.chemConnectParameter;
	
	@UiField
	MaterialIcon info;
	@UiField
	MaterialLink type;
	@UiField
	MaterialLink value;
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialPanel toppanel;
	InputLineModal line;
	String identifier;
	String typeWithNamespace;
	boolean typeSet;
	
	public PrimitiveShortStringRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		value.setText("Text");
	}

	public PrimitiveShortStringRow(PrimitiveDataStructureInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(info);
	}

	private void init() {
		identifier ="id";
		tip.setText(identifier);
		type.setText("Choose type");
		typeSet = false;
		type.setTextColor(Color.BLACK);
		value.setTextColor(Color.BLACK);
	}
	public void fill(PrimitiveDataStructureInformation info) {
		typeWithNamespace = info.getType();
		identifier =info.getIdentifier();
		tip.setText(identifier);
		type.setText(TextUtilities.removeNamespace(typeWithNamespace));
		value.setText(info.getValue());
		typeSet = true;
	}
	@UiHandler("type")
	void onClick(ClickEvent e) {
		if(!typeSet) {
			ArrayList<String> choices = new ArrayList<String>();			
			choices.add(chemconnectPropertiesS);
			ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
			toppanel.add(choosedevice);
			choosedevice.open();
		}
	}
	@UiHandler("info")
	void onClickInfo(ClickEvent e) {
		MaterialToast.fireToast(identifier);
	}
	@UiHandler("value")
	void onClickValue(ClickEvent e) {
		line = new InputLineModal(TextUtilities.removeNamespace(typeWithNamespace),value.getText(),this);
		toppanel.add(line);
		line.openModal();
	}

	public void setText(String text) {
		identifier = text;
		line.removeFromParent();
	}

	public String getText() {
		return identifier;
	}

	@Override
	public void setLineContent(String line) {
		value.setText(line);
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		typeWithNamespace = concept;
		type.setText(TextUtilities.removeNamespace(typeWithNamespace));
	}

}
