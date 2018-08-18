package info.esblurock.reaction.core.server.ontology;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class TestFindStructure {

	@Test
	public void test() {
		String structure1 = ConceptParsing.getStructureType("dataset:BurnerPlateObservations");
		System.out.println("dataset:BurnerPlateObservations:  " + structure1);
	}

}
