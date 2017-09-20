package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class InitializeDatabaseObjectImpl {
	
	static public void initializeDatabaseObjects(boolean reset) throws IOException {
		//String isAInitialization = "resources/experiment/isAInitialization.yaml";
		//String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";
		String organizationInitialization = "resources/contact/OrganizationInitialization.yaml";
		String userInitialization = "resources/contact/UserInitialization.yaml";

		if(reset) {
			ResetDatabaseObjects.clearDatabase();
		}
		
		DatabaseInitializeBase base = new DatabaseInitializeBase();
		if (!base.alreadyRead(userInitialization)) {
			System.out.println("Initializing users: " + userInitialization);
			base.readInitializationFile(userInitialization, "yaml");
		}
		
		if (!base.alreadyRead(organizationInitialization)) {
			System.out.println("Initializing organization: " + organizationInitialization);
			base.readInitializationFile(organizationInitialization, "yaml");
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
