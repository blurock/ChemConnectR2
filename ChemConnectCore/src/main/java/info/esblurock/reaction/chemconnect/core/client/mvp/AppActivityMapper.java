package info.esblurock.reaction.chemconnect.core.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import info.esblurock.reaction.chemconnect.core.client.activity.AboutSummaryActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ChemConnectAdministrationActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ChemConnectObservationActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ChemConnectPartnersActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.activity.ContactInformationActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.DataManagementActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.DatabasePersonDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.DeviceWithSubystemsDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.FirstPageActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.FirstSiteLandingPageActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.IsolateMatrixBlockActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ManageCatalogHierarchyActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.MissionStatementActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.OrganizationDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.ProtocolDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.TutorialExampleActivity;
import info.esblurock.reaction.chemconnect.core.client.activity.UploadFileToBlobStorageActivity;
import info.esblurock.reaction.chemconnect.core.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectAdministrationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectObservationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectPartnersPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ContactInformationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DataManagementPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.client.place.MissionStatementPlace;
import info.esblurock.reaction.chemconnect.core.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ProtocolDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.TutorialExamplePlace;
import info.esblurock.reaction.chemconnect.core.client.place.UploadFileToBlobStoragePlace;



public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ChemConnectAdministrationPlace) {
			return new ChemConnectAdministrationActivity((ChemConnectAdministrationPlace) place, clientFactory);
		} else if (place instanceof ChemConnectObservationPlace) {
			return new ChemConnectObservationActivity((ChemConnectObservationPlace) place, clientFactory);
		} else if (place instanceof IsolateMatrixBlockPlace) {
			return new IsolateMatrixBlockActivity((IsolateMatrixBlockPlace) place, clientFactory);
		} else if (place instanceof DeviceWithSubystemsDefinitionPlace) {
			return new DeviceWithSubystemsDefinitionActivity((DeviceWithSubystemsDefinitionPlace) place, clientFactory);
		} else if (place instanceof ProtocolDefinitionPlace) {
			return new ProtocolDefinitionActivity((ProtocolDefinitionPlace) place, clientFactory);
		} else if (place instanceof UploadFileToBlobStoragePlace) {
			return new UploadFileToBlobStorageActivity((UploadFileToBlobStoragePlace) place, clientFactory);
		} else if (place instanceof ManageCatalogHierarchyPlace) {
			return new ManageCatalogHierarchyActivity((ManageCatalogHierarchyPlace) place, clientFactory);
		} else if (place instanceof OrganizationDefinitionPlace) {
			return new OrganizationDefinitionActivity((OrganizationDefinitionPlace) place, clientFactory);
		} else if (place instanceof DatabasePersonDefinitionPlace) {
			return new DatabasePersonDefinitionActivity((DatabasePersonDefinitionPlace) place, clientFactory);
		} else if (place instanceof TutorialExamplePlace) {
			return new TutorialExampleActivity((TutorialExamplePlace) place, clientFactory);
		} else if (place instanceof FirstPagePlace) {
			return new FirstPageActivity((FirstPagePlace) place, clientFactory);
		} else if (place instanceof FirstSiteLandingPagePlace) {
			return new FirstSiteLandingPageActivity((FirstSiteLandingPagePlace) place, clientFactory);
		} else if (place instanceof MissionStatementPlace) {
			return new MissionStatementActivity((MissionStatementPlace) place, clientFactory);
		} else if (place instanceof AboutSummaryPlace) {
			return new AboutSummaryActivity((AboutSummaryPlace) place, clientFactory);
		} else if (place instanceof ChemConnectPartnersPlace) {
			return new ChemConnectPartnersActivity((ChemConnectPartnersPlace) place, clientFactory);
		} else if (place instanceof ContactInformationPlace) {
			return new ContactInformationActivity((ContactInformationPlace) place, clientFactory);
		} else if (place instanceof DataManagementPlace) {
			return new DataManagementActivity((DataManagementPlace) place, clientFactory);
		}
		
		return null;
	}
}