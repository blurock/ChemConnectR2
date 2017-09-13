package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.rdf.StoreObject;

public class InitializeCatalogDataStructuresYaml extends YamlFileInterpreterBase {
	public static String sourceKeyS = "";
	public static String inputKeyS = "Administration";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void interpret(Map map) throws IOException {
		ArrayList<ListOfElementInformation> results = ReadYamlDataset.ExtractListOfObjects(map);
		for (ListOfElementInformation info : results) {
			for (YamlDatasetInformation yaml : info) {
				DatabaseObject obj = yaml.getObject();
				StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), "1");
				WriteDatabaseObjectRDF.writeRDF(obj, store);
				store.finish();
			}
		}
	}
}
