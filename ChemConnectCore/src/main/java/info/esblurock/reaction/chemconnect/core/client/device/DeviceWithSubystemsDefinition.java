package info.esblurock.reaction.chemconnect.core.client.device;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;

import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;

public class DeviceWithSubystemsDefinition extends Composite implements HasText, ChooseFromConceptHeirarchy {

	private static DeviceWithSubystemsDefinitionUiBinder uiBinder = GWT
			.create(DeviceWithSubystemsDefinitionUiBinder.class);

	interface DeviceWithSubystemsDefinitionUiBinder extends UiBinder<Widget, DeviceWithSubystemsDefinition> {
	}

	@UiField
	MaterialLink parameter;
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
		parameter.setText(firstName);
		init();
	}

	private void init() {
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

	@Override
	public void conceptChosen(String concept) {
		MaterialToast.fireToast("conceptChose: " + concept);
		DeviceHierarchyCallback callback = new DeviceHierarchyCallback(this);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.buildSubSystem(concept,callback);
		
		
		
	}

	public void addHierarchialModal(TotalSubsystemInformation hierarchy) {
		Window.alert(hierarchy.getSubsystemtree().toString());
		
	}

}
