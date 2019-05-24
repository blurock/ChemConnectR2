package info.esblurock.reaction.core.server.gcs;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class FileTypeConceptTest {

	@Test
	public void test() {
		String contentType = ConceptParsing.getContentType(StandardDatasetMetaData.yamlFileType);
		System.out.println(StandardDatasetMetaData.yamlFileType + ":  " + contentType);

		
		String type1 = ConceptParsing.getStructureFromFileType("dataset:FileTypeSpreadSheetCSV");
		System.out.println("dataset:FileTypeSpreadSheetCSV" + ":  " + type1);
		
		String type2 = ConceptParsing.getStructureFromFileType("dataset:FileTypeSpreadSheetSpaceDelimited");
		System.out.println("dataset:FileTypeSpreadSheetSpaceDelimited" + ":  " + type2);
		
		String type3 = ConceptParsing.getStructureFromFileType("dataset:FileYamlOrganization");
		System.out.println("dataset:FileYamlOrganization" + ":  " + type3);
		
		String type4 = ConceptParsing.getStructureFromFileType("dataset:FileYamlChemConnectMethodology");
		System.out.println("dataset:FileYamlChemConnectMethodology" + ":  " + type4);
		
		
		
	}

}
