package info.esblurock.reaction.io.db;

import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;

public class QueryFactory {

	public static ListOfQueries produceQueryList(QuerySetupBase query, UserDTO user) {
		ListOfQueries queries = new ListOfQueries();
		if(user.getUserLevel().compareTo(MetaDataKeywords.accessTypeSuperUser) == 0) {
			QuerySetupBase newquery = query.produceWithAccess(null);
			queries.add(newquery);
		} else {
			for(String access : user.getAccess()) {
				QuerySetupBase newquery = query.produceWithAccess(access);
				queries.add(newquery);
			}
		}
		return queries;
	}
	public static ListOfQueries accessQueryForUser(String classname, String user, SetOfQueryPropertyValues values) {
		ListOfQueries queries = new ListOfQueries();
		if(values == null) {
			values = new SetOfQueryPropertyValues();
		}
		QuerySetupBase ownerquery = new QuerySetupBase(user,classname, values);
		QuerySetupBase publicquery = new QuerySetupBase(MetaDataKeywords.publicAccess, classname, values);
		queries.add(publicquery);
		queries.add(ownerquery);
		/*
		 * Add consortium queries
		 */
		return queries;
	}
}
