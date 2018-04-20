package info.esblurock.reaction.ontology;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class DataElementTest {

	@Test
	public void test() {
		DataElementInformation subelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject("dataset:ContactInfoData");
		System.out.println(subelement);
	}

}
