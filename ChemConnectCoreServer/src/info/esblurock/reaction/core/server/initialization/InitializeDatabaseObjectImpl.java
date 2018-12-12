package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;


public class InitializeDatabaseObjectImpl {
	
	static public void initializeDatabaseObjects() throws IOException {
		//String isAInitialization = "resources/experiment/isAInitialization.yaml";
		//String apparatusProperties = "resources/experiment/ApparatusPropertiesInitialization.yaml";
		//String organizationInitialization = "resources/contact/OrganizationInitialization.yaml";
		String userInitialization = "resources/contact/UserInitialization.yaml";

		String devcat = "https://storage.cloud.google.com/combustion/Guest/DatasetCatalogHierarchy/DeviceCatagory/Guest-DatasetCatalogHierarchy-DeviceCatagory-Device.yaml";
		String usercat = "https://storage.cloud.google.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
		String orgcat = "https://storage.cloud.google.com/combustion/Guest/DatasetCatalogHierarchy/OrganizationDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-OrganizationDataCatagory-CHEMCONNECT.yaml";
		String datcat = "https://storage.cloud.google.com/combustion/Guest/DatasetCatalogHierarchy/ExperimentalSetDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-ExperimentalSetDataCatagory-ExampleData.yaml";
		DatabaseInterpretBase base = new DatabaseInterpretBase();
		
		ArrayList<String> initlst = new ArrayList<String>();
		initlst.add(usercat);
		initlst.add(orgcat);
		initlst.add(datcat);
		initlst.add(devcat);
		for(String url: initlst) {
			if (!base.alreadyRead(url)) {
				base.readInitializationYamlFromURL(url);
			}
		}
		
		/*
		if (!base.alreadyRead(userInitialization)) {
			System.out.println("Initializing users: " + userInitialization);
			base.readInitializationFile(userInitialization, "yaml");
		}
		 */
		/*
		if (!base.alreadyRead(organizationInitialization)) {
			System.out.println("Initializing organization: " + organizationInitialization);
			base.readInitializationFile(organizationInitialization, "yaml");
		}
		*/
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
