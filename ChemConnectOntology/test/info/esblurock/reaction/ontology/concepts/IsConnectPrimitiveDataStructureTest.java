package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectPrimitiveDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class IsConnectPrimitiveDataStructureTest {

	@Test
	public void test() {
		String concept1 = "dataset:ContactHasSite";
		boolean answer1 = ConceptParsing.isAChemConnectPrimitiveDataStructure(concept1);
		System.out.println(concept1 + ":   " + answer1);
		
		String concept2 = "dataset:ContactEmail";
		boolean answer2 = ConceptParsing.isAChemConnectPrimitiveDataStructure(concept2);
		System.out.println(concept2 + ":   " + answer2);
		
		String concept3 = "dataset:NameOfPerson";
		boolean answer3 = ConceptParsing.isAChemConnectPrimitiveDataStructure(concept3);
		System.out.println(concept3 + ":   " + answer3);
		
		String concept4 = "dataset:ContactEmail";
		DatabaseObject object = new DatabaseObject("Administration","Public","Administration","1");
		ChemConnectPrimitiveDataStructure answer4 = ConceptParsing.createChemConnectPrimitiveDataStructure(object,concept4);
		System.out.println(concept3 + ":   " + answer4);
		
		
	}

}
