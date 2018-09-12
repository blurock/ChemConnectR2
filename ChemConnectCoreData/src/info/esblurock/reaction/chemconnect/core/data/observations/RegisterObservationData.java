package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;

public class RegisterObservationData {
	public static void register() {
		
		ObjectifyService.register(SpreadSheetInputInformation.class);
		ObjectifyService.register(SpreadSheetInterpretation.class);
		ObjectifyService.register(ObservationValueRow.class);
		ObjectifyService.register(SpreadSheetTitleRowCorrespondence.class);
		ObjectifyService.register(ObservationsFromSpreadSheet.class);
		ObjectifyService.register(ObservationMatrixValues.class);
		ObjectifyService.register(ObservationValueRowTitle.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(SpreadSheetInputInformation.class);
		ResetDatabaseObjects.resetClass(SpreadSheetInterpretation.class);
		ResetDatabaseObjects.resetClass(ObservationValueRow.class);
		ResetDatabaseObjects.resetClass(SpreadSheetTitleRowCorrespondence.class);
		ResetDatabaseObjects.resetClass(ObservationsFromSpreadSheet.class);
		ResetDatabaseObjects.resetClass(ObservationMatrixValues.class);
		ResetDatabaseObjects.resetClass(ObservationValueRowTitle.class);
	}
}
