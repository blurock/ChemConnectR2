package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfQueries extends ArrayList<QuerySetupBase> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void addAccessToBaseQuery(QuerySetupBase query, String access) {
		QuerySetupBase newquery = query.produceWithAccess(access);
		this.add(newquery);
	}

}
