package info.esblurock.reaction.chemconnect.core.client.device;

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
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
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

	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	@UiField
	MaterialLink topname;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialLink choose;
	@UiField
	MaterialPanel modalpanel;
	String access;

	InputLineModal line; 
	ArrayList<SetOfObservationsCollapsible> setofobservables;
	SetOfObservationsCollapsible observations;
	DatabaseObject baseobj;
	
	public SetOfObservationsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SetOfObservationsDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		defaultCatagory = "Catagory";
		baseobj = new DatabaseObject();
		choose.setText("Create");
		topname.setText(defaultCatagory);
		enterkeyS = "Enter Observations Catagory";
		keynameS = "Catagory";
		access = MetaDataKeywords.publicAccess;
		setofobservables = new ArrayList<SetOfObservationsCollapsible>();
	}
	
	@UiHandler("topname")
	public void changeCatagory(ClickEvent event) {
		MaterialToast.fireToast("Change Catagory");
	}
	
	@UiHandler("choose")
	public void chooseConcept(ClickEvent event) {
		if(topname.getText().compareTo(defaultCatagory) == 0 ) {
			line = new InputLineModal(enterkeyS,keynameS,this);
			modalpanel.add(line);
			line.openModal();
		} else {
			setUpObservationStructure();
		}
	}
	@Override
	public void setLineContent(String line) {
		topname.setText(line);
		setUpObservationStructure();
	}
	
	public void setUpObservationStructure() {
		ObservationStructureCallback callback = new ObservationStructureCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsStructructure(callback);
	}

	@Override
	public void addChemConnectDataStructure(ChemConnectDataStructure structure) {
		DatabaseObject topobj = structure.getIdentifier();
		String id = topname.getText();
		topobj.setIdentifier(id);
		topobj.setAccess(access);
		String defaultObservationID = "Observation";
		observations = new SetOfObservationsCollapsible(id, defaultObservationID, modalpanel);
		setofobservables.add(observations);
		addHierarchialModal(topobj,structure,observations);
		contentcollapsible.add(observations);
		setIdentifer(topobj);
	}

	public void addHierarchialModal(DatabaseObject topobj,ChemConnectDataStructure infoStructure, SetOfObservationsCollapsible obstop) {
		for(DataElementInformation element : infoStructure.getRecords()) {
			String subid = topobj.getIdentifier() + "-" + element.getSuffix();
			DatabaseObject subobj = new DatabaseObject(topobj);
			subobj.setIdentifier(subid);
			String type = element.getDataElementName();
			if(infoStructure.getMapping().getStructure(type) != null) {
					MainDataStructureCollapsible main = new MainDataStructureCollapsible(subobj,element,infoStructure,modalpanel);
					obstop.addInfoCollapsible(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}	
		}
	}
	
	public void setIdentifer(DatabaseObject obj) {
		baseobj = new DatabaseObject(obj);
		for(SetOfObservationsCollapsible observe : setofobservables) {
			observe.setIdentifier(obj);
		}
	}

}
