package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.menubar.MaterialMenuBar;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectObservationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ProtocolDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.TutorialExamplePlace;
import info.esblurock.reaction.chemconnect.core.client.place.UploadFileToBlobStoragePlace;

public class TopChemConnectPanel extends Composite {

	private static TopChemConnectPanelUiBinder uiBinder = GWT.create(TopChemConnectPanelUiBinder.class);

	interface TopChemConnectPanelUiBinder extends UiBinder<Widget, TopChemConnectPanel> {
	}

	@UiField
	MaterialLabel title;
	@UiField
	MaterialLabel subtitle;
	@UiField
	SimplePanel contentPanel;
	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink upload;
	@UiField
	MaterialLink isolate;
	@UiField
	MaterialLink specification;
	@UiField
	MaterialLink protocol;
	@UiField
	MaterialLink dataSet;
	@UiField
	MaterialLink devices;
	@UiField
	MaterialLink people;
	@UiField
	MaterialLink organizations;
	@UiField
	MaterialLink tutorialreadfile;

	ClientFactory clientFactory;
	
	public TopChemConnectPanel(ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.clientFactory = clientFactory;
	}
	
	void init() {
		catalog.setText("Manage Data Catalog Structure");
		upload.setText("file staging and interpretation");
		isolate.setText("isolate block out of spreadsheet");
		specification.setText("parameter specification");
		protocol.setText("protocol specification");
		dataSet.setText("dataset from a protocol");
		devices.setText("Manage Device Information");
		people.setText("researchers in database");
		organizations.setText("organizations in database");
		tutorialreadfile.setText("Interpret data files");
	}
	
	@UiHandler("catalog")
	public void onCatalogClick(ClickEvent event) {
		subtitle.setText("Manage Catalog Structure");
		goTo(new ManageCatalogHierarchyPlace("Manage Catalog Structure"));
	}
	@UiHandler("upload")
	public void onUploadClick(ClickEvent event) {
		subtitle.setText("File staging");
		goTo(new UploadFileToBlobStoragePlace("File staging"));
	}
	@UiHandler("isolate")
	public void onIsolateClick(ClickEvent event) {
		subtitle.setText("Isolate data block");
		goTo(new IsolateMatrixBlockPlace("Isolate data block"));
	}
	@UiHandler("specification")
	public void onSpecificationClick(ClickEvent event) {
		subtitle.setText("File staging");
		goTo(new ChemConnectObservationPlace("File staging"));
	}
	@UiHandler("protocol")
	public void onProtocolClick(ClickEvent event) {
		subtitle.setText("Protocol Specification");
		goTo(new ProtocolDefinitionPlace("Protocol Specification"));
	}
	@UiHandler("dataSet")
	public void onDataSetClick(ClickEvent event) {
		subtitle.setText("Observation Specification");
		goTo(new ChemConnectObservationPlace("Observation Specification"));
	}
	@UiHandler("devices")
	public void onDevicesClick(ClickEvent event) {
		subtitle.setText("Manage Device Info");
		goTo(new DeviceWithSubystemsDefinitionPlace("Manage Device Info"));
	}
	@UiHandler("people")
	public void onPeopleClick(ClickEvent event) {
		subtitle.setText("Manage Contact Info");
		goTo(new DatabasePersonDefinitionPlace("Manage Contact Info"));
	}
	@UiHandler("organizations")
	public void onOrganizationsClick(ClickEvent event) {
		subtitle.setText("Manage Organizations");
		goTo(new OrganizationDefinitionPlace("Manage Organizations"));
	}
	@UiHandler("tutorialreadfile")
	public void onTutorialReadFileClick(ClickEvent event) {
		subtitle.setText("Tutorial: Reading and Interpreting data files");
		goTo(new TutorialExamplePlace("Tutorial: Reading and Interpreting data files"));
	}

	public SimplePanel getContentPanel() {
		return contentPanel;
	}

	private void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
