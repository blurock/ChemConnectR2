package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;

public class InitializeDatabaseObjectImpl {
	
	static public void initializeDatabaseObjects() throws IOException {
		//String isAInitialization = "resources/experiment/isAInitialization.yaml";
		//String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";
		String organizationInitialization = "resources/contact/OrganizationInitialization.yaml";
		String userInitialization = "resources/contact/UserInitialization.yaml";

		RegisterContactData.reset();
		RegisterDescriptionData.reset();
		RegisterInitializationData.reset();
		RegisterRDFData.reset();

		
		DatabaseInitializeBase base = new DatabaseInitializeBase();
		if (!base.alreadyRead(userInitialization)) {
			System.out.println("Initializing contact: " + userInitialization);
			base.readInitializationFile(userInitialization, "yaml");
		}
		
		if (!base.alreadyRead(organizationInitialization)) {
			System.out.println("Initializing contact: " + organizationInitialization);
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
