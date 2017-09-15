package info.esblurock.reaction.core.server.map;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.dataset.InterpretData;

public class TestMap {

	@Test
	public void test() {
		String sourceID = "1";
		try {
			String fileS = "resources/contact/OrganizationInitialization.yaml";
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);

			System.out.println("\n-----------------readYaml");
			ArrayList<ListOfElementInformation> results = ReadYamlDataset.readYaml(in,sourceID);
			System.out.println("-----------------");
			Organization org = null;
			ListOfElementInformation info = results.get(0);
			for (YamlDatasetInformation element : info) {
				System.out.println("element: " + element.getDataelement());
				if (element.getDataelement().compareTo("dataset:Organization") == 0) {
					org = (Organization) element.getObject();
				}
			}
			System.out.println("----------------------------");
			System.out.println("Organization: " + org);
			InterpretData interpret = InterpretData.valueOf("Organization");
			Map<String, Object> map = interpret.createYamlFromObject(org);
			System.out.println(map);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

}
