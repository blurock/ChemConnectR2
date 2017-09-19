package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.util.List;

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transaction.DataSourceIdentification;
import info.esblurock.reaction.chemconnect.core.data.transaction.EventCount;

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
	static public List<DatabaseObject> getDatabaseObjectsFromSingleProperty(String classname, String propertyname,
			String propertyvalue) throws IOException {
		@SuppressWarnings("rawtypes")
		Class objClass;
		List<DatabaseObject> set = null;
		try {
			objClass = Class.forName(classname);
			set = (List<DatabaseObject>) ofy().load().type(objClass).filter(propertyname, propertyvalue);
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
			} else if(o.getClass().isAssignableFrom(DatabaseObject.class)) {
				obj = (DatabaseObject) o;
			} else {
				throw new IOException(o.getClass().getCanonicalName() + " is not a superclas of DatabaseObject");
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}

		return obj;
	}

	public static void deleteUsingPropertyValue(Class<?> cls, String propertyname, String propertyvalue) {
		Object o = ofy().load().type(cls).filter(propertyname, propertyvalue).first().now();
		if(o != null) {
			ofy().delete().entity(o);
		}
	}
}
