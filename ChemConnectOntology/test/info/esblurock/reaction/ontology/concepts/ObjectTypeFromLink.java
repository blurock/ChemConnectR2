package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ObjectTypeFromLink {

	@Test
	public void test() {
		String concept1 = "dataset:ConceptLinkMethodology";
		String structure1 = ConceptParsing.findObjectTypeFromLinkConcept(concept1);
		System.out.println(structure1);
	}

}
