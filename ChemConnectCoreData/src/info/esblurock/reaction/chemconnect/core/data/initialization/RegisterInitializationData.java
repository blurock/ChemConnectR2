package info.esblurock.reaction.chemconnect.core.data.initialization;

import com.googlecode.objectify.ObjectifyService;

public class RegisterInitializationData {
	public static void register() {
		ObjectifyService.register(InitializationFile.class);
	}
}
