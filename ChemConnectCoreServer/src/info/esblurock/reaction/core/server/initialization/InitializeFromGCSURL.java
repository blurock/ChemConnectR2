package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;

public class InitializeFromGCSURL {
	public static void initialize() throws IOException {
		
		
		DatabaseInterpretBase base = new DatabaseInterpretBase();
		
		ArrayList<String> initlst = new ArrayList<String>();
		/*
		String usercat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
		String usercat1 = "https://storage.cloud.google.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
		String usercat2 = "https://00e9e64bac01a89baf5ebf720bbc03c4b66d489f10b2de78fd-apidata.googleusercontent.com/download/storage/v1/b/combustion/o/Guest%2FDatasetCatalogHierarchy%2FUserDataCatagory%2FGuest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml?qk=AD5uMEtGITQEwXn4vWOAxoPaysL9x3Sng8sm9AeKPCCznmW9Y9NZq_KNIf4QiESDljIPeYSu7bwhCZge6cM6ReUvX-PeNKG_SxKKSw3DmMJ5eMZeB6wyY5kxZGNd7t8qU1pc-ccUPQMjfm1pTBlHrlxdEcH17qMEccAJN701REXPvq0ubj8Vbtp1tGxM4OSiFMy8zSgf1i1T7XXgksCyeGLjHHIVBv75CcCf3aHTpRSQHBjWl4CI9iAGVuamLGA01EuCzXzzOKcfUhQVC9upnY-G4ONDU6VtGZgvkdDGFZrBRydPi8LXEp7Zs4Xe1tL1T6wzyqyBQpvewo0O7Vn0GnhcMc_XAtHMOP6-nVMJvZ6yoV6REHmXFT4fwNqvpnx27OsyixG5jRhiKbvJdqF3g9ASMi1A4pz1nA94Yk1-e4fOKqocOIsNbgCzQKIc-D-DBdIktL5ZUqo2_-kYAsfIUCrWBmUZq1C6rhY7keRTSynUP85gi-UDtUg_4vVFtj4bxdrOuLLWD0HUZon0eNoaU2gvI1qguldTyuvx6_n256X-RBMb700naJNjwLgV4c0Ql8DX73Y5ywtiD-4HMXrIRuvD45ZJbE5V0HCrKSw2DwQn8o0pnwM2L56iTKbpD5m89mSdaIWYgNVmDQytVwj45-fLC5uacqtp9IE9CeJjOqNVRy-cUYbiMlwW-2giGw9wyuwybfj7kSvRBlHnHlNvtvSDWQCh9NKo6EU06DkEu9JPRyYzNcoGM9sS7FDR_1Zis6SnVnhMek2ZwTifc1efdmfJ3nYCOJQpqgmeU5iP49pO9u_-3D1lFLagxqUcmDKHC44a_N_EpQCnnYcWItlkKSNjpr1-8kuAPbVFtVojtBW9XNAqT2bloySogHv7JbrfSO51iU-dnxcE";
*/
		String usercat3 = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/"
				+ "Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml";
				
		initlst.add(usercat3);
		/*
		String imgdat = "https://storage.cloud.google.com/combustion/Guest/CHEMCONNECT/PublishedData/ExampleData/DataFileImageStructure/"
				+ "Guest-CHEMCONNECT-PublishedData-ExampleData-DataFileImageStructure-iButanol-15bar-phi%3D1.0.yaml";
				*/
		//initlst.add(imgdat);
		for(String url: initlst) {
			if (!base.alreadyRead(url)) {
				base.readInitializationYamlFromURL(url);
			}
		}
		
	}
}
