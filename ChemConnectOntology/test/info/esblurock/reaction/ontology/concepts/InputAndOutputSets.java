package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class InputAndOutputSets {

	@Test
	public void test() {
		Set<String> measure2 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",true);
		System.out.println("RapidCompressionMachineReportingProtocol Measure Observations\n" + measure2);
		Set<String> dimension2 = ConceptParsing.setOfObservationsForProtocol("dataset:RapidCompressionMachineReportingProtocol",false);
		System.out.println("RapidCompressionMachineReportingProtocol Dimension Observations\n" + dimension2);
	}

}
