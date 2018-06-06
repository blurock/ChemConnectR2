package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.Map;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.io.dataset.InterpretData;


public class TestChemConnectRecordInformation {

	@Test
	public void test() {
		DescriptionDataData obj = new DescriptionDataData();
		String structureS = DatasetOntologyParsing.dataTypeOfStructure(obj);
		String objecttype = obj.getClass().getSimpleName();
		System.out.println("Object Type:  " + objecttype);
		System.out.println("Structure:  " + structureS);
		
		InterpretData interpret = InterpretData.valueOf(objecttype);
		try {
			Map<String,Object> mapping = interpret.createYamlFromObject(obj);
			ChemConnectCompoundDataStructure structure = DatasetOntologyParsing.subElementsOfStructure(structureS);
			System.out.println("Structure:\n" + structure.toString());

			ChemConnectRecordInformation info = new ChemConnectRecordInformation(obj,structureS,structure,mapping);
			
			System.out.println("Info: \n" + info.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
