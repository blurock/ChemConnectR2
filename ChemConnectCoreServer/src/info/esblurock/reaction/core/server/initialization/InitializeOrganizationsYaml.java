package info.esblurock.reaction.core.server.initialization;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.core.server.db.StoreObject;


public class InitializeOrganizationsYaml extends YamlFileInterpreterBase {
	public static String sourceKeyS = "";
	public static String inputKeyS = "Administration";
	/*
	public static String organizationDataTypeS = "foaf:Organisation";
	
	public static String descriptionkeyS = "Description";
	public static String contactkeyS = "Contact";
	public static String locationkeyS = "Location";

	public static String keywordsKeyS = "keywords";
	public static String titleKeyS = "title";
	public static String organizationKeyS = "organization";
	public static String descriptionKeyS = "description";
*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void interpret( Map map) {
		
		System.out.println("InitializeRDFYaml");
		StoreObject store = new StoreObject("Administration","", "0");
		System.out.println("Org: " + map.get("Organizations").getClass().getCanonicalName());
		HashMap<String,Object> orgsmap = (HashMap<String,Object>) map.get("Organizations");
		Set<String> orgnames = orgsmap.keySet();
		System.out.println("Orgnames: " + orgnames);
		for(String orgname : orgnames) {
			System.out.println("Org: " +  orgsmap.get(orgname).getClass().getCanonicalName());
			System.out.println("Org: " +  orgsmap.get(orgname));
			HashMap<String,Object> orgpartsmap = (HashMap<String,Object>) orgsmap.get(orgname);

		}
	
	}
}
