package info.esblurock.reaction.chemconnect.core.client.device;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectDataStructureInterface;
import info.esblurock.reaction.chemconnect.core.client.device.observations.ObservationStructureCallback;
import info.esblurock.reaction.chemconnect.core.client.device.observations.SetOfObservationsCollapsible;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class SetOfObservationsDefinition extends Composite implements  SetLineContentInterface, ChemConnectDataStructureInterface {

	private static SetOfObservationsDefinitionUiBinder uiBinder = GWT.create(SetOfObservationsDefinitionUiBinder.class);

	interface SetOfObservationsDefinitionUiBinder extends UiBinder<Widget, SetOfObservationsDefinition> {
	}

	String enterkeyS;
	String keynameS;
	@UiField
	MaterialLink parameter;
	@UiField
	MaterialTextBox topname;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialLink choose;
	@UiField
	MaterialPanel modalpanel;
	String access;

	InputLineModal line; 

	public SetOfObservationsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SetOfObservationsDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		parameter.setText("Set of Observations");
		choose.setText("Create");
		topname.setLabel("Catagory Name");
		topname.setText("");
		String name = Cookies.getCookie("user");
		topname.setPlaceholder(name);
		enterkeyS = "Enter Observations Catagory";
		keynameS = "Catagory";
		access = MetaDataKeywords.publicAccess;
	}
	
	@UiHandler("choose")
	public void chooseConcept(ClickEvent event) {
		if(topname.getText().length() == 0 ) {
			line = new InputLineModal(enterkeyS,keynameS,this);
			modalpanel.add(line);
			line.openModal();
		} else {
			//chooseConceptHieararchy();
			setUpObservationStructure();
		}
	}
/*
	private void chooseConceptHieararchy() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:ChemConnectObservable");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}
*/
	@Override
	public void setLineContent(String line) {
		topname.setText(line);
		//chooseConceptHieararchy();
		setUpObservationStructure();
	}
	public void setUpObservationStructure() {
		ObservationStructureCallback callback = new ObservationStructureCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsStructructure(callback);
	}
	
	
/*
	@Override
	public void conceptChosen(String topconcept, String concept) {
		ObservationStructureCallback callback = new ObservationStructureCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsStructructure(callback);
	}
	*/
	/*
	public void addSetOfObservations(SetOfObservationsTransfer transfer) {
		DatabaseObject topobj = transfer.getBaseobject();
		SetOfObservationsInformation info = transfer.getObservations();
		String observationName = transfer.getObservationStructure();
		SetOfObservationsCollapsible observations = new SetOfObservationsCollapsible(observationName,modalpanel);
		contentcollapsible.add(observations);
		String id = topname.getText();
		topobj.setIdentifier(id);
		topobj.setAccess(access);
		info.setIdentifier(id);
		addHierarchialModal(topobj, transfer, observations);
	}
	*/
	/*
	public void addHierarchialModal(DatabaseObject topobj, SetOfObservationsTransfer transfer,SetOfObservationsCollapsible obstop) {
		ChemConnectDataStructure infoStructure = transfer.getStructure();
		for(DataElementInformation element : infoStructure.getRecords()) {
			String subid = topobj.getIdentifier() + "-" + element.getSuffix();
			DatabaseObject subobj = new DatabaseObject(topobj);
			subobj.setIdentifier(subid);
			String type = element.getDataElementName();
			if(infoStructure.getMapping().getStructure(type) != null) {
					MainDataStructureCollapsible main = new MainDataStructureCollapsible(subobj,element,modalpanel);
					obstop.getInfoCollapsible().add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
	}
	*/
	public void addHierarchialModal(DatabaseObject topobj,ChemConnectDataStructure infoStructure, SetOfObservationsCollapsible obstop) {
		Window.alert("addHierarchialModal");
		Window.alert("addHierarchialModal: " + topobj.toString());
		for(DataElementInformation element : infoStructure.getRecords()) {
			Window.alert("addHierarchialModal: " + element.toString());
			String subid = topobj.getIdentifier() + "-" + element.getSuffix();
			DatabaseObject subobj = new DatabaseObject(topobj);
			subobj.setIdentifier(subid);
			String type = element.getDataElementName();
			if(infoStructure.getMapping().getStructure(type) != null) {
					MainDataStructureCollapsible main = new MainDataStructureCollapsible(subobj,element,infoStructure,modalpanel);
					obstop.getInfoCollapsible().add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}	
		}
	}

	@Override
	public void addChemConnectDataStructure(ChemConnectDataStructure structure) {
		DatabaseObject topobj = structure.getIdentifier();
		String id = topname.getText();
		topobj.setIdentifier(id);
		topobj.setAccess(access);
		Window.alert("addChemConnectDataStructure: " + id);
		SetOfObservationsCollapsible observations = new SetOfObservationsCollapsible(id,modalpanel);
		contentcollapsible.add(observations);
		//addHierarchialModal(topobj,structure,observations);
	}


}
