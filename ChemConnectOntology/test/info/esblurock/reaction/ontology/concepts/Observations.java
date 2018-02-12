package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class Observations {

	@Test
	public void test() {
		Set<String> set1 = ConceptParsing.setOfObservationsForSubsystem("dataset:HeatFluxBurner");
		System.out.println(set1);
		Set<String> set2 = ConceptParsing.subsystemsForSubsystem("dataset:HeatFluxBurner");
		System.out.println(set2);
	}

}
