package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.io.rdf.StoreObject;
import info.esblurock.reaction.ontology.initialization.ReadYamlDataset;

public class InterpretCatalogDataStructuresYaml extends YamlFileInterpreterBase {
	public static String sourceKeyS = "";
	public static String inputKeyS = "Administration";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void interpret(Map map, String sourceID) throws IOException {
		System.out.println("InitializeCatalogDataStructuresYaml: " + sourceID);
		ArrayList<ChemConnectDataStructureObject> results = ReadYamlDataset.ExtractListOfObjects(map,sourceID);
		System.out.println("InitializeCatalogDataStructuresYaml: \n" + results);
		for (ChemConnectDataStructureObject info : results) {
			DatabaseObjectHierarchy hierarchy = info.getObjecthierarchy();
			writeHierarchy(hierarchy,sourceID);
			}
	}
	private void writeHierarchy(DatabaseObjectHierarchy hierarchy, String sourceID) throws IOException {
		DatabaseObject obj = hierarchy.getObject();
		StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), sourceID);
		System.out.println("Object to store:\n" + obj.toString());
		store.store(obj);
		WriteDatabaseObjectRDF.writeRDF(obj, store);
		store.finish();
		for(DatabaseObjectHierarchy subhiearchy : hierarchy.getSubobjects() ) {
			writeHierarchy(subhiearchy,sourceID);
		}
	}
}
