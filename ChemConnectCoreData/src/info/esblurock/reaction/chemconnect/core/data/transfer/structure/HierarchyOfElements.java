package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class HierarchyOfElements implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ArrayList<ChemConnectDataStructure> elements;
	HierarchyNode hierarchy;

	public HierarchyOfElements() {
	}
	
	public HierarchyOfElements(ArrayList<ChemConnectDataStructure> elements, HierarchyNode hierarchy) {
		this.elements = elements;
		this.hierarchy = hierarchy;
	}

	public ArrayList<ChemConnectDataStructure> getElements() {
		return elements;
	}

	public HierarchyNode getHierarchy() {
		return hierarchy;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(hierarchy.toString());
		for(ChemConnectDataStructure element : elements) {
			builder.append("\n");
			builder.append(element.toString());
		}
		return builder.toString();
	}
}
