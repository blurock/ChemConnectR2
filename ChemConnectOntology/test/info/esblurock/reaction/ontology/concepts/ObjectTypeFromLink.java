package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ObjectTypeFromLink {

	@Test
	public void test() {
		String concept1 = "dataset:BurnerPlateObservationInterpretationMethodology";
		String structure1 = ConceptParsing.findObjectTypeFromLinkConcept(concept1);
		System.out.println(structure1);
		String concept2 = "dataset:ChemConnectProtocol";
		String structure2 = ConceptParsing.findObjectTypeFromLinkConcept(concept2);
		System.out.println(structure2);
	}

}
