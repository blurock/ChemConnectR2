package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class Observations {

	@Test
	public void test() {
		Set<String> set1 = ConceptParsing.setOfObservationsForSubsystem("dataset:HeatFluxBurner");
		System.out.println(set1);
		
		for(String parameter : set1) {
			Set<AttributeDescription> set = ConceptParsing.totalSetOfAttributesInConcept(parameter);
			System.out.println(set);
			for(AttributeDescription descr : set) {
				PrimitiveParameterValueInformation param = ConceptParsing.fillParameterInfo(descr.getAttributeName());
				System.out.println(param);
			}
		}
		
		Set<String> set2 = ConceptParsing.subsystemsForSubsystem("dataset:HeatFluxBurner");
		System.out.println(set2);
	}

}
