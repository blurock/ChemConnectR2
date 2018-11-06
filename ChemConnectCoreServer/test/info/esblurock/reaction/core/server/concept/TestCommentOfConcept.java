package info.esblurock.reaction.core.server.concept;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class TestCommentOfConcept {

	@Test
	public void test() {
		String concept1 = "dataset:FlameBurner";
		String concept2 = "dataset:HeatFluxBurner";
		String concept3 = "dataset:ConceptLinkReferenceMatrix";
		String concept4 = "dataset:ConceptLinkSensor";
		
		String comment1 = ConceptParsing.getComment(concept1);
		System.out.println("Comment of '" + concept1 +  "' = " + comment1);
		String label1 = ConceptParsing.getLabel(concept1);
		System.out.println("label of '" + concept1 +  "' = " + label1);
		
		String comment2 = ConceptParsing.getComment(concept2);
		System.out.println("Comment of '" + concept2 +  "' = " + comment2);
		String label2 = ConceptParsing.getLabel(concept2);
		System.out.println("label of '" + concept2 +  "' = " + label2);
		
		String comment3 = ConceptParsing.getComment(concept3);
		System.out.println("Comment of '" + concept3 +  "' = " + comment3);
		String label3 = ConceptParsing.getLabel(concept3);
		System.out.println("label of '" + concept3 +  "' = " + label3);
		
		String comment4 = ConceptParsing.getComment(concept4);
		System.out.println("Comment of '" + concept4 +  "' = " + comment4);
		String label4 = ConceptParsing.getLabel(concept4);
		System.out.println("label of '" + concept4 +  "' = " + label4);
	}

}
