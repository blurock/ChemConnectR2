package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.ontology.DatasetOntologyParsing;


public class GetStructureFromDataStructureTest {

	@Test
	public void test() {
		String dataset1 = DatasetOntologyParsing.getStructureFromDataStructure("DatasetImage");
		System.out.println("DatasetImage: " + dataset1);
	}

}
