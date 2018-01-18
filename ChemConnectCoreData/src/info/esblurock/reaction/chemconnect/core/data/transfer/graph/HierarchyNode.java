package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;

public class HierarchyNode implements Serializable {
	private static final long serialVersionUID = 1L;

	String identifier;
	ClassificationInformation info;
	ArrayList<HierarchyNode> subNodes;
	
	public HierarchyNode() {
		subNodes = null;
	}
	public HierarchyNode(String identifier) {
		super();
		this.identifier = identifier;
		subNodes = new ArrayList<HierarchyNode>();
		info = null;
	}
	public HierarchyNode(String identifier,ClassificationInformation info) {
		super();
		this.identifier = identifier;
		subNodes = new ArrayList<HierarchyNode>();
		this.info = info;
	}
	
	public void addSubNode(HierarchyNode subnode) {
		subNodes.add(subnode);
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public ArrayList<HierarchyNode> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(ArrayList<HierarchyNode> subNodes) {
		this.subNodes = subNodes;
	}
	public ClassificationInformation getInfo() {
		return info;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + identifier);
		build.append(":\n");
		if(info != null) {
			build.append(prefix + info.toString());
			build.append(":\n");
		}
		prefix += "\t";
		for(HierarchyNode subnode : subNodes) {
			String subnodeS = subnode.toString(prefix);
			build.append(subnodeS);
		}
		return build.toString();
	}
}
