package info.esblurock.reaction.ontology.test.dataset;

import java.util.ArrayList;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class TestGetMainObjects {

	@Test
	public void test() {
		List<String> lst = DatasetOntologyParsing.getMainDataStructures();
		System.out.println(lst);
		ArrayList<ClassificationInformation> clslst = new ArrayList<ClassificationInformation>();
		for(String dataElementName : lst) {
			ClassificationInformation clsinfo = DatasetOntologyParsing.getIdentificationInformation(dataElementName);
			System.out.println(clsinfo.toString());
			clslst.add(clsinfo);
		}
		
	}

}
