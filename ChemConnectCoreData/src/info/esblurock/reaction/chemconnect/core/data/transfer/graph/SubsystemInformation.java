package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class SubsystemInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	String identifier;
	
	Set<String> immediateComponents;
	Set<String> immediateSubsystems;
	
	Set<SubSystemConceptLink> links;
	
	SubSystemParameters attributes;
	SubSystemParameters output;
	Set<SetOfObservationsInformation> observations;
	

	public SubsystemInformation() {
		this.identifier = null;
		this.immediateComponents = null;
		this.immediateSubsystems = null;
		this.links = null;
		this.attributes = null;
		this.output = null;
		this.observations = null;
		
	}
	public SubsystemInformation(String identifier, 
			Set<String> immediateComponents, 
			Set<String> immediateSubsystems,
			Set<SubSystemConceptLink> links,
			SubSystemParameters attributes, 
			SubSystemParameters output,
			Set<SetOfObservationsInformation> observations
			) {
		this.identifier = identifier;
		this.immediateComponents = immediateComponents;
		this.immediateSubsystems = immediateSubsystems;
		this.links = links;
		this.attributes = attributes;
		this.output = output;
		this.observations = observations;
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


	public Set<SetOfObservationsInformation> getObservations() {
		return observations;
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
		if(observations != null) {
			if(observations.size() > 0) {
				build.append(prefix + "Observations: \n");
				String newprefix = prefix + "\t";
				for(SetOfObservationsInformation observation : observations) {
					build.append(observation.toString(newprefix));
				}
			}
		}
		return build.toString();
	}
}
