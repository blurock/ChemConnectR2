package info.esblurock.reaction.ontology;

import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class BuildSetOfObservationsInformation {
	
	SetOfObservationsTransfer transfer;

	public BuildSetOfObservationsInformation(String observations) {
		
		transfer = new SetOfObservationsTransfer(observations);
		
		Set<String> subsystems = ConceptParsing.subsystemsOfObservations(observations);
		transfer.setSubsystems(subsystems);
		
		SetOfObservationsInformation obs = ConceptParsing.fillSetOfObservations(observations);
		transfer.setObservations(obs);

	}

	public SetOfObservationsTransfer getTransfer() {
		return transfer;
	}
	

}
