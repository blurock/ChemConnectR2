package info.esblurock.reaction.chemconnect.core.data.query;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class SetOfQueryResults extends ArrayList<SingleQueryResult> {
	private static final long serialVersionUID = 1L;

	ArrayList<DatabaseObject> finalresults;
	boolean complete;
	
	public SetOfQueryResults() {
		finalresults = new ArrayList<DatabaseObject>();
		complete = true;
	}
	
	public void merge(SingleQueryResult result) {
		finalresults.addAll(result.getResults());
		if(result.getCursorS() != null) {
			complete = false;
			result.clearResults();
			this.add(result);
		}
	}
	public ArrayList<DatabaseObject> retrieveAndClear() {
		
		ArrayList<DatabaseObject> completed = finalresults;
		finalresults = new ArrayList<DatabaseObject>();
		return completed;
	}
}
