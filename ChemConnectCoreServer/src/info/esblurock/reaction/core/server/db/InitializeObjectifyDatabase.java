package info.esblurock.reaction.core.server.db;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.register.RegisterChemConnectData;

public class InitializeObjectifyDatabase {
	public static void init() {
		ObjectifyService.init();
		RegisterChemConnectData.register();
		RegisterServerDatabaseObjects.register();
	}
}
