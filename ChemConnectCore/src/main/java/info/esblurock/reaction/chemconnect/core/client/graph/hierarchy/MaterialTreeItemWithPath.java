package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import java.util.ArrayList;

import gwt.material.design.addins.client.tree.MaterialTreeItem;

public class MaterialTreeItemWithPath extends MaterialTreeItem {
	ArrayList<String> path; 
	
	
	public MaterialTreeItemWithPath(String name, ArrayList<String> path) {
		super(name);
		this.path = path;
	}
	
	public ArrayList<String> getPath() {
		return path;
	}
	
}
