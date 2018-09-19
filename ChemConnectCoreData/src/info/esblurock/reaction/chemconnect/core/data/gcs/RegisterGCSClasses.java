package info.esblurock.reaction.chemconnect.core.data.gcs;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterGCSClasses {

	
	
	public static void register() {
		ObjectifyService.register(GCSBlobFileInformation.class);
		ObjectifyService.register(GCSInputFileInterpretation.class);
		ObjectifyService.register(ParsedFilename.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(GCSBlobFileInformation.class);
		ResetDatabaseObjects.resetClass(GCSInputFileInterpretation.class);
		ResetDatabaseObjects.resetClass(ParsedFilename.class);
	}
}
