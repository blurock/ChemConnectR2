package info.esblurock.reaction.ontology;

import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class BuildSetOfObservationsInformation {
	
	SetOfObservationsTransfer transfer;

	public BuildSetOfObservationsInformation(String observations) {
		
		transfer = new SetOfObservationsTransfer(observations);
		
		Set<String> subsystems = ConceptParsing.subsystemsOfObservations(observations);
		transfer.setSubsystems(subsystems);
		
		SetOfObservationsInformation obs = ConceptParsing.fillSetOfObservations(observations);
		transfer.setObservations(obs);

		ChemConnectDataStructure structure = DatasetOntologyParsing.getChemConnectDataStructure("dataset:SetOfObservationsStructure");
		transfer.setStructure(structure);
	}

	public SetOfObservationsTransfer getTransfer() {
		return transfer;
	}
	

}
