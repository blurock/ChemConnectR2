package info.esblurock.reaction.core.server.ontology;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.core.server.db.extract.BuildSetOfObservationsInformation;

public class SetofObservationsInformation {

	@Test
	public void test() {
		BuildSetOfObservationsInformation build1 
		= new BuildSetOfObservationsInformation("dataset:BurnerPlateObservations");
		System.out.println("------------------------------------------------------");
		System.out.println(build1.getTransfer());
		System.out.println("------------------------------------------------------");

		ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation("dataset:SetOfObservationsStructure");
		System.out.println(classification);

	}

}
