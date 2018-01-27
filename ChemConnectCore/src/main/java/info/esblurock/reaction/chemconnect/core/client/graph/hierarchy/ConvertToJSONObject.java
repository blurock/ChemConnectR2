package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConvertToJSONObject {
	
	public static JSONObject HierarchyNodeToJSON(HierarchyNode hierarchy) {
		JSONObject root = new JSONObject();
		root.put("name", new JSONString(removeNamespace(hierarchy.getIdentifier())));
		JSONArray arr = new JSONArray();
		root.put("children", arr);
		int count = 0;
		for(HierarchyNode node : hierarchy.getSubNodes()) {
			JSONObject jsonnode = HierarchyNodeToJSON(node);
			arr.set(count++,jsonnode);
		}
		return root;
	}
	public static String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String shortname = name;
		if(pos > 0) {
			shortname = name.substring(pos+1);
		}
		return shortname;
	}
}
