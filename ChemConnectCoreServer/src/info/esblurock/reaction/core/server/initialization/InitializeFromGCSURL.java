package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;

public class InitializeFromGCSURL {
	public static void initialize() throws IOException {
		String devcat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/DeviceCatagory/"
				+ "Guest-DatasetCatalogHierarchy-DeviceCatagory-Device.yaml";
		String datacat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/ExperimentalSetDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-ExperimentalSetDataCatagory-Data.yaml";
		String pubresultscat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/PublishedResultsCatagory/"
				+ "Guest-DatasetCatalogHierarchy-PublishedResultsCatagory-PublishedResults.yaml";
		String orgcat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/OrganizationDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-OrganizationDataCatagory-CHEMCONNECT.yaml";
		String usercat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
		
		
		DatabaseInterpretBase base = new DatabaseInterpretBase();
		
		ArrayList<String> initlst = new ArrayList<String>();
		initlst.add(usercat);
		initlst.add(orgcat);
		initlst.add(datacat);
		initlst.add(devcat);
		initlst.add(pubresultscat);
		for(String url: initlst) {
			if (!base.alreadyRead(url)) {
				base.readInitializationYamlFromURL(url);
			}
		}
		
	}
}
