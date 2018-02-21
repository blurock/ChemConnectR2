package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.ontology.BuildSetOfObservationsInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class SetofObservationsInformation {

	@Test
	public void test() {
		BuildSetOfObservationsInformation build1 
		= new BuildSetOfObservationsInformation("dataset:BurnerPlateObservations");
		System.out.println(build1.getTransfer());
		System.out.println("------------------------------------------------------");

		ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation("dataset:SetOfObservationsStructure");
		System.out.println(classification);

	}

}
