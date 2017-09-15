package info.esblurock.reaction.ontology.test.dataset;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class SubObjectsTest {

	@Test
	public void test() {
		String consortiumS = "dataset:Consortium";
		Set<String> sub2 = DatasetOntologyParsing.SubObjectsOfDataStructure(consortiumS);
		System.out.println(consortiumS + ":  " + sub2);

		
		String organizationS = "dataset:Organization";
		Set<String> subelements = DatasetOntologyParsing.SubObjectsOfDataStructure(organizationS);
		System.out.println(organizationS + ":  " + subelements);
		String userS = "dataset:DatabaseUser";
		Set<String> subelements2 = DatasetOntologyParsing.SubObjectsOfDataStructure(userS);
		System.out.println(userS + ":  " + subelements2);



	}

}
