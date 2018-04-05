package info.esblurock.reaction.io.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import static com.googlecode.objectify.ObjectifyService.ofy;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.DataSourceIdentification;
import info.esblurock.reaction.chemconnect.core.data.transaction.EventCount;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;

public class QueryBase {
	public static String userS = "user";
	
	public static int getNextEventCount(String id) {
		EventCount count = (EventCount) ofy().load().type(EventCount.class).id(id).now();
		if(count == null) {
			count = new EventCount(id);
		} else {
			count.increment();
		}
		ofy().save().entity(count).now();
		return count.getCount();
	}

	public static DatabaseObject getDatabaseObjectWith(Class<?> cls, Long id) {
		DatabaseObject obj = (DatabaseObject) ofy().load().key(Key.create(cls, id)).now();
		return obj;
	}

	public static String getDataSourceIdentification(String username) {
		DataSourceIdentification sourceID = ofy().load().type(DataSourceIdentification.class).id(username).now();
		if(sourceID != null) {
			sourceID.increment();
		} else {
			sourceID = new DataSourceIdentification(username);
		}
		ofy().save().entity(sourceID).now();
		
		return sourceID.getCountAsString();
	}
	
	@SuppressWarnings("unchecked")
	static public List<DatabaseObject> getDatabaseObjects(String classname) throws IOException {
		@SuppressWarnings("rawtypes")
		Class objClass;
		List<DatabaseObject> lst = null;
		try {
			objClass = Class.forName(classname);
			lst = (List<DatabaseObject>) ofy().load().type(objClass).list();
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}
		return lst;
		
	}
	static public DatabaseObject getDatabaseObjectFromIdentifier(String classname, String identifier) throws IOException {
		return getFirstDatabaseObjectsFromSingleProperty(classname, "identifier", identifier);
	}
	
	
	@SuppressWarnings("unchecked")
	static public List<DatabaseObject> getDatabaseObjectsFromSingleProperty(String classname, 
			String propertyname,
			String propertyvalue) throws IOException {
		@SuppressWarnings("rawtypes")
		Class objClass;
		List<DatabaseObject> set = null;
		try {
			objClass = Class.forName(classname);
			
			Object o = ofy().load().type(objClass).filter(propertyname, propertyvalue).first().now();
			set = (List<DatabaseObject>) o;
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}
		
		return set;
	}
	
	@SuppressWarnings("unchecked")
	static public DatabaseObject getFirstDatabaseObjectsFromSingleProperty(String classname,
			String propertyname, String propertyvalue) throws IOException {
		@SuppressWarnings("rawtypes")
		Class objClass;
		DatabaseObject obj = null;
		try {
			objClass = Class.forName(classname);
			Object o = ofy().load().type(objClass).filter(propertyname, propertyvalue).first().now();
			if(o == null) {
				throw new IOException("No results found");				
			} else if(DatabaseObject.class.isAssignableFrom(o.getClass())) {
				obj = (DatabaseObject) o;
			} else {
				throw new IOException(o.getClass().getCanonicalName() + " is not a superclas of DatabaseObject");
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}

		return obj;
	}

	public static List<KeywordRDF> findRDF(String subject, String predicate, String object) {
		Query<KeywordRDF> query = ofy().load().type(KeywordRDF.class);
		if(subject != null) {
			query = query.filter("subject",subject);
		}
		if(predicate != null) {
			query = query.filter("predicate",predicate);
		}
		if(object != null) {
			query = query.filter("object",object);
		}
		List<KeywordRDF> lst = query.list();
		return lst;
	}
	
	public static SingleQueryResult StandardQueryResult(QuerySetupBase parameters) throws ClassNotFoundException {

		System.out.println("StandardQueryResult: set up query: " + parameters.toString());
		Class<?> cls = Class.forName(parameters.getQueryClass());
		Query<?> query = ofy().load().type(cls);
		
		if(parameters.getCursorS() != null) {
			query = query.startAt(Cursor.fromWebSafeString(parameters.getCursorS()));
		}
		
		
		//query = query.limit(parameters.getAnswerLimit());
		
		if(parameters.getQueryvalues() != null) {
			for(QueryPropertyValue pv : parameters.getQueryvalues()) {
				if(pv.isStringvalue()) {
					query = query.filter(pv.getProperty(),pv.getValueS());
				} else if(pv.isDoublevalue()) {
					query = query.filter(pv.getProperty(),pv.getValueD());
				} else if(pv.isIntvalue()) {
					query = query.filter(pv.getProperty(),pv.getValueI());
				}
			}
		}
		
		ArrayList<DatabaseObject> lst = new ArrayList<DatabaseObject>();
		System.out.println("Query results with right filter");
		for(Object obj : query) {
			System.out.println(obj.toString());
			lst.add((DatabaseObject) obj);
		}
		System.out.println("StandardQueryResult: with property" + parameters.toString());
		/*
		@SuppressWarnings("unchecked")
		QueryResultIterator<DatabaseObject> iterator = (QueryResultIterator<DatabaseObject>) query.iterator();
		boolean cont = false;
		while(iterator.hasNext()) {
			DatabaseObject obj = (DatabaseObject) iterator.next();
			System.out.println("StandardQueryResult: " + obj.toString());
			lst.add(obj);
			cont = true;
		}
		*/
		String encodedCursor = null;
		/*
		if(cont) {
			Cursor cursor = iterator.getCursor();
			encodedCursor = cursor.toWebSafeString();
		}
		*/
		SingleQueryResult result = new SingleQueryResult(lst,parameters,encodedCursor);
		return result;
	}
	
	public static void deleteUsingPropertyValue(Class<?> cls, String propertyname, String propertyvalue) {
		Object o = ofy().load().type(cls).filter(propertyname, propertyvalue).first().now();
		if(o != null) {
			ofy().delete().entity(o);
		} else { 
			System.out.println(cls.getCanonicalName() + " not found: property='" + propertyname + "  value='" + propertyvalue + "'");
		}
	}
	public static void deleteObject(Object o) {
		if(o != null) {
			deleteObject(o);
		}
	}
}
