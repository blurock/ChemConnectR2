package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.Set;

public class SubsystemInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	String identifier;
	
	Set<String> immediateComponents;
	Set<String> immediateSubsystems;
	
	Set<SubSystemConceptLink> links;
	
	SubSystemParameters attributes;
	SubSystemParameters output;
	
	

	public SubsystemInformation() {
		this.identifier = null;
		this.immediateComponents = null;
		this.immediateSubsystems = null;
		this.links = null;
		this.attributes = null;
		this.output = null;
		
	}
	public SubsystemInformation(String identifier, 
			Set<String> immediateComponents, 
			Set<String> immediateSubsystems,
			Set<SubSystemConceptLink> links,
			SubSystemParameters attributes, 
			SubSystemParameters output) {
		this.identifier = identifier;
		this.immediateComponents = immediateComponents;
		this.immediateSubsystems = immediateSubsystems;
		this.links = links;
		this.attributes = attributes;
		this.output = output;
		
	}

	
	public String getIdentifier() {
		return identifier;
	}


	public Set<String> getImmediateComponents() {
		return immediateComponents;
	}


	public Set<String> getImmediateSubsystems() {
		return immediateSubsystems;
	}


	public SubSystemParameters getAttributes() {
		return attributes;
	}


	public SubSystemParameters getOutput() {
		return output;
	}


	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {

		StringBuilder build = new StringBuilder();
		build.append(prefix + identifier);
		build.append("\n");
		prefix += "\t";
		if (immediateSubsystems != null) {
			build.append(prefix + "  Subsystems: " + immediateSubsystems + "\n");
		}
		if (immediateComponents != null) {
			build.append(prefix + "  Components: " + immediateComponents + "\n");
		}
		if (links != null) {
			build.append(prefix + "  Links     : " + links + "\n");
		}
		if (attributes != null) {
			build.append(attributes.toString(prefix));
		}
		if (output != null) {
			build.append(output.toString(prefix));
		}
		return build.toString();
	}
}
