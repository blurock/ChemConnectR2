package info.esblurock.reaction.core.server.db;

import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class QueryBase {
	
	
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
}
