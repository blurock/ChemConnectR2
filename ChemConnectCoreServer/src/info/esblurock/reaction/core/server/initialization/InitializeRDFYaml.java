package info.esblurock.reaction.core.server.initialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.core.server.db.StoreObject;


public class InitializeRDFYaml extends YamlFileInterpreterBase {
	@SuppressWarnings("rawtypes")
	public void interpret( Map map) {
		System.out.println("InitializeRDFYaml");
		StoreObject store = new StoreObject("Administration","", "0");
		@SuppressWarnings("unchecked")
		HashMap<String,String> rdfmap = (HashMap<String,String>) map.get("RDF");
		Set<String> keys = rdfmap.keySet();
		for(String predicateS : keys) {
			Object obj = rdfmap.get(predicateS);
			if(obj != null) {
				ArrayList lst = (ArrayList) obj;
				for(Object o : lst) {
					@SuppressWarnings("unchecked")
					HashMap<String,String> valuemap = (HashMap<String,String>) o;
					Set<String> subjectkeys = valuemap.keySet();
					for(String subjectS: subjectkeys) {
						String objectS = (String) valuemap.get(subjectS);
						System.out.println("S: " + subjectS + "\t P: " + predicateS + "\t O: " + objectS);
						store.setKeyword(subjectS);
						store.storeStringRDF(predicateS, objectS);
					}
				}				
			} else {
				System.out.println("null: " + predicateS);
			}
		}
		store.finish();
	}

}
