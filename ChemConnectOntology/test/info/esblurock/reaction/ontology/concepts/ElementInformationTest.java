package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ElementInformationTest {

	@Test
	public void test() {
		
		ChemConnectCompoundDataStructure structure = DatasetOntologyParsing.subElementsOfStructure("dataset:DatabasePerson");
		System.out.println(structure.toString());

		ChemConnectCompoundDataStructure structure2 = DatasetOntologyParsing.subElementsOfStructure("dataset:PersonalDescription");
		System.out.println(structure2.toString());
		
	}

}
