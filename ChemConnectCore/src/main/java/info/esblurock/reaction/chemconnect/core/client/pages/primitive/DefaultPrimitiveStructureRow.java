package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

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
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class DefaultPrimitiveStructureRow extends Composite implements ChooseFromConceptHeirarchy, SetLineContentInterface {

	private static DefaultPrimitiveStructureRowUiBinder uiBinder = GWT
			.create(DefaultPrimitiveStructureRowUiBinder.class);

	interface DefaultPrimitiveStructureRowUiBinder extends UiBinder<Widget, DefaultPrimitiveStructureRow> {
	}


	String chemconnectPropertiesS;

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

	public DefaultPrimitiveStructureRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public DefaultPrimitiveStructureRow(PrimitiveDataStructureInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(info);
	}

	void init() {
		type.setTextColor(Color.BLACK);
		value.setTextColor(Color.BLACK);
		typeSet = false;
		chemconnectPropertiesS = MetaDataKeywords.chemConnectParameter;
	}
	public void fill(PrimitiveDataStructureInformation info) {
		typeWithNamespace = info.getType();
		type.setText(TextUtilities.removeNamespace(typeWithNamespace));
		value.setText(info.getValue());
		identifier = info.getIdentifier();
		tip.setText(identifier);
		typeSet = true;
	}

	@UiHandler("value")
	void onClickValue(ClickEvent e) {
		line = new InputLineModal(TextUtilities.removeNamespace(typeWithNamespace),value.getText(),this);
		toppanel.add(line);
		line.openModal();
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
	void onClickType(ClickEvent e) {
		MaterialToast.fireToast("ID: " + identifier);
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
