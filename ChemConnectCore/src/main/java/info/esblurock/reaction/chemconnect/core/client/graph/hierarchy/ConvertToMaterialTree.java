package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;


import java.util.ArrayList;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConvertToMaterialTree {
	
	
	public static MaterialTree addHierarchyTop(HierarchyNode hierarchy, MaterialTree tree) {
		ArrayList<String> path = new ArrayList<String>();
		MaterialTreeItemWithPath item = new MaterialTreeItemWithPath(hierarchy.getIdentifier(), determineLabel(hierarchy), path);
		item.setTextColor(Color.BLACK);
		item.setTextAlign(TextAlign.LEFT);
		item.setIconType(IconType.FOLDER);
		tree.add(item);
		item.collapse();
		ArrayList<String> nextpath = new ArrayList<String>(path);
		nextpath.add(item.getText());
		for(HierarchyNode sub : hierarchy.getSubNodes()) {
			addHierarchy(sub,nextpath,item);
		}
		return tree;
	}
		
	public static void addHierarchy(HierarchyNode hierarchy, ArrayList<String> path, MaterialTreeItem item) {
		MaterialTreeItemWithPath next = new MaterialTreeItemWithPath(hierarchy.getIdentifier(), determineLabel(hierarchy),path);
		next.setTextColor(Color.BLACK);
		next.setTextAlign(TextAlign.LEFT);
		if(hierarchy.getSubNodes().size() > 0) {
			next.setIconType(IconType.FOLDER);
			next.collapse();
		} else {
			next.setIconType(IconType.BUILD);
		}
		item.add(next);
		ArrayList<String> nextpath = new ArrayList<String>(path);
		nextpath.add(next.getIdentifier());
		for(HierarchyNode sub : hierarchy.getSubNodes() ) {
			addHierarchy(sub,nextpath,next);
		}
	}

	private static String determineLabel(HierarchyNode hierarchy) {
		String id = hierarchy.getLabel();
		if(hierarchy.getLabel().compareTo(hierarchy.getComment()) != 0) {
			id = hierarchy.getLabel() + ": (" + hierarchy.getComment() + ")";
		}
		return id;
	}
}
