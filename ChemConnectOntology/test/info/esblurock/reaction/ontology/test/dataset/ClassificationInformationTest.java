package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ClassificationInformationTest {
/*
 *  dataset:Organization
	ID: dataset:Organization: dataset:Organization  (Organization)
	dataset:UserInformation
	returned null
 */
	@Test
	public void test() {
		System.out.println("dataset:Organization");
		ClassificationInformation info1 = DatasetOntologyParsing.getIdentificationInformation("dataset:Organization");
		System.out.println(info1.toString());
		System.out.println("dataset:UserInformation");
		ClassificationInformation info2 = DatasetOntologyParsing.getIdentificationInformation("dataset:UserInformation");
		if(info2 != null) {
			System.out.println(info2.toString());
		} else {
			System.out.println("returned null");
		}
		
	}

}
