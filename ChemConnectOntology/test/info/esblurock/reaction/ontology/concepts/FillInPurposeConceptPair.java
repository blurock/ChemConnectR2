package info.esblurock.reaction.ontology.concepts;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class FillInPurposeConceptPair {

	@Test
	public void test() {
		DatabaseObject object = new DatabaseObject();
		PurposeConceptPair pair = new PurposeConceptPair();
		String parameter = "dataset:HeatFluxBurner";
		ConceptParsing.fillInPurposeConceptPair(parameter, pair);
		System.out.println(pair.toString());
	}

}
