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
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class DeviceWithSubystemsDefinition extends Composite implements  
	ChooseFromConceptHeirarchy, SetLineContentInterface {

	private static DeviceWithSubystemsDefinitionUiBinder uiBinder = GWT
			.create(DeviceWithSubystemsDefinitionUiBinder.class);

	interface DeviceWithSubystemsDefinitionUiBinder extends UiBinder<Widget, DeviceWithSubystemsDefinition> {
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
	String access;
	
	public DeviceWithSubystemsDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public DeviceWithSubystemsDefinition(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		topname.setPlaceholder(firstName);
		init();
	}

	private void init() {
		parameter.setText("Device");
		choose.setText("Choose Device");
		topname.setLabel("Catagory Name");
		topname.setText("");
		String name = Cookies.getCookie("user");
		topname.setPlaceholder(name);
		enterkeyS = "Enter Catagory Name";
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
			chooseConceptHieararchy();
		}
	}
	
	private void chooseConceptHieararchy() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("dataset:DataTypeDevice");
		choices.add("dataset:DataTypeSubSystem");
		choices.add("dataset:DataTypeComponent");
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.add(choosedevice);
		choosedevice.open();		
	}
	
	public void async(String text) {
		MaterialToast.fireToast("Selected: " + text);
	}

	String getTopCatagory() {
		return topname.getText();
	}
	String getAccessLevel() {
		return access;
	}
	@Override
	public void conceptChosen(String topconcept, String concept) {
		SetUpDatabaseObjectHierarchyCallback callback = new SetUpDatabaseObjectHierarchyCallback(contentcollapsible,modalpanel);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String id = topname.getText();
		String user = Cookies.getCookie("user");
		DatabaseObject obj = new DatabaseObject(id,user,user,"");
		async.getDevice(obj,concept,callback);
	}
	
	public void addTopHierarchialModal(DatabaseObject obj,HierarchyNode hierarchy, TotalSubsystemInformation top) {
		SubsystemsAndDeviceCollapsible devicetop = new SubsystemsAndDeviceCollapsible(hierarchy.getIdentifier(),obj,modalpanel);
		contentcollapsible.add(devicetop);
		addHierarchialModal(obj,hierarchy, top,devicetop);
	}
	
	
	public void addHierarchialModal(DatabaseObject obj, HierarchyNode hierarchy, TotalSubsystemInformation top,
			SubsystemsAndDeviceCollapsible devicetop) {
		ChemConnectDataStructure infoStructure = top.getInfoStructure();
		for(DataElementInformation element : infoStructure.getRecords()) {
			String subid = obj.getIdentifier() + "-" + element.getSuffix();
			Window.alert("addHierarchialModal:  " + subid);
			Window.alert("addHierarchialModal:  " + element.toString());
			DatabaseObject subobj = new DatabaseObject(obj);
			subobj.setIdentifier(subid);
			String type = element.getDataElementName();
			SubsystemInformation subsysteminfo = top.getSubsystemsandcomponents().get(hierarchy.getIdentifier());
			if(infoStructure.getMapping().getStructure(element.getDataElementName()) != null) {
				MainDataStructureCollapsible main = new MainDataStructureCollapsible(subobj,element,
						infoStructure,subsysteminfo,modalpanel);
				devicetop.getInfoCollapsible().add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
		for(HierarchyNode sub: hierarchy.getSubNodes()) {
			DatabaseObject subobj = new DatabaseObject(obj);
			String subid = subobj.getIdentifier() + "-" + TextUtilities.removeNamespace(sub.getIdentifier());
			subobj.setIdentifier(subid);
			SubsystemsAndDeviceCollapsible subsystem = new SubsystemsAndDeviceCollapsible(sub.getIdentifier(),subobj,modalpanel);
			devicetop.getCollapsible().add(subsystem);
			DatabaseObject subsubobj = new DatabaseObject(subobj);
			addHierarchialModal(subsubobj,sub,top,subsystem);
		}
	}

	public void fillParameters(MainDataStructureCollapsible main, 
			SubsystemInformation subsysteminfo) {
	}

	@Override
	public void setLineContent(String line) {
		topname.setText(line);
		chooseConceptHieararchy();
	}


}
