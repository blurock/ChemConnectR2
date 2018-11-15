package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;

public class HierarchyNode implements Serializable {
	private static final long serialVersionUID = 1L;

	String identifier;
	String label;
	String comment;
	
	ClassificationInformation info;
	ArrayList<HierarchyNode> subNodes;
	
	public HierarchyNode() {
		subNodes = null;
	}
	public HierarchyNode(String identifier) {
		super();
		subNodes = new ArrayList<HierarchyNode>();
		info = null;
		this.identifier = identifier;
		this.label = ChemConnectCompoundDataStructure.removeNamespace(identifier);
		this.comment = ChemConnectCompoundDataStructure.removeNamespace(identifier);
	}
	public HierarchyNode(String identifier, String label) {
		super();
		subNodes = new ArrayList<HierarchyNode>();
		info = null;
		this.identifier = identifier;
		this.label = label;
		this.comment = label;
	}
	public HierarchyNode(String identifier, String label, String comment) {
		super();
		subNodes = new ArrayList<HierarchyNode>();
		info = null;
		this.identifier = identifier;
		this.label = label;
		this.comment = comment;
	}

	public HierarchyNode(String identifier, String label, String comment,ClassificationInformation info) {
		super();
		subNodes = new ArrayList<HierarchyNode>();
		this.info = info;
		this.identifier = identifier;
		this.label = ChemConnectCompoundDataStructure.removeNamespace(identifier);
		this.comment = ChemConnectCompoundDataStructure.removeNamespace(identifier);
	}

	public HierarchyNode(String identifier,ClassificationInformation info) {
		super();
		this.identifier = identifier;
		subNodes = new ArrayList<HierarchyNode>();
		this.info = info;
		this.identifier = identifier;
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
	public String getLabel() {
		return label;
	}
	public String getComment() {
		return comment;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + identifier);
		build.append("  (" + label + ") " + comment);
		build.append(":\n");
		build.append(stringInfo(prefix));
		prefix += "\t";
		build.append(stringSubNodes(prefix));
		return build.toString();
	}
	protected String stringInfo(String prefix) {
		StringBuilder build = new StringBuilder();
		if(info != null) {
			build.append(prefix + info.toString());
			build.append(":\n");
		}
		return build.toString();		
	}
	protected String stringSubNodes(String prefix) {
		StringBuilder build = new StringBuilder();
		for(HierarchyNode subnode : subNodes) {
			String subnodeS = subnode.toString(prefix);
			build.append(subnodeS);
		}
		return build.toString();		
	}
}
