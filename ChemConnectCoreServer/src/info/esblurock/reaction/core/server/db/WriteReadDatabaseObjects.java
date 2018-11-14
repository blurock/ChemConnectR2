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
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryResults;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.db.QueryFactory;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;;

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
		InterpretData interpret = InterpretData.valueOf(name);
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
			throw new IOException("getIDsOfAllDatabaseObjects Class not found: " + classType);
		}
		return ids;
	}
	
	public static ArrayList<DatabaseObjectHierarchy> getDatabaseObjectHierarchyFromIDs(String classType, Set<String> ids) {
		ArrayList<DatabaseObjectHierarchy> objects = new ArrayList<DatabaseObjectHierarchy>();
		for(String id:ids) {
			DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject(id, classType);
			objects.add(readhierarchy);
		}
		return objects;
	}
	public static ArrayList<DatabaseObjectHierarchy> getAllDatabaseObjectHierarchyForUser(String user, String classType) throws IOException {
		Set<String> ids = getIDsOfAllDatabaseObjects(user,classType);
		ArrayList<DatabaseObjectHierarchy> objects = getDatabaseObjectHierarchyFromIDs(classType,ids);
		return objects;
	}
	public static void writeChemConnectDataStructureObject(ChemConnectDataStructureObject object) {
		writeDatabaseObjectHierarchy(object.getObjecthierarchy());
	}

	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchyWithTransaction(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseWriteBase.writeTransactionWithoutObjectWrite(objecthierarchy.getObject());
		return writeDatabaseObjectHierarchy(objecthierarchy);
	}
	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		writeDatabaseObjectHierarchyRecursive(objecthierarchy);
		return objecthierarchy;
	}
		
	public static void writeDatabaseObjectHierarchyRecursive(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject topobject = objecthierarchy.getObject();
		DatabaseWriteBase.writeDatabaseObject(topobject);
		for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
			writeDatabaseObjectHierarchyRecursive(subhierarchy);
		}
	}

	public static void updateDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		ArrayList<DatabaseObject> lst = new ArrayList<DatabaseObject>();
		Map<String,DatabaseObject> map = new HashMap<String,DatabaseObject>();
		ArrayList<DatabaseObject> newobjs = new ArrayList<DatabaseObject>();
		collectDatabaseObjectsInHierarchy(objecthierarchy,newobjs,lst,map);
		ObjectifyService.ofy().save().entities(newobjs).now();
		/*
		System.out.println("--------------------------------"  + newobjs.size());
		for(DatabaseObject obj: newobjs) {
			System.out.println("ID: '" + obj.getIdentifier() + "'  Key: " + obj.getKey());
		}
		
		System.out.println("--------------------------------");
		*/
		Map<Key<DatabaseObject>,DatabaseObject> result = ObjectifyService.ofy().load().entities(lst);
		//System.out.println(result.keySet());
		ArrayList<DatabaseObject> objs = new ArrayList<DatabaseObject>();
		
		for(Key<DatabaseObject> id: result.keySet()) {
			DatabaseObject dbobj = result.get(id);
			DatabaseObject update = map.get(dbobj.getIdentifier());
			dbobj.fill(update);
			objs.add(dbobj);
		}
		ObjectifyService.ofy().save().entities(objs).now();
	}
	
	public static void collectDatabaseObjectsInHierarchy(DatabaseObjectHierarchy objecthierarchy, 
			ArrayList<DatabaseObject> newobjs, 
			ArrayList<DatabaseObject> lst, 
			Map<String,DatabaseObject> map) {
		DatabaseObject obj = objecthierarchy.getObject();
		if(obj.getKey() == null) {
			newobjs.add(obj);
		} else {
			lst.add(obj);
			map.put(obj.getIdentifier(),obj);
			for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
				collectDatabaseObjectsInHierarchy(subhierarchy,newobjs,lst,map);
			}
			ObjectifyService.ofy().save().entities(newobjs).now();
		}
	}
	
	public static HierarchyNode getIDHierarchyFromDataCatalogID(String user,
			String basecatalog, String catalog) throws IOException {
		String classname = DataCatalogID.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();

		QueryPropertyValue value1 = new QueryPropertyValue("CatalogBaseName",basecatalog);
		QueryPropertyValue value2 = new QueryPropertyValue("DataCatalog",catalog);
		values.add(value1);
		values.add(value2);
		ListOfQueries queries = QueryFactory.accessQueryForUser(classname, user, values);
		SetOfQueryResults results;
		HierarchyNode topnode = null;
		try {
			results = QueryBase.StandardSetOfQueries(queries);
			List<DatabaseObject> objs = results.retrieveAndClear();
			topnode = hierarchialList(objs);
		} catch (ClassNotFoundException e) {
			throw new IOException("getIDHierarchyFromDataCatalogID Class not found: " + classname);
		}
		return topnode;
	}
	private static HierarchyNode hierarchialList(List<DatabaseObject> objs) {
		HierarchyNode topnode = new HierarchyNode("Database Objects");
		for(DatabaseObject obj : objs) {
			DataCatalogID datid = (DataCatalogID) obj;
			HierarchyNode subnode = new HierarchyNode(datid.getParentLink(),datid.getSimpleCatalogName());
			topnode.addSubNode(subnode);
		}
		return topnode;
	}
	
	public static HierarchyNode getIDHierarchyFromDataCatalogAndUser(String user,String datacatalog) throws IOException {
		String classname = DataCatalogID.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("owner",user);
		values.add(value1);
		QueryPropertyValue value2 = new QueryPropertyValue("DataCatalog",datacatalog);
		values.add(value2);
		QuerySetupBase ownerquery = new QuerySetupBase(user,classname, values);
		Set<String> ids = new HashSet<String>();
		HierarchyNode topnode = null;
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(ownerquery);
			List<DatabaseObject> objs = result.getResults();
			for(DatabaseObject obj : objs) {
				DataCatalogID datid = (DataCatalogID) obj;
				ids.add(datid.getParentLink());
			}
			topnode = ParseUtilities.parseIDsToHierarchyNode("Objects",ids,true);
		} catch (ClassNotFoundException e) {
			throw new IOException("getIDHierarchyFromDataCatalogIDAndClassType DataCatalog Class not found: " + datacatalog);
		}
		return topnode;
		
	}
	
	
	
	public static HierarchyNode getIDHierarchyFromDataCatalogIDAndClassType(String user,
			String catalogbasename, String classtype) throws IOException {
		String classname = DataCatalogID.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		DataElementInformation info = DatasetOntologyParsing.getSubElementStructureFromIDObject(classtype);
		String suffix = info.getSuffix();
		QueryPropertyValue value2 = new QueryPropertyValue("CatalogBaseName",catalogbasename);
		values.add(value2);
		ListOfQueries queries = QueryFactory.accessQueryForUser(classname, user, values);
		SetOfQueryResults results;
		Set<String> ids = new HashSet<String>();
		HierarchyNode topnode = null;
		try {
			results = QueryBase.StandardSetOfQueries(queries);
			List<DatabaseObject> objs = results.retrieveAndClear();
			for(DatabaseObject obj : objs) {
				DataCatalogID datid = (DataCatalogID) obj;
				String parent = datid.getParentLink();
				if(parent.endsWith(suffix)) {
					ids.add(datid.getParentLink());
				}
			}
			topnode = ParseUtilities.parseIDsToHierarchyNode("Objects",ids,true);
		} catch (ClassNotFoundException e) {
			throw new IOException("getIDHierarchyFromDataCatalogIDAndClassType Class not found: " + classtype);
		}
		return topnode;
	}
	
	public static void deleteObject(String id, String type) {
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(id, type);
		if(hierarchy != null) {
			deleteHierarchy(hierarchy);
		}
	}
	public static void deleteHierarchy(DatabaseObjectHierarchy hierarchy) {
		for(DatabaseObjectHierarchy sub : hierarchy.getSubobjects()) {
			deleteHierarchy(sub);
		}
		ObjectifyService.ofy().delete().entity(hierarchy.getObject());
	}

}
