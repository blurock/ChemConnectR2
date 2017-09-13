package info.esblurock.reaction.chemconnect.core.data.initialization;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterInitializationData {
	public static void register() {
		ObjectifyService.register(InitializationFile.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(InitializationFile.class);
	}
	
}
