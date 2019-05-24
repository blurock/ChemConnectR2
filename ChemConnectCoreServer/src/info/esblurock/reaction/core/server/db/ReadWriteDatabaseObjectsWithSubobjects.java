package info.esblurock.reaction.core.server.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class ReadWriteDatabaseObjectsWithSubobjects {

	public static Map<String,DatabaseObject> readCatalogObjectWithElements(String type, String identifier) {
		
		System.out.println("readCatalogObjectWithElements: " + type + "  -   " + identifier);
		
		Map<String,DatabaseObject> map = new HashMap<String,DatabaseObject>();
		ClassificationInformation info = DatasetOntologyParsing.getIdentificationInformation(type);
		System.out.println("readCatalogObjectWithElements: " + info);
		addDatabaseObject(identifier,info.getDataType(),info.getIdentifier(),map);
		return map;
	}
	public static void addDatabaseObject(String identifier, String structure, String elementName, Map<String,DatabaseObject> map) {
		DatabaseObject obj = null;
		try {
			System.out.println("addDatabaseObject");
			System.out.println("addDatabaseObject  identifier: " + identifier);
			System.out.println("addDatabaseObject structure:   " + structure);
			InterpretData interpret = InterpretData.valueOf(structure);
			obj = interpret.readElementFromDatabase(identifier);
			System.out.println("addDatabaseObject: " + obj);
			if(obj != null) {
				System.out.println("addDatabaseObject: " + obj.getClass().getCanonicalName());
				map.put(identifier,obj);
				readObjectAndSubObjects(obj,structure,elementName,map);
			}
		} catch(Exception ex) {
			System.out.println("addDatabaseObject: Didn't find InterpretData: " + structure);
			System.out.println("addDatabaseObject: Didn't find InterpretData: " + ex.toString());
				}
	}
	@SuppressWarnings("unchecked")
	public static void readObjectAndSubObjects(DatabaseObject object, 
			String structure, String elementName,
			Map<String,DatabaseObject> map) {
		System.out.println("readObjectAndSubObjects");
		System.out.println("readObjectAndSubObjects: structure:  " + structure);
		System.out.println("readObjectAndSubObjects: elementName " + elementName);
				try {
			InterpretData interpret = InterpretData.valueOf(structure);
			Map<String, Object> mapping = interpret.createYamlFromObject(object);
			System.out.println("readObjectAndSubObjects  map:\n" + mapping.toString());
			
			ChemConnectCompoundDataStructure subs 
				= DatasetOntologyParsing.subElementsOfStructure(elementName);
			System.out.println("readObjectAndSubObjects  subs:\n" + subs.toString());
			for(DataElementInformation subelement : subs) {
				String identifier = subelement.getIdentifier();
				if(subelement.isSinglet()) {
					String id = (String) mapping.get(identifier);
					addDatabaseObject(id,subelement.getChemconnectStructure(),subelement.getDataElementName(),map);
				} else {
					String multid = object.getIdentifier() + "-" + subelement.getSuffix();
					System.out.println("readObjectAndSubObjects multiple ID: " + multid);
					ArrayList<String> ids = (ArrayList<String>) mapping.get(identifier);
					DatabaseObject mobj = new DatabaseObject(object);
					mobj.setIdentifier(multid);
					ChemConnectCompoundMultiple multiple = new ChemConnectCompoundMultiple(mobj,subelement.getChemconnectStructure());
					map.put(multid, multiple);
					for(String id: ids) {
						addDatabaseObject(id,subelement.getChemconnectStructure(),subelement.getDataElementName(),map);
					}
				}
			}
		} catch(Exception ex) {
			System.out.println("readObjectAndSubObjects: Didn't find InterpretData: " + structure);
			System.out.println("readObjectAndSubObjects: Didn't find InterpretData: " + ex.toString());
			
		}
	}
	
}
