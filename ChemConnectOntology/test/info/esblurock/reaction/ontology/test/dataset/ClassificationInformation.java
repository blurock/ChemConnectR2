package info.esblurock.reaction.ontology.test.dataset;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class ClassificationInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	String idName;
	String identifier;
	String dataType;
	DatabaseObject top;
	
	public ClassificationInformation(DatabaseObject top, String idName, String identifier, String dataType) {
		super();
		this.idName = idName;
		this.identifier = identifier;
		this.dataType = dataType;
		this.top = top;
	}
	public String getIdName() {
		return idName;
	}
	public String getIdentifier() {
		return identifier;
	}
	public String getDataType() {
		return dataType;
	}
	
	public DatabaseObject getTop() {
		return top;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append("ID: ");
		build.append(idName);
		build.append(": ");
		build.append(identifier);
		build.append("  (");
		build.append(dataType);
		build.append(")");
		
		return build.toString();
	}
	
}
