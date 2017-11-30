package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class TestSubElements {

	@Test
	public void test() {
		//ChemConnectCompoundDataStructure subs = DatasetOntologyParsing.subElementsOfStructure("dataset:CatalogInformation");
		//System.out.println(subs.toString());
		
		ChemConnectCompoundDataStructure subs = DatasetOntologyParsing.subElementsOfStructure("dataset:Consortium");
		System.out.println(subs.toString());
		ChemConnectCompoundDataStructure subs1 = DatasetOntologyParsing.subElementsOfStructure("dataset:NameOfPerson");
		System.out.println(subs1.toString());
		
	}

}
