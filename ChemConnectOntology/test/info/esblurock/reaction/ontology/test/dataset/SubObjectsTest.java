package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class SubObjectsTest {

	@Test
	public void test() {
		String userAccountS = "dataset:UserAccount";
		Set<String> sub1 = DatasetOntologyParsing.SubObjectsOfDataStructure(userAccountS);
		System.out.println(userAccountS + ":  " + sub1);
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
