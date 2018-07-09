package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;


import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConvertToMaterialTree {
	
	
	public static MaterialTree addHierarchyTop(HierarchyNode hierarchy, MaterialTree tree) {
		MaterialTreeItem item = new MaterialTreeItem(hierarchy.getIdentifier());
		item.setTextColor(Color.BLACK);
		item.setTextAlign(TextAlign.LEFT);
		item.setIconType(IconType.FOLDER);
		tree.add(item);
		for(HierarchyNode sub : hierarchy.getSubNodes()) {
			addHierarchy(sub,item);
		}
		return tree;
	}
		
	public static void addHierarchy(HierarchyNode hierarchy, MaterialTreeItem item) {
		MaterialTreeItem next = new MaterialTreeItem(hierarchy.getIdentifier());
		next.setTextColor(Color.BLACK);
		next.setTextAlign(TextAlign.LEFT);
		if(hierarchy.getSubNodes().size() > 0) {
			next.setIconType(IconType.FOLDER);
			next.collapse();
		} else {
			next.setIconType(IconType.BUILD);
		}
		item.add(next);
		for(HierarchyNode sub : hierarchy.getSubNodes() ) {
			addHierarchy(sub,next);
		}
	}

}
