package info.esblurock.reaction.chemconnect.core.data.concepts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AttributesOfObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String conceptobject;
	Map<String, Set<String>> attributesInSubsystems;
	Map<String, Set<String>> subsystemsOfAttributes;
	
	
	public AttributesOfObject() {
		this.conceptobject = null;
		attributesInSubsystems = new HashMap<String, Set<String>>();
		subsystemsOfAttributes = new HashMap<String, Set<String>>();
	}
	public AttributesOfObject(String conceptobject) {
		this.conceptobject = conceptobject;
		attributesInSubsystems = new HashMap<String, Set<String>>();
		subsystemsOfAttributes = new HashMap<String, Set<String>>();
	}
	
	public void addAttribute(String subsystem, String attribute) {
		addAttributeInSubsystem(subsystem, attribute);
		addSubsystemOfAttribute(subsystem, attribute);
	}
	public void addAttributeInSubsystem(String subsystem, String attribute) {
		Set<String> set = attributesInSubsystems.get(subsystem);
		if(set == null) {
			set = new TreeSet<String>();
			attributesInSubsystems.put(subsystem,set);
		}
		set.add(attribute);
	}

	public void addSubsystemOfAttribute(String subsystem, String attribute) {
		Set<String> attrset = subsystemsOfAttributes.get(attribute);
		if(attrset == null) {
			attrset = new TreeSet<String>();
			subsystemsOfAttributes.put(attribute,attrset);
		}
		attrset.add(subsystem);
	}
	
	public String getConceptobject() {
		return conceptobject;
	}

	public Map<String, Set<String>> getAttributesInSubsystems() {
		return attributesInSubsystems;
	}

	public Map<String, Set<String>> getSubsystemsOfAttributes() {
		return subsystemsOfAttributes;
	}

	public String toString() {
		return toString("");
		
	}
	
	public Set<AttributeDescription> extractAttributeDescription(Set<AttributeDescription> total) {
		Set<String> set = subsystemsOfAttributes.keySet();
		for(String attr : set) {
			AttributeDescription descr = new AttributeDescription(attr, subsystemsOfAttributes.get(attr));
			total.add(descr);
		}
		return total;
	}
	
	public String toString(String prefix) {
		StringBuffer buffer = new StringBuffer();
		String subprefix = prefix + "\t";
		buffer.append(prefix + "Concept Object: " + conceptobject + "\n");
		buffer.append(toStringAttributesInSubsystems(subprefix));
		buffer.append(toStringSubsystemsOfAttributes(subprefix));
		
		return buffer.toString();
	}
	
	
	public String toStringAttributesInSubsystems(String prefix) {
		String attributeprefix = prefix + "\t";
		String subprefix = attributeprefix + "\t  ";
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix + "Attributes in Subsystem\n");
		Set<String> set = attributesInSubsystems.keySet();
		for(String subsystem : set) {
			buffer.append(attributeprefix + "Subsystem: " + subsystem + "\n");
			Set<String> subset = attributesInSubsystems.get(subsystem);
			for(String attribute : subset) {
				buffer.append(subprefix + attribute + "\n");
			}
		}
		return buffer.toString();
	}
	
	public String toStringSubsystemsOfAttributes(String prefix) {
		String systemprefix = prefix + "\t";
		String subprefix = systemprefix + "\t  ";
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix + "Subsystems of Attributes\n");
		Set<String> set = subsystemsOfAttributes.keySet();
		for(String subsystem : set) {
			buffer.append(systemprefix + "Attribute: " + subsystem + "\n");
			Set<String> subset = subsystemsOfAttributes.get(subsystem);
			for(String attribute : subset) {
				buffer.append(subprefix + attribute + "\n");
			}
		}
		return buffer.toString();
	}
}
