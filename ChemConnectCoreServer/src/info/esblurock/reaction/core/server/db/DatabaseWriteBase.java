package info.esblurock.reaction.core.server.db;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class DatabaseWriteBase {
	public static void writeDatabaseObject(DatabaseObject object) {
		ObjectifyService.ofy().save().entity(object).now();
	}
	public static void writeListOfDatabaseObjects(List<DatabaseObject> lst) {
		ObjectifyService.ofy().save().entities(lst);
	}
}
