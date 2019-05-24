package info.esblurock.reaction.ontology.concepts;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class DataTypeTest {

	@Test
	public void test() {
		DescriptionDataData data = new DescriptionDataData();
		String dataS = DatasetOntologyParsing.dataTypeOfStructure(data);
		System.out.println("DescriptionDataData: " + dataS);
	}

}
