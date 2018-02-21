package info.esblurock.reaction.chemconnect.core.data.transfer.observations;

import java.io.Serializable;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class SetOfObservationsTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	String observationStructure;
	Set<String> subsystems;
	SetOfObservationsInformation observations;
	ChemConnectDataStructure structure;
	
	public SetOfObservationsTransfer() {
		super();
		this.observationStructure = null;
	}
	public SetOfObservationsTransfer(String observationStructure) {
		super();
		this.observationStructure = observationStructure;
	}

	public String getObservationStructure() {
		return observationStructure;
	}

	public void setObservationStructure(String observationStructure) {
		this.observationStructure = observationStructure;
	}

	public Set<String> getSubsystems() {
		return subsystems;
	}

	public void setSubsystems(Set<String> subsystems) {
		this.subsystems = subsystems;
	}

	public SetOfObservationsInformation getObservations() {
		return observations;
	}

	public void setObservations(SetOfObservationsInformation observations) {
		this.observations = observations;
	}

	public ChemConnectDataStructure getStructure() {
		return structure;
	}

	public void setStructure(ChemConnectDataStructure structure) {
		this.structure = structure;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix)  {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix + "------------   " + observations + "   ------------\n");
		builder.append(prefix + "Subsystems using Observations");
		builder.append(prefix + subsystems + "\n");
		builder.append(observations);
		builder.append(structure);
		return builder.toString();
	}

}
