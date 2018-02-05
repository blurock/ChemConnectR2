package info.esblurock.reaction.chemconnect.core.client.device;

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

import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class DeviceWithSubystemsDefinition extends Composite implements HasText, ChooseFromConceptHeirarchy {

	private static DeviceWithSubystemsDefinitionUiBinder uiBinder = GWT
			.create(DeviceWithSubystemsDefinitionUiBinder.class);

	interface DeviceWithSubystemsDefinitionUiBinder extends UiBinder<Widget, DeviceWithSubystemsDefinition> {
	}

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
		String name = Cookies.getCookie("user");
		topname.setPlaceholder(name);
	}
	
	@UiHandler("choose")
	public void chooseConcept(ClickEvent event) {
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(this);
		modalpanel.add(choosedevice);
		choosedevice.open();
	}
	
	public void setText(String text) {
		parameter.setText(text);
	}

	public String getText() {
		return parameter.getText();
	}

	public void async(String text) {
		MaterialToast.fireToast("Selected: " + text);
	}

	String getTopCatagory() {
		return topname.getPlaceholder();
	}
	
	@Override
	public void conceptChosen(String concept) {
		MaterialToast.fireToast("conceptChose: " + concept);
		DeviceHierarchyCallback callback = new DeviceHierarchyCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.buildSubSystem(concept,callback);
	}
	
	public void addTopHierarchialModal(HierarchyNode hierarchy, TotalSubsystemInformation top) {
		SubsystemsAndDeviceCollapsible devicetop = new SubsystemsAndDeviceCollapsible(hierarchy.getIdentifier());
		contentcollapsible.add(devicetop);
		addHierarchialModal(hierarchy, top,devicetop);
	}
	
	
	public void addHierarchialModal(HierarchyNode hierarchy, TotalSubsystemInformation top,
			SubsystemsAndDeviceCollapsible devicetop) {
		//Window.alert("DeviceWithSubystemsDefinition: DataElementInformation" + hierarchy.toString());
		ChemConnectDataStructure infoStructure = top.getInfoStructure();
		for(DataElementInformation element : infoStructure.getRecords()) {
			String type = element.getDataElementName();
			ChemConnectCompoundDataStructure compound = infoStructure.getMapping().getStructure(type);
			if(compound != null) {
				MainDataStructureCollapsible main = new MainDataStructureCollapsible(compound,infoStructure);
				devicetop.getInfoCollapsible().add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}
		}
		for(HierarchyNode sub: hierarchy.getSubNodes()) {
			SubsystemsAndDeviceCollapsible subsystem = new SubsystemsAndDeviceCollapsible(sub.getIdentifier());
			devicetop.getCollapsible().add(subsystem);
			addHierarchialModal(sub,top,subsystem);
		}
	}

}
