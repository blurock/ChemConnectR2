package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveDateObjectRow extends Composite implements ChooseFromConceptHeirarchy {

	private static PrimitiveDateObjectRowUiBinder uiBinder = GWT.create(PrimitiveDateObjectRowUiBinder.class);

	interface PrimitiveDateObjectRowUiBinder extends UiBinder<Widget, PrimitiveDateObjectRow> {
	}

	@UiField
	MaterialIcon info;
	@UiField
	MaterialLink dateType;
	@UiField
	MaterialDatePicker value;
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialPanel toppanel;
	
	String chemconnectPropertiesS = MetaDataKeywords.chemConnectParameter;
	String typeWithNamespace;
	String identifier;
	boolean typeSet;
	
	public PrimitiveDateObjectRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public PrimitiveDateObjectRow(PrimitiveDataStructureInformation primitiveinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		fill(primitiveinfo);
	}
	public void fill(PrimitiveDataStructureInformation info) {
		try {
		typeWithNamespace = info.getType();
		String without = TextUtilities.removeNamespace(typeWithNamespace);
		dateType.setText(without);
		String dateS = info.getValue();
		Date date = new Date();
		if(dateS != null) {
			if(dateS.length() > 0) {
				
			}
		}
		value.setDate(date);
		identifier = info.getIdentifier();
		tip.setText(identifier);
		typeSet = true;
		} catch(Exception ex) {
			Window.alert(ex.toString());
		}
	}
	
	@UiHandler("dateType")
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

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		typeWithNamespace = concept;
		dateType.setText(TextUtilities.removeNamespace(typeWithNamespace));
	}

	public String getIdentifier() {
		return identifier;
	}


}
