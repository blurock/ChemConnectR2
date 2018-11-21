package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class FillInPurposeConceptPair {

	@Test
	public void test() {
		PurposeConceptPair pair = new PurposeConceptPair();
		String parameter = "dataset:HeatFluxBurner";
		ConceptParsing.fillInPurposeConceptPair(parameter, pair);
		System.out.println(pair.toString());
		String parameter2 = "dataset:RapidCompressionMachineReportingProtocol";
		ConceptParsing.fillInPurposeConceptPair(parameter2, pair);
		System.out.println(pair.toString());
	}

}
