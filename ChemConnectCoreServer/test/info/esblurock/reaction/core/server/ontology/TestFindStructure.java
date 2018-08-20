package info.esblurock.reaction.core.server.ontology;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class TestFindStructure {

	@Test
	public void test() {
		String structure1 = ConceptParsing.getStructureType("dataset:BurnerPlateObservations");
		System.out.println("dataset:BurnerPlateObservations:  " + structure1);
		
		String measureS = ConceptParsing.qualifyStructureType(structure1, true);
		System.out.println("dataset:BurnerPlateObservations:  measure=" + measureS);
		
		String dimensionS = ConceptParsing.qualifyStructureType(structure1, false);
		System.out.println("dataset:BurnerPlateObservations:  dimension=" + dimensionS);
	}

	
	
}
