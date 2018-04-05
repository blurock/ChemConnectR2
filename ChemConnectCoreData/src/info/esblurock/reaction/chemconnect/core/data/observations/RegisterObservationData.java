package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterObservationData {
	public static void register() {
		
		ObjectifyService.register(SingleSpreadSheetBlock.class);
		ObjectifyService.register(SpreadSheetInputInformation.class);
		ObjectifyService.register(SpreadSheetInterpretation.class);
		ObjectifyService.register(SpreadSheetRow.class);
		ObjectifyService.register(SpreadSheetTitleRowCorrespondence.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(SingleSpreadSheetBlock.class);
		ResetDatabaseObjects.resetClass(SpreadSheetInputInformation.class);
		ResetDatabaseObjects.resetClass(SpreadSheetInterpretation.class);
		ResetDatabaseObjects.resetClass(SpreadSheetRow.class);
		ResetDatabaseObjects.resetClass(SpreadSheetTitleRowCorrespondence.class);
	}
}
