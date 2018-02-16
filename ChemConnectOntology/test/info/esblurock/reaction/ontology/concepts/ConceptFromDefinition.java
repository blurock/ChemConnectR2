package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ConceptFromDefinition {

	@Test
	public void test() {
		String definition = "dataset:DataLinkType";
		String topconcept = ConceptParsing.definitionFromStructure(definition);
		System.out.println("Concept: " + topconcept);
		HierarchyNode hierarchy = ConceptParsing.conceptHierarchy(topconcept);
		System.out.println(hierarchy.toString());
	}

}
