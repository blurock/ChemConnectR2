package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import com.google.gwt.json.client.JSONObject;

public class HorizontalHierarchyInformation {

	JSONObject root;
    int width = 500;
    int height = 500;
    
	public HorizontalHierarchyInformation(JSONObject root, int width, int height) {
		super();
		this.root = root;
		this.width = width;
		this.height = height;
	}
	
	public JSONObject getRoot() {
		return root;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
    
	
}
