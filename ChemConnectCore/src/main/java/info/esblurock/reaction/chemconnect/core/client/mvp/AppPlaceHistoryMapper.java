package info.esblurock.reaction.chemconnect.core.client.mvp;



import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectAdministrationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectObservationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.core.client.place.UploadFileToBlobStoragePlace;
import info.esblurock.reaction.chemconnect.core.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.client.place.MissionStatementPlace;
import info.esblurock.reaction.chemconnect.core.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.place.TutorialExamplePlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectPartnersPlace;
import info.esblurock.reaction.chemconnect.core.client.place.ContactInformationPlace;
import info.esblurock.reaction.chemconnect.core.client.place.DataManagementPlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
//@WithTokenizers( { HelloPlace.Tokenizer.class, GoodbyePlace.Tokenizer.class })
@WithTokenizers( { ChemConnectAdministrationPlace.Tokenizer.class,
	IsolateMatrixBlockPlace.Tokenizer.class,
	DeviceWithSubystemsDefinitionPlace.Tokenizer.class,
	ChemConnectObservationPlace.Tokenizer.class,
	DatabasePersonDefinitionPlace.Tokenizer.class,
	UploadFileToBlobStoragePlace.Tokenizer.class,
	ManageCatalogHierarchyPlace.Tokenizer.class,
	OrganizationDefinitionPlace.Tokenizer.class,
	DatabasePersonDefinitionPlace.Tokenizer.class,
	TutorialExamplePlace.Tokenizer.class,
	FirstPagePlace.Tokenizer.class,
	FirstSiteLandingPagePlace.Tokenizer.class,
	MissionStatementPlace.Tokenizer.class,
	AboutSummaryPlace.Tokenizer.class,
	ChemConnectPartnersPlace.Tokenizer.class,
	ContactInformationPlace.Tokenizer.class,
	DataManagementPlace.Tokenizer.class
	}

)

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
