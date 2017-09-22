package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class SingleQueryResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String cursorS;
	ArrayList<DatabaseObject> results;
	QuerySetupBase query;
	public SingleQueryResult(ArrayList<DatabaseObject> results, QuerySetupBase query, String cursorS) {
		super();
		this.results = results;
		this.query = query;
		this.cursorS = cursorS;
	}
	
	
	

}
