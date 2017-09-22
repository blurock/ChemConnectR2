package info.esblurock.reaction.core.server.services.util;

import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class DatabaseObjectUtilities {
	public static ArrayList<String> getListOfIdentifiers(List<DatabaseObject> lst) {
		ArrayList<String> ids = new ArrayList<String>();
		for(DatabaseObject obj : lst) {
			ids.add(obj.getIdentifier());
		}
		return ids;
	}
}
