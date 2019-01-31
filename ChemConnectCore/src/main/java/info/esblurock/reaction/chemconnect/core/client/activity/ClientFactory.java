package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.client.TopChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.client.ui.view.AboutSummaryView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectPartnersView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ContactInformationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DataManagementView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DeviceWithSubystemsDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.FirstSiteLandingPageView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.IsolateMatrixBlockView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ManageCatalogHierarchyView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.MissionStatementView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ProtocolDefinitionView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.TutorialExampleView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.UploadFileToBlobStorageView;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	void setInUser();
	TopChemConnectPanel getTopPanel();

	ChemConnectAdministrationView getChemConnectAdministrationView();
	ChemConnectObservationView getChemConnectObservationView();
	IsolateMatrixBlockView getIsolateMatrixBlockView();
	DeviceWithSubystemsDefinitionView getDeviceWithSubystemsDefinitionView();
	ProtocolDefinitionView getProtocolDefinitionView();
	UploadFileToBlobStorageView getUploadFileToBlobStorageView();
	ManageCatalogHierarchyView getManageCatalogHierarchyView();
	OrganizationDefinitionView getOrganizationDefinitionView();
	DatabasePersonDefinitionView getDatabasePersonDefinitionView();
	TutorialExampleView getTutorialExampleView();
	FirstPageView getFirstPageView();
	FirstSiteLandingPageView getFirstSiteLandingPageView();
	MissionStatementView getMissionStatementView();
	AboutSummaryView getAboutSummaryView();
	ChemConnectPartnersView getChemConnectPartnersView();
	DataManagementView getDataManagementView();
	ContactInformationView getContactInformationView();
}
