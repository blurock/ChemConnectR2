package info.esblurock.reaction.chemconnect.core.data.gcs;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterGCSClasses {

	
	
	public static void register() {
		ObjectifyService.register(GCSBlobFileInformation.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(GCSBlobFileInformation.class);
	}
}
