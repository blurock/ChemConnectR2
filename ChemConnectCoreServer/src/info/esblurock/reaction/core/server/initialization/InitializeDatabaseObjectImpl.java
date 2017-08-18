package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;

public class InitializeDatabaseObjectImpl {
	
	static public void initializeDatabaseObjects() throws IOException {
		String isAInitialization = "resources/experiment/isAInitialization.yaml";
		String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";

		System.out.println("initializeDatabaseObjects()");
		DatabaseInitializeBase base = new DatabaseInitializeBase();
		if (!base.alreadyRead(isAInitialization)) {
			System.out.println("Initializing: " + isAInitialization);
			base.readInitializationFile(isAInitialization, "yaml");
		}
		if (!base.alreadyRead(apparatusProperties)) {
			System.out.println("Initializing: " + apparatusProperties);
			base.readInitializationFile(apparatusProperties, "yaml");
		}
	}
	
}
