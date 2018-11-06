package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import java.util.ArrayList;

import gwt.material.design.addins.client.tree.MaterialTreeItem;

public class MaterialTreeItemWithPath extends MaterialTreeItem {
	ArrayList<String> path; 
	
	String identifier;
	
	public MaterialTreeItemWithPath(String identifier, String name, ArrayList<String> path) {
		super(name);
		this.path = path;
		this.identifier = identifier;
	}
	
	public ArrayList<String> getPath() {
		return path;
	}
	
	public String getIdentifier() {
		return identifier;
	}
}
