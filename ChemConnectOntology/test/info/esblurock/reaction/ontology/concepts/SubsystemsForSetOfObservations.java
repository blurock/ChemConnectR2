package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class SubsystemsForSetOfObservations {

	@Test
	public void test() {
		String obs1 = "dataset:BurnerPlateObservations";
		Set<String> subsystems1 = ConceptParsing.subsystemsOfObservations(obs1);
		System.out.println(obs1 + ":  " + subsystems1);
	}

}
