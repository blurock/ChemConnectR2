package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
//import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class SimpleReadDatasetInformation {

	@Test
	public void test() {
		System.out.println("Database Test 1");
		//OntModel model = OntologyBase.Util.getDatabaseOntology();
		System.out.println("Database Test 2");

		List<String> lst = DatasetOntologyParsing.getMainDataStructures();
		System.out.println(lst);
		for (String structure : lst) {
			System.out.println("Data Structure: '" + structure + "'");
			List<DataElementInformation> subs = DatasetOntologyParsing.subElementsOfStructure(structure);
			for (DataElementInformation element : subs) {
				System.out.println("\tSubstructure: '" + element + "'");
				List<DataElementInformation> subelements = DatasetOntologyParsing
						.subElementsOfStructure(element.getDataElementName());
				for (DataElementInformation info : subelements) {
					System.out.println("\t\tElement: " + info.toString());
					DataElementInformation dataelement = new DataElementInformation(info.getDataElementName(), null,
							true, 0, null, null);
					ClassificationInformation classid = DatasetOntologyParsing.getIdentificationInformation(null,
							dataelement);
					if (classid != null) {
						System.out.println("\t\t\tClassificationID: " + classid.toString());
					}
				}
			}

		}
		
	}

}
