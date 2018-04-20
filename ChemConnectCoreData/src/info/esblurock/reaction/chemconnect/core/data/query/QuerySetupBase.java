package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;

public class QuerySetupBase implements Serializable {
	private static final long serialVersionUID = 1L;

	public static int standardLimit = 500;
	
	String cursorS;
	String access;
	int answerLimit;
	String queryClass;
	SetOfQueryPropertyValues queryvalues;
	
	public QuerySetupBase() {
	}
	public QuerySetupBase(String queryClass, SetOfQueryPropertyValues queryvalues) {
		this.queryClass = queryClass;
		this.queryvalues = queryvalues;
		this.access = null;
		answerLimit = standardLimit;
		cursorS = null;
	}
	public QuerySetupBase(String queryClass) {
		this.queryClass = queryClass;
		this.queryvalues = null;
		this.access = null;
		answerLimit = standardLimit;
		cursorS = null;
	}
	public QuerySetupBase(String access, String queryClass, SetOfQueryPropertyValues queryvalues) {
		this.access = access;
		this.queryClass = queryClass;
		this.queryvalues = queryvalues;
		answerLimit = standardLimit;
		cursorS = null;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public int getAnswerLimit() {
		return answerLimit;
	}
	public void setAnswerLimit(int answerLimit) {
		this.answerLimit = answerLimit;
	}
	public String getQueryClass() {
		return queryClass;
	}
	public SetOfQueryPropertyValues getQueryvalues() {
		return queryvalues;
	}
	
	public String getCursorS() {
		return cursorS;
	}
	public void setCursorS(String cursorS) {
		this.cursorS = cursorS;
	}
	public QuerySetupBase produceWithAccess(String access2) {
		QuerySetupBase newquery = new QuerySetupBase(access,queryClass,queryvalues);
		return newquery;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + " QuerySetupBase: ");
		build.append(queryClass);
		build.append("( " + access + ", " + answerLimit + ")\n");
		if(queryvalues != null) {
		build.append(queryvalues.toString(prefix));
		} else {
			build.append("query values empty\n");
		}
		build.append("\n");
		return build.toString();
	}	

}
