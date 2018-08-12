package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class PropertyInConcept {

	@Test
	public void test() {
		String measure = "<http://purl.org/linked-data/cube#dimension>";
		String parameter = "dataset:SetOfMassFlowControllerObservables";
		
		Set<AttributeDescription> set = ConceptParsing.propertyInConcept(measure, parameter);
		System.out.println("-------------------------------------------------: " + set.size());
		for(AttributeDescription attr : set) {
			System.out.println(attr.toString());
		}
	}

}
