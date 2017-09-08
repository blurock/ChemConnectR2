package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;

public class InitializeDatabaseObjectImpl {
	
	static public void initializeDatabaseObjects() throws IOException {
		//String isAInitialization = "resources/experiment/isAInitialization.yaml";
		//String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";
		String contactInitialization = "resources/contact/OrganizationInitialization.yaml";
		
		DatabaseInitializeBase base = new DatabaseInitializeBase();
		if (!base.alreadyRead(contactInitialization)) {
			System.out.println("Initializing contact: " + contactInitialization);
			base.readInitializationFile(contactInitialization, "yaml");
		}
		
		/*
		System.out.println("initializeDatabaseObjects()");
		if (!base.alreadyRead(isAInitialization)) {
			System.out.println("Initializing: " + isAInitialization);
			base.readInitializationFile(isAInitialization, "yaml");
		}
		if (!base.alreadyRead(apparatusProperties)) {
			System.out.println("Initializing: " + apparatusProperties);
			base.readInitializationFile(apparatusProperties, "yaml");
		}
		
		*/
	}
	
}
