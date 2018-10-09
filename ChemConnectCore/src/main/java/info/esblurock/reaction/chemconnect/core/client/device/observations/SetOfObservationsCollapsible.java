package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.PrimitiveGeneralObservation;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class SetOfObservationsCollapsible extends Composite implements ChooseFromConceptHeirarchy, ObservationHierarchyInterface {

	private static SetOfObservationsCollapsibleUiBinder uiBinder = GWT
			.create(SetOfObservationsCollapsibleUiBinder.class);

	interface SetOfObservationsCollapsibleUiBinder extends UiBinder<Widget, SetOfObservationsCollapsible> {
	}



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
	@UiField
	MaterialLink infoelements;

	MaterialPanel modalpanel;
	ArrayList<PrimitiveGeneralObservation> obsset;
	ArrayList<MainDataStructureCollapsible> infoset;
	DatabaseObject baseobj;
	String rootID;
	String catalog;
	
	public SetOfObservationsCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SetOfObservationsCollapsible(String catalog, String rootID, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.catalog = catalog;
		this.rootID = TextUtilities.removeNamespace(rootID);
		this.modalpanel = modalpanel;
	}
	
	void init() {
		obsset = new ArrayList<PrimitiveGeneralObservation>();
		infoset = new ArrayList<MainDataStructureCollapsible>();
		baseobj = new DatabaseObject();
	}
	
	public void addInfoCollapsible(MainDataStructureCollapsible main) {
		infocollapsible.add(main);
		infoset.add(main);
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
		choices.add(MetaDataKeywords.chemConnectObservable);
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
		
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		rootID = TextUtilities.removeNamespace(concept);
		ObservationHierarchyCallback callback = new ObservationHierarchyCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsInformation(concept,callback);
	}

	@Override
	public void addSetOfObservations(SetOfObservationsTransfer transfer) {
		PrimitiveGeneralObservation observation = new PrimitiveGeneralObservation(transfer);
		baseobj = new DatabaseObject(transfer.getBaseobject());
		baseobj.setIdentifier(catalog);
		obsset.add(observation);
		contentcollapsible.add(observation);
		setIdentifier(baseobj);
	}
	
	public void setIdentifier(DatabaseObject obj) {
		baseobj = new DatabaseObject(obj);
		String id = baseobj.getIdentifier() + "-" + rootID;
		baseobj.setIdentifier(id);
		typetitle.setText(baseobj.getIdentifier());
		
		for(MainDataStructureCollapsible main : infoset) {
			main.setIdenifier(baseobj);
		}
		for(PrimitiveGeneralObservation obs : obsset) {
			obs.setIdentifier(baseobj);
		}
	}
	
}
