package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ListOfElementInformation;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.io.rdf.StoreObject;
import info.esblurock.reaction.ontology.initialization.ReadYamlDataset;

public class InitializeCatalogDataStructuresYaml extends YamlFileInterpreterBase {
	public static String sourceKeyS = "";
	public static String inputKeyS = "Administration";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void interpret(Map map, String sourceID) throws IOException {
		System.out.println("InitializeCatalogDataStructuresYaml: " + sourceID);
		ArrayList<ListOfElementInformation> results = ReadYamlDataset.ExtractListOfObjects(map,sourceID);
		for (ListOfElementInformation info : results) {
			for (DatasetInformationFromOntology yaml : info) {
				DatabaseObject obj = yaml.getObject();
				StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), sourceID);
				WriteDatabaseObjectRDF.writeRDF(obj, store);
				store.finish();
			}
		}
	}
}
