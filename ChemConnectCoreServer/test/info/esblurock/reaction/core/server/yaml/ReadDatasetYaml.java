package info.esblurock.reaction.core.server.yaml;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;

public class ReadDatasetYaml {

	@Test
	public void test() {
		String fileS = "resources/contact/OrganizationInitialization.yaml";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		try {
			String sourceID = "1";
			System.out.println("\n-----------------readYaml");
			ArrayList<ChemConnectDataStructureObject> results = ReadYamlDataset.readYaml(in,sourceID);
			for (ChemConnectDataStructureObject info : results) {
				System.out.println("=============================================: " + results.size());
				System.out.println(info.toString() + "\n");
				/*
				ChemConnectDataStructure structure = info.getChemconnect();
				for (DataElementInformation element : structure.getLinkedTos()) {
					String name = element.getDataElementName();
					System.out.println("-------------------------------------------------");
					System.out.println(name);
					InterpretData interpret = InterpretData.valueOf(name);
					Map<String, Object> map = interpret.createYamlFromObject(element.getChemconnectStructure());
					System.out.println(map);
					System.out.println("-------------------------------------------------");
				}
				*/
			}

			System.out.println("\n-----------------findListOfElementInformation");
			ListOfElementInformation element = ReadYamlDataset.findListOfElementInformation("dataset:Organization",
					null,sourceID);
			System.out.println(element);

			ClassificationInformation classification = new ClassificationInformation(null, null,"dataset:ContactInfoData",
					"vcard:Contact", "ContactInfoData");

			System.out.println("\n-----------------findDatasetInformation");
			DatasetInformationFromOntology info = ReadYamlDataset.findDatasetInformation(classification, null,sourceID);
			System.out.println(info);
		} catch (IOException e) {
			System.out.println("ReadDatasetYaml: ");
			e.printStackTrace();
		}

	}

}
