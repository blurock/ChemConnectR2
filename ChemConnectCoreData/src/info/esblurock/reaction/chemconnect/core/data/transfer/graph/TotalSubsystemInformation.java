package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TotalSubsystemInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	String identifier;
	Map<String, SubsystemInformation> subsystemsandcomponents;
	Map<String, String> attributesubsystemMap;
	HierarchyNode subsystemtree;
	
	
	public TotalSubsystemInformation(String identifier, 
			Map<String, SubsystemInformation> subsystemsandcomponents,
			Map<String, String> attributesubsystemMap,
			HierarchyNode subsystemtree) {
		super();
		this.identifier = identifier;
		this.subsystemsandcomponents = subsystemsandcomponents;
		this.attributesubsystemMap = attributesubsystemMap;
		this.subsystemtree = subsystemtree;
	}
	public TotalSubsystemInformation(String identifier) {
		super();
		this.identifier = identifier;
		this.subsystemsandcomponents = new HashMap<String, SubsystemInformation>();
		this.attributesubsystemMap = new HashMap<String, String>();
		this.subsystemtree = null;
	}
	public TotalSubsystemInformation() {
		super();
		this.identifier = "";
		this.subsystemsandcomponents = new HashMap<String, SubsystemInformation>();
		this.attributesubsystemMap = new HashMap<String, String>();
		this.subsystemtree = null;
	}
	
	public void addInformation(SubsystemInformation info) {
		subsystemsandcomponents.put(info.getIdentifier(), info);
	}
	public HierarchyNode getSubsystemtree() {
		return subsystemtree;
	}
	public void setSubsystemtree(HierarchyNode subsystemtree) {
		this.subsystemtree = subsystemtree;
	}
	public Map<String, SubsystemInformation> getSubsystemsandcomponents() {
		return subsystemsandcomponents;
	}
	
	public Map<String, String> getAttributesubsystemMap() {
		return attributesubsystemMap;
	}
	public void setAttributesubsystemMap(Map<String, String> attributesubsystemMap) {
		this.attributesubsystemMap = attributesubsystemMap;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Total: " + identifier + "\n");
		prefix = prefix + "\t  ";
		build.append(subsystemtree.toString(prefix));
		Set<String> attributes = attributesubsystemMap.keySet();
		build.append(prefix + "Attribute Source\n");
		String spaces = "                                          ";
		String sprefix = prefix + "\t";
		for(String attribute : attributes) {
			build.append(sprefix);
			int amount = spaces.length() - attribute.length();
			build.append(attribute);
			if(amount > 0) 
				build.append(spaces.substring(0,amount));
			else
				build.append(" ");
			build.append(attributesubsystemMap.get(attribute) + "\n");
		}
		build.append(prefix + "SubSystems:\n");
		sprefix = prefix + "\t";
		Set<String> set = subsystemsandcomponents.keySet();
		for(String sub : set) {
			build.append(subsystemsandcomponents.get(sub).toString(sprefix));
		}
		return build.toString();
	}
}
