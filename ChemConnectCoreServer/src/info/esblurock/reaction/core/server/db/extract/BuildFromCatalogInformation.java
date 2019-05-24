package info.esblurock.reaction.core.server.db.extract;

import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.core.server.db.ReadWriteDatabaseObjectsWithSubobjects;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class BuildFromCatalogInformation {
	
	
	public static ChemConnectDataStructure getChemConnectDataStructure(String identifier, String structureS) {
		ChemConnectDataStructure structure = DatasetOntologyParsing
				.getChemConnectDataStructure(structureS);
		Map<String,DatabaseObject> map = getElementsOfCatalogObject(identifier,structureS);
		DatabaseObject obj = map.get(identifier);
		if(obj != null) {
			structure.setIdentifier(obj);
		}
		structure.setObjectMap(map);
		return structure;
	}
	
	
	public static Map<String,DatabaseObject> getElementsOfCatalogObject(String identifier, String dataElementName) {
		return ReadWriteDatabaseObjectsWithSubobjects.readCatalogObjectWithElements(dataElementName, identifier);
	}

}
