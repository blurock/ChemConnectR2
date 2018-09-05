package info.esblurock.reaction.core.server.gcs;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class FileTypeConceptTest {

	@Test
	public void test() {
		String contentType = ConceptParsing.getContentType(StandardDatasetMetaData.yamlFileType);
		System.out.println(StandardDatasetMetaData.yamlFileType + ":  " + contentType);
	}

}
