package info.esblurock.reaction.chemconnect.core.data.concepts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import org.eclipse.jdt.internal.core.util.Util.Comparable;

public class AttributeDescription implements Serializable {
	private static final long serialVersionUID = 1L;
	String attributeName;
	Set<String> subsystems;
	
	public AttributeDescription() {
	}
	
	public AttributeDescription(String attributeName, Set<String> subsystems) {
		this.attributeName = attributeName;
		this.subsystems = subsystems;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Set<String> getSubsystems() {
		return subsystems;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append(attributeName);
		buffer.append("\t");
		buffer.append(subsystems);
		return buffer.toString();
		
	}

}
