package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.TextAlign;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConvertToMaterialTree {
	
	
	public static MaterialTree addHierarchyTop(HierarchyNode hierarchy, MaterialTree tree) {
		MaterialTreeItem item = new MaterialTreeItem(hierarchy.getIdentifier());
		item.setTextColor(Color.BLACK);
		item.setIconType(IconType.FOLDER);
		item.setTextAlign(TextAlign.LEFT);
		tree.add(item);
		for(HierarchyNode sub : hierarchy.getSubNodes()) {
			addHierarchy(sub,item);
		}
		return tree;
	}
		
	public static void addHierarchy(HierarchyNode hierarchy, MaterialTreeItem item) {
		for(HierarchyNode sub : hierarchy.getSubNodes() ) {
			MaterialTreeItem subitem = new MaterialTreeItem(sub.getIdentifier());
			if(sub.getSubNodes().size() > 0) {
				subitem.setIconType(IconType.FOLDER);
			} else {
				subitem.setIconType(IconType.BUILD);
			}
			subitem.setTextAlign(TextAlign.LEFT);
			subitem.setTextColor(Color.BLACK);
			item.addItem(subitem);
			addHierarchy(sub,subitem);
		}
	}

}
