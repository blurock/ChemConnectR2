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
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.device.observations.SetOfObservationsCollapsible;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetOfObservationsDefinition extends Composite implements ObjectVisualizationInterface {

	private static SetOfObservationsDefinitionUiBinder uiBinder = GWT.create(SetOfObservationsDefinitionUiBinder.class);

	interface SetOfObservationsDefinitionUiBinder extends UiBinder<Widget, SetOfObservationsDefinition> {
	}

	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	
	ChooseFullNameFromCatagoryRow choose;
	String access;

	
	public SetOfObservationsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public SetOfObservationsDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:ChemConnectObservable");
		String user = Cookies.getCookie("user");
		String object = "dataset:SetOfObservationValues";
		choose = new ChooseFullNameFromCatagoryRow(this,user,object,choices,modalpanel);
		topPanel.add(choose);
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
	/*
	@Override
	public void conceptChosen(String topconcept, String concept) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String id = topname.getText();
		String user = Cookies.getCookie("user");
		DatabaseObject obj = new DatabaseObject(id,user,user,"");
		async.getSetOfObservations(obj,concept,topname.getText(),callback);
		
	}
	*/
/*
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
*/
	@Override
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String observation = choose.getObjectType();
		String title = choose.getObjectName();
		async.getSetOfObservations(obj,observation,title,datid,callback);
	
		
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(subs,modalpanel);
		contentcollapsible.add(item);
	}


}
