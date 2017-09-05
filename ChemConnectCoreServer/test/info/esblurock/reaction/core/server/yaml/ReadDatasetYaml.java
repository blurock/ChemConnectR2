package info.esblurock.reaction.core.server.yaml;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.ontology.test.dataset.ClassificationInformation;

public class ReadDatasetYaml {

	@Test
	public void test() {
		String fileS = "resources/contact/OrganizationInitialization.yaml";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		try {

			System.out.println("\n-----------------readYaml");
			ArrayList<ListOfElementInformation> results;
			results = ReadYamlDataset.readYaml(in);
			System.out.println("=============================================: " + results.size());
			for (ListOfElementInformation info : results) {
				System.out.println(info.toString() + "\n");
				System.out.println("Size: " + info.size());
				for (YamlDatasetInformation element : info) {
					String name = element.getObject().getClass().getSimpleName();
					System.out.println("-------------------------------------------------");
					System.out.println(name);
					InterpretData interpret = InterpretData.valueOf(name);
					Map<String, Object> map = interpret.createYamlFromObject(element.getObject());
					System.out.println(map);
					System.out.println("-------------------------------------------------");
				}

			}

			System.out.println("\n-----------------findListOfElementInformation");
			ListOfElementInformation element = ReadYamlDataset.findListOfElementInformation("dataset:Organization",
					null);
			System.out.println(element);

			ClassificationInformation classification = new ClassificationInformation(null, "dataset:ContactInfoData",
					"vcard:Contact", "ContactInfoData");

			System.out.println("\n-----------------findDatasetInformation");
			YamlDatasetInformation info = ReadYamlDataset.findDatasetInformation(classification, null);
			System.out.println(info);
		} catch (IOException e) {
			System.out.println("ReadDatasetYaml: ");
			e.printStackTrace();
		}

	}

}
