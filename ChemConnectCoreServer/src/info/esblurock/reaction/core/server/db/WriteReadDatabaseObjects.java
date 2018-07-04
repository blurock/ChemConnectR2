package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryResults;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.db.QueryFactory;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class WriteReadDatabaseObjects {

	public static void updateSourceID(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject object = objecthierarchy.getObject();
		String sourceID = QueryBase.getDataSourceIdentification(object.getOwner());
		updateSourceID(sourceID, objecthierarchy);
	}
	public static void updateSourceID(String sourceID, DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject object = objecthierarchy.getObject();
		object.setSourceID(sourceID);
		for(DatabaseObjectHierarchy subs : objecthierarchy.getSubobjects()) {
			updateSourceID(sourceID,subs);
		}
	}
	
	public static Set<String> getIDsOfAllDatabaseObjects(String user, String classType) throws IOException {
		String name = DatasetOntologyParsing.getChemConnectDirectTypeHierarchy(classType);
		System.out.println("dataset:Organization    \t" + name);
		InterpretData interpret = InterpretData.valueOf(name);
		System.out.println(interpret.canonicalClassName());
		ListOfQueries queries = QueryFactory.accessQueryForUser(interpret.canonicalClassName(), user, null);
		SetOfQueryResults results;
		Set<String> ids = new HashSet<String>();
		try {
			results = QueryBase.StandardSetOfQueries(queries);
			List<DatabaseObject> objs = results.retrieveAndClear();
			for(DatabaseObject obj : objs) {
				ids.add(obj.getIdentifier());
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classType);
		}
		return ids;
	}
	public static ArrayList<DatabaseObjectHierarchy> getDatabaseObjectHierarchyFromIDs(String classType, Set<String> ids) {
		System.out.println("Calling getDatabaseObjectHierarchyFromIDs classtype='" + classType + "'");
		ArrayList<DatabaseObjectHierarchy> objects = new ArrayList<DatabaseObjectHierarchy>();
		for(String id:ids) {
			System.out.println("getDatabaseObjectHierarchyFromIDs: classtype='" + classType + "'   ID=" + id);
			DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject(id, classType);
			System.out.println("getDatabaseObjectHierarchyFromIDs: done fetching  classtype='" + classType + "'   ID=" + id);
			System.out.println("getDatabaseObjectHierarchyFromIDs: ID= " + id + "\n" + readhierarchy.toString());
			objects.add(readhierarchy);
		}
		System.out.println("getDatabaseObjectHierarchyFromIDs: done" + objects.size());
		return objects;
	}
	public static ArrayList<DatabaseObjectHierarchy> getAllDatabaseObjectHierarchyForUser(String user, String classType) throws IOException {
		Set<String> ids = getIDsOfAllDatabaseObjects(user,classType);
		System.out.println("getAllDatabaseObjectHierarchyForUser:  '" + classType + "'  with IDs: "+ ids);
		ArrayList<DatabaseObjectHierarchy> objects = getDatabaseObjectHierarchyFromIDs(classType,ids);
		System.out.println("getAllDatabaseObjectHierarchyForUser: done: " + objects.size());
		
		return objects;
	}
	public static void writeChemConnectDataStructureObject(ChemConnectDataStructureObject object) {
		writeDatabaseObjectHierarchy(object.getObjecthierarchy());
	}

	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchyWithTransaction(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseWriteBase. writeTransactionWithoutObjectWrite(objecthierarchy.getObject());
		return writeDatabaseObjectHierarchy(objecthierarchy);
	}
	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject topobject = objecthierarchy.getObject();
		try {
			QueryBase.getDatabaseObjectFromIdentifier(topobject.getClass().getCanonicalName(),topobject.getIdentifier());
			updateDatabaseObjectHierarchy(objecthierarchy);
		} catch (IOException e) {
			writeDatabaseObjectHierarchyRecursive(objecthierarchy);
		}
		return objecthierarchy;
	}
		
	public static void writeDatabaseObjectHierarchyRecursive(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseWriteBase.writeDatabaseObject(objecthierarchy.getObject());
		for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
			writeDatabaseObjectHierarchyRecursive(subhierarchy);
		}
	}

	public static void updateDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		ArrayList<DatabaseObject> lst = new ArrayList<DatabaseObject>();
		Map<String,DatabaseObject> map = new HashMap<String,DatabaseObject>();
		collectDatabaseObjectsInHierarchy(objecthierarchy,lst,map);
		Map<Key<DatabaseObject>,DatabaseObject> result = ObjectifyService.ofy().load().entities(lst);
		System.out.println(result.keySet());
		ArrayList<DatabaseObject> newobjs = new ArrayList<DatabaseObject>();
		for(Key<DatabaseObject> id: result.keySet()) {
			DatabaseObject dbobj = result.get(id);
			DatabaseObject update = map.get(dbobj.getIdentifier());
			dbobj.fill(update);
			newobjs.add(dbobj);
		}
		ObjectifyService.ofy().save().entities(newobjs).now();
	}
	
	public static void collectDatabaseObjectsInHierarchy(DatabaseObjectHierarchy objecthierarchy, ArrayList<DatabaseObject> lst, 
			Map<String,DatabaseObject> map) {
		DatabaseObject obj = objecthierarchy.getObject();
		lst.add(obj);
		map.put(obj.getIdentifier(),obj);
		for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
			collectDatabaseObjectsInHierarchy(subhierarchy,lst,map);
		}
	}
	

	@SuppressWarnings("unchecked")
	public static void readChemConnectDataStructureObject(String elementType, String identifier) throws IOException {
		System.out.println("------------------------------------------");
		ChemConnectDataStructure chemconnect = DatasetOntologyParsing.getChemConnectDataStructure(elementType);
		ClassificationInformation classification = chemconnect.getClassification();
		InterpretData interpret = InterpretData.valueOf(classification.getDataType());
		String classname = interpret.canonicalClassName();
		DatabaseObject object = QueryBase.getDatabaseObjectFromIdentifier(classname, identifier);
		Map<String, Object> map = interpret.createYamlFromObject(object);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(object);
		System.out.println("------------------------------------------");
		System.out.println(chemconnect.getRecords().size());
		for (DataElementInformation info : chemconnect.getRecords()) {
			System.out.println("Information: " + info);
			System.out.println("Information: " + map);
			Object mapobject = map.get(info.getIdentifier());
			System.out.println(mapobject.getClass().getSimpleName());
			if (mapobject.getClass().getSimpleName().compareTo("String") == 0) {
				String id = (String) mapobject;
				String chemstructure = info.getChemconnectStructure();
				InterpretData subinterpret = InterpretData.valueOf(chemstructure);
				String canonical = subinterpret.canonicalClassName();
				DatabaseObject obj = null;
				try {
					obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
					DatabaseObjectHierarchy subhierarchy = new DatabaseObjectHierarchy(obj);
					hierarchy.addSubobject(subhierarchy);
					Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
					System.out.println("\t\tMap: " + submap);
					readChemConnectCompoundObject(info, submap, subhierarchy);
				} catch (IOException io) {

				}

			} else {
				System.out.println("MultipleObject" + mapobject);
				String chemstructure = info.getChemconnectStructure();
				InterpretData subinterpret = InterpretData.valueOf(chemstructure);
				String canonical = subinterpret.canonicalClassName();
				ArrayList<String> lst = (ArrayList<String>) mapobject;
				DatabaseObject obj = null;
				for(String name : lst) {
					obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, name);
					DatabaseObjectHierarchy subhierarchy = new DatabaseObjectHierarchy(obj);
					hierarchy.addSubobject(subhierarchy);
					Map<String, Object> submap = subinterpret.createYamlFromObject(obj);
					System.out.println("\t\tMap: " + submap);
					readChemConnectCompoundObject(info, submap, subhierarchy);
				}
			}
		}

		System.out.println("Hierarchy: \n" + hierarchy);
	}

	@SuppressWarnings("unchecked")
	private static void readChemConnectCompoundObject(DataElementInformation info, Map<String, Object> submap,
			DatabaseObjectHierarchy subhierarchy) {
		System.out.println("readChemConnectCompoundObject: " + info);
		System.out.println("readChemConnectCompoundObject: " + info.getDataElementName());
		ChemConnectCompoundDataStructure subs = DatasetOntologyParsing
				.subElementsOfStructure(info.getDataElementName());
		System.out.println(subs);
		for (DataElementInformation element : subs) {
			if (DatasetOntologyParsing.isChemConnectPrimitiveDataStructure(element.getDataElementName()) == null) {
				System.out.println("Compound Object: " + element);
				if (element.isSinglet()) {
					String idlabel = element.getIdentifier();
					String id = (String) submap.get(idlabel);
					if (id != null) {
						String chemstructure = element.getChemconnectStructure();
						InterpretData subinterpret = InterpretData.valueOf(chemstructure);
						String canonical = subinterpret.canonicalClassName();
						DatabaseObject obj;
						try {
							obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
							DatabaseObjectHierarchy next = new DatabaseObjectHierarchy(obj);
							subhierarchy.addSubobject(next);
						} catch (IOException e) {
							System.out.println("not found");
						}
					}
				} else {
					String idlabel = element.getIdentifier();
					ArrayList<String> lst = (ArrayList<String>) submap.get(idlabel);
					for(String id : lst) {
						String chemstructure = element.getChemconnectStructure();
						InterpretData subinterpret = InterpretData.valueOf(chemstructure);
						String canonical = subinterpret.canonicalClassName();
						DatabaseObject obj;
						try {
							obj = QueryBase.getDatabaseObjectFromIdentifier(canonical, id);
							DatabaseObjectHierarchy next = new DatabaseObjectHierarchy(obj);
							subhierarchy.addSubobject(next);
						} catch (IOException e) {
							System.out.println("not found");
						}						
					}
				}

			} else {
				System.out.println("Simple: " + element.getDataElementName());
			}

		}
	}

}
