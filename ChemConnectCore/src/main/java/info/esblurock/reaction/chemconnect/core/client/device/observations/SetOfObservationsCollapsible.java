package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveGeneralObservation;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class SetOfObservationsCollapsible extends Composite implements ChooseFromConceptHeirarchy, ObservationHierarchyInterface {

	private static SetOfObservationsCollapsibleUiBinder uiBinder = GWT
			.create(SetOfObservationsCollapsibleUiBinder.class);

	interface SetOfObservationsCollapsibleUiBinder extends UiBinder<Widget, SetOfObservationsCollapsible> {
	}


	String identifier;

	@UiField
	MaterialLabel typetitle;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsible infocollapsible;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink add;	

	MaterialPanel modalpanel;
	
	public SetOfObservationsCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SetOfObservationsCollapsible(String name, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		typetitle.setText(TextUtilities.removeNamespace(name));
		this.modalpanel = modalpanel;
	}

	public MaterialCollapsible getInfoCollapsible() {
		return infocollapsible;
	}
	public MaterialCollapsible getContentCollapsible() {
		return contentcollapsible;
	}

	@UiHandler("add") 
	void onClickAdd(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:ChemConnectObservable");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
		
	}

	@Override
	public void conceptChosen(String topconcept, String concept) {
		ObservationHierarchyCallback callback = new ObservationHierarchyCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsInformation(concept,callback);
	}

	@Override
	public void addSetOfObservations(SetOfObservationsTransfer transfer) {
		String observationname = transfer.getObservationStructure();
		PrimitiveGeneralObservation observation = new PrimitiveGeneralObservation(observationname);
		contentcollapsible.add(observation);
	}
	
	
}
