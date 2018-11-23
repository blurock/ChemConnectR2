package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.HashMap;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ProtocolSetupTransfer implements Serializable {
	private static final long serialVersionUID = 1L;
	String protocolS;
	HashMap<String,HierarchyNode> measurenodes;
	HashMap<String,HierarchyNode> dimensionnodes;

	public ProtocolSetupTransfer() {
	}

	public ProtocolSetupTransfer(String protocolS, 
			HashMap<String, HierarchyNode> measurenodes,
			HashMap<String, HierarchyNode> dimensionnodes) {
		super();
		this.protocolS      = protocolS;
		this.measurenodes   = measurenodes;
		this.dimensionnodes = dimensionnodes;
	}

	
	public String getProtocolS() {
		return protocolS;
	}

	public void setProtocolS(String protocolS) {
		this.protocolS = protocolS;
	}

	public HashMap<String, HierarchyNode> getMeasurenodes() {
		return measurenodes;
	}

	public void setMeasurenodes(HashMap<String, HierarchyNode> measurenodes) {
		this.measurenodes = measurenodes;
	}

	public HashMap<String, HierarchyNode> getDimensionnodes() {
		return dimensionnodes;
	}

	public void setDimensionnodes(HashMap<String, HierarchyNode> dimensionnodes) {
		this.dimensionnodes = dimensionnodes;
	}


	public String hashMapPrint(String prefix, HashMap<String,HierarchyNode> nodes) {
		StringBuilder build = new StringBuilder();
		for(String name : nodes.keySet()) {
			build.append(prefix + name + "\n");
			HierarchyNode hierarchy = nodes.get(name);
			build.append(hierarchy.toString());
		}
		return build.toString();
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Protocol Name: " + protocolS + "\n");
		String dimensionprefix = prefix + " Dimension Nodes:";
		build.append(dimensionprefix + dimensionnodes.keySet() + "\n");
		build.append(hashMapPrint(dimensionprefix,dimensionnodes));
		String measureprefix =   prefix + "   Measure Nodes:";
		build.append(measureprefix + measurenodes.keySet() + "\n");
		build.append(hashMapPrint(measureprefix,measurenodes));
		
		//String hierarchyprefix = prefix + " Hierarchy:";
		//build.append(hierarchy.getObject().toString(hierarchyprefix));
		
		return build.toString();
	}
	
	
}
