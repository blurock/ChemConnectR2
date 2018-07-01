package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class SingleQueryResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String cursorS;
	ArrayList<DatabaseObject> results;
	QuerySetupBase query;
	
	public SingleQueryResult() {
		results = new ArrayList<DatabaseObject>();
		cursorS = null;
		query = null;
	}
	public SingleQueryResult(ArrayList<DatabaseObject> results, QuerySetupBase query, String cursorS) {
		super();
		this.results = results;
		this.query = query;
		this.cursorS = cursorS;
	}
	public String getCursorS() {
		return cursorS;
	}
	public ArrayList<DatabaseObject> getResults() {
		return results;
	}
	public QuerySetupBase getQuery() {
		return query;
	}
	public void clearResults() {
		results = new ArrayList<DatabaseObject>();
	}
	
	
	

}
