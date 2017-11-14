package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class TestSubElements {

	@Test
	public void test() {
		List<DataElementInformation> subs = DatasetOntologyParsing.subElementsOfStructure("dataset:Consortium");
		for(DataElementInformation element : subs) {
			System.out.println(element);
		}
	
	}

}
