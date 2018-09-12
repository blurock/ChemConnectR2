package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ArrayListDataObjectTest {

	@Test
	public void test() {
		System.out.println("dataset:ListOfTitles:  " + ConceptParsing.isAArrayListDataObject("dataset:ListOfTitles"));
		System.out.println("dataset:ListOfValuesAsString:  " + ConceptParsing.isAArrayListDataObject("dataset:ListOfValuesAsString"));
		System.out.println("dataset:ValueAsString:  " + ConceptParsing.isAArrayListDataObject("dataset:ValueAsString"));
	}

}
