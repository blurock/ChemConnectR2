package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
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
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;;

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
		System.out.println("getIDHierarchyFromDataCatalogID user" + user);
		System.out.println("getIDHierarchyFromDataCatalogID basecatalog" + basecatalog);
		System.out.println("getIDHierarchyFromDataCatalogID catalog" + catalog);
		
		
		
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
			System.out.println("getIDHierarchyFromDataCatalogID results:\n" + objs);
			
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

	public static ArrayList<NameOfPerson> getIDHierarchyFromFamilyNameAndUser(String user,
			String familyname) throws IOException {
		String classname = NameOfPerson.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("owner",user);
		values.add(value1);
		QuerySetupBase ownerquery = new QuerySetupBase(user,classname, values);
		System.out.println(ownerquery.toString("getIDHierarchyFromFamilyNameAndUser: "));
		SingleQueryResult result;
		ArrayList<NameOfPerson> namelst = new ArrayList<NameOfPerson>();
		try {
			result = QueryBase.StandardQueryResult(ownerquery);
			List<DatabaseObject> objs = result.getResults();
			StringCompareElement[] lst = new StringCompareElement[objs.size()];
			int count = 0;
			for(DatabaseObject obj : objs) {
				NameOfPerson name = (NameOfPerson) obj;
				StringCompareElement element = new StringCompareElement(familyname,name);
				lst[count++] = element;
			}
			Arrays.sort(lst);
			for(int i=0; i<lst.length;i++) {
				namelst.add(lst[i].getNameOfPerson());
			}
		} catch (ClassNotFoundException e) {
			throw new IOException(e.toString());
		}
		return namelst;
	}
	public static HierarchyNode getIDHierarchyFromDataCatalogAndUser(String user,String datacatalog, String classtype) throws IOException {

		System.out.println("getIDHierarchyFromDataCatalogAndUser: user: " + user);
		System.out.println("getIDHierarchyFromDataCatalogAndUser: datacatalog: " + datacatalog);
		
		String classname = DataCatalogID.class.getCanonicalName();
		String suffix = null;
		if(classtype != null) {
			DataElementInformation info = DatasetOntologyParsing.getSubElementStructureFromIDObject(classtype);
			suffix = info.getSuffix();
		}
		System.out.println("getIDHierarchyFromDataCatalogAndUser: suffix: " + suffix);
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("owner",user);
		values.add(value1);
		QueryPropertyValue value2 = new QueryPropertyValue("DataCatalog",datacatalog);
		values.add(value2);
		QuerySetupBase ownerquery = new QuerySetupBase(user,classname, values);
		System.out.println(ownerquery.toString("getIDHierarchyFromDataCatalogAndUser: "));
		Set<String> ids = new HashSet<String>();
		HierarchyNode topnode = null;
		try {
			ownerquery.setAccess(user);
			SingleQueryResult result1 = QueryBase.StandardQueryResult(ownerquery);
			ownerquery.setAccess(MetaDataKeywords.publicAccess);
			SingleQueryResult result2 = QueryBase.StandardQueryResult(ownerquery);
			
			List<DatabaseObject> objs1 = result1.getResults();
			List<DatabaseObject> objs2 = result2.getResults();
			System.out.println("getIDHierarchyFromDataCatalogAndUser: result: \n" + objs1);
			System.out.println("getIDHierarchyFromDataCatalogAndUser: result: \n" + objs2);
			
			
			for(DatabaseObject obj : objs1) {
				DataCatalogID datid = (DataCatalogID) obj;
				String parent = datid.getParentLink();
				if(suffix == null) {
					ids.add(parent);
				} else {
					if(parent.endsWith(suffix)) {
						ids.add(parent);
					}					
				}
			}
			for(DatabaseObject obj : objs2) {
				DataCatalogID datid = (DataCatalogID) obj;
				String parent = datid.getParentLink();
				if(suffix == null) {
					ids.add(parent);
				} else {
					if(parent.endsWith(suffix)) {
						ids.add(parent);
					}					
				}
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
		System.out.println("getIDHierarchyFromDataCatalogIDAndClassType suffix: " + suffix);
		QueryPropertyValue value2 = new QueryPropertyValue("CatalogBaseName",catalogbasename);
		values.add(value2);
		ListOfQueries queries = QueryFactory.accessQueryForUser(classname, user, values);
		SetOfQueryResults results;
		Set<String> ids = new HashSet<String>();
		HierarchyNode topnode = null;
		try {
			results = QueryBase.StandardSetOfQueries(queries);
			System.out.println("getIDHierarchyFromDataCatalogIDAndClassType results: \n" + results);
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
/*
 * The lastest blob store write takes precedence
 * on overwrite, previous writes are deleted.
 * 
 */
	public static void deletePreviousBlobStorageMoves(GCSBlobFileInformation target) throws IOException {
		String classname = GCSBlobFileInformation.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("filename",target.getFilename());
		values.add(value1);
		QueryPropertyValue value2 = new QueryPropertyValue("path",target.getPath());
		values.add(value2);
		QuerySetupBase query = new QuerySetupBase(target.getOwner(),classname, values);
		System.out.println(query.toString("deletePreviousBlobStorageMoves"));
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			List<DatabaseObject> objs = result.getResults();
			System.out.println("deletePreviousBlobStorageMoves: " + objs.size());
			for(DatabaseObject obj : objs) {
				System.out.println(obj.toString("deletePreviousBlobStorageMoves: object: "));
				List<DatabaseObject> transactions = QueryBase.getDatabaseObjectsFromSingleProperty(TransactionInfo.class.getCanonicalName(), 
						"identifier", obj.getIdentifier());
				System.out.println("deletePreviousBlobStorageMoves: transactions: " + transactions.size());
				QueryBase.deleteObject(obj);
				for(DatabaseObject transaction : transactions) {
					System.out.println(transaction.toString("deletePreviousBlobStorageMoves: transaction: "));
					QueryBase.deleteObject(transaction);
				}
				System.out.println("deletePreviousBlobStorageMoves: done with transactions");
			}
			System.out.println("deletePreviousBlobStorageMoves: done with objects");
		} catch (ClassNotFoundException e) {
			throw new IOException("deletePreviousBlobStorageMoves: class not found");
		}
	}
}
