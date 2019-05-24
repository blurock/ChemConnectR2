package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ProtocolObservationsTest {

	@Test
	public void test() {
		System.out.println("Observations            -------------------------------------------------------------");
		Set<String> obs1 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",true);
		System.out.println("Observations: hasInput" + obs1);
		Set<String> obs2 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",false);
		System.out.println("Observations: hasOutput" + obs2);
	}

}
