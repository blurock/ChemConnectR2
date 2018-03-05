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
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.device.observations.ObservationHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.device.observations.SetOfObservationsCollapsible;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class SetOfObservationsDefinition extends Composite implements ChooseFromConceptHeirarchy, SetLineContentInterface{

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
		choose.setText("Choose Observations");
		topname.setLabel("Catagory Name");
		topname.setText("");
		String name = Cookies.getCookie("user");
		topname.setPlaceholder(name);
		enterkeyS = "Enter Observations Catagory";
		keynameS = "Catagory";
	}
	
	@UiHandler("choose")
	public void chooseConcept(ClickEvent event) {
		if(topname.getText().length() == 0 ) {
			line = new InputLineModal(enterkeyS,keynameS,this);
			modalpanel.add(line);
			line.openModal();
		} else {
			chooseConceptHieararchy();
		}
	}

	private void chooseConceptHieararchy() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:ChemConnectSetOfObservations");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}

	@Override
	public void setLineContent(String line) {
		topname.setText(line);
		chooseConceptHieararchy();
	}

	@Override
	public void conceptChosen(String topconcept, String concept) {
		ObservationHierarchyCallback callback = new ObservationHierarchyCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.getSetOfObservationsInformation(concept,callback);
	}


	
	public void addSetOfObservations(SetOfObservationsTransfer transfer) {
		String observationName = transfer.getObservationStructure();
		SetOfObservationsCollapsible observations = new SetOfObservationsCollapsible(observationName);
		contentcollapsible.add(observations);
		String id = topname.getText();
		addHierarchialModal(id, transfer, observations);
	}
	public void addHierarchialModal(String id, SetOfObservationsTransfer transfer,SetOfObservationsCollapsible obstop) {
		ChemConnectDataStructure infoStructure = transfer.getStructure();
		for(DataElementInformation element : infoStructure.getRecords()) {
			String subid = id + "-" + element.getSuffix();
			String type = element.getDataElementName();
			//SubsystemInformation subsysteminfo = transfer.getSubsystemsandcomponents().get(element.getIdentifier());
			if(infoStructure.getMapping().getStructure(type) != null) {
					MainDataStructureCollapsible main = new MainDataStructureCollapsible(subid,element,
							infoStructure,transfer.getObservations(),
							transfer.getObservationStructure(),
							modalpanel);
					obstop.getInfoCollapsible().add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
	}


}
