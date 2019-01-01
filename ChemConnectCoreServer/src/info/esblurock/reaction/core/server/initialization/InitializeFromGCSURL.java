package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;

public class InitializeFromGCSURL {
	public static void initialize() throws IOException {
		
		
		DatabaseInterpretBase base = new DatabaseInterpretBase();
		
		ArrayList<String> initlst = new ArrayList<String>();
		String usercat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
		initlst.add(usercat);
		for(String url: initlst) {
			if (!base.alreadyRead(url)) {
				base.readInitializationYamlFromURL(url);
			}
		}
		
	}
}
