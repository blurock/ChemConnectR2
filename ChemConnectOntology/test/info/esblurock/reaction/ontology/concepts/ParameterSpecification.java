package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;


import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ParameterSpecification {

	@Test
	public void test() {
		PrimitiveParameterValueInformation set1 = ConceptParsing.fillParameterInfo("dataset:ThermocouplePositionInBurner");
		System.out.println(set1);
		
		SetOfObservationsInformation obs = ConceptParsing.fillSetOfObservations("dataset:BurnerPlateObservations");
		System.out.println(obs);
	}

}
