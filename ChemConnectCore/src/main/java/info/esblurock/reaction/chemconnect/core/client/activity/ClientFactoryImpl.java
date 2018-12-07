package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectAdministrationImpl;
import info.esblurock.reaction.chemconnect.core.client.catalog.ManageCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.protocol.ProtocolDefinition;
import info.esblurock.reaction.chemconnect.core.client.contact.DatabasePersonDefinition;
import info.esblurock.reaction.chemconnect.core.client.contact.OrganizationDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.DeviceWithSubystemsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.IsolateMatrixBlock;
import info.esblurock.reaction.chemconnect.core.client.firstpage.FirstPage;
import info.esblurock.reaction.chemconnect.core.client.firstpage.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.client.gcs.UploadFileToBlobStorage;
import info.esblurock.reaction.chemconnect.core.client.info.tutorial.TutorialExample;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DeviceWithSubystemsDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstSiteLandingPageView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.IsolateMatrixBlockView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ManageCatalogHierarchyView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ProtocolDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.TutorialExampleView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.UploadFileToBlobStorageView;

public class ClientFactoryImpl implements ClientFactory {
	private final SimpleEventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(eventBus);
	private final ChemConnectAdministrationView chemConnectAdministrationView = new ChemConnectAdministrationImpl();
	private final IsolateMatrixBlockView isolateMatrixBlockView = new IsolateMatrixBlock();
	private final DeviceWithSubystemsDefinitionView deviceWithSubystemsDefinitionView = new DeviceWithSubystemsDefinition();
	private final ChemConnectObservationView chemConnectObservationView = new SetOfObservationsDefinition();
	private final ProtocolDefinitionView protocolDefinitionView = new ProtocolDefinition();
	private final UploadFileToBlobStorageView uploadFileToBlobStorageView = new UploadFileToBlobStorage();
	private final ManageCatalogHierarchyView manageCatalogHierarchyView = new ManageCatalogHierarchy();
	private final OrganizationDefinitionView organizationDefinitionView = new OrganizationDefinition();
	private final DatabasePersonDefinitionView databasePersonDefinitionView = new DatabasePersonDefinition();
	private final TutorialExampleView tutorialExampleView = new TutorialExample();
	private final FirstPageView firstPageView = new FirstPage();
	private final FirstSiteLandingPageView firstLandingSitePageView = new FirstSiteLandingPage();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public ChemConnectAdministrationView getChemConnectAdministrationView() {
		return chemConnectAdministrationView;
	}
	
	@Override
	public ChemConnectObservationView getChemConnectObservationView() {
		return chemConnectObservationView;
	}
	@Override
	public IsolateMatrixBlockView getIsolateMatrixBlockView() {
		return isolateMatrixBlockView;
	}
	@Override
	public DeviceWithSubystemsDefinitionView getDeviceWithSubystemsDefinitionView() {
		return deviceWithSubystemsDefinitionView;
	}
	@Override
	public ProtocolDefinitionView getProtocolDefinitionView() {
		return protocolDefinitionView;
	}
	@Override
	public UploadFileToBlobStorageView getUploadFileToBlobStorageView() {
		return uploadFileToBlobStorageView;
	}
	@Override
	public ManageCatalogHierarchyView getManageCatalogHierarchyView() {
		return manageCatalogHierarchyView;
	}
	@Override
	public OrganizationDefinitionView getOrganizationDefinitionView() {
		return organizationDefinitionView;
	}
	@Override
	public DatabasePersonDefinitionView getDatabasePersonDefinitionView() {
		return databasePersonDefinitionView;
	}
	@Override
	public TutorialExampleView getTutorialExampleView() {
		return tutorialExampleView;
	}
	@Override
	public FirstPageView getFirstPageView() {
		return firstPageView;
	}
	@Override
	public FirstSiteLandingPageView getFirstSiteLandingPageView() {
		return firstLandingSitePageView;
	}

}
