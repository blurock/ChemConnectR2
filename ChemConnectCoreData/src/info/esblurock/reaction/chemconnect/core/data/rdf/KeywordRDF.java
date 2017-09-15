package info.esblurock.reaction.chemconnect.core.data.rdf;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class KeywordRDF extends DatabaseObject {
	
	public static String subjectProp = "subject";
	public static String predicateProp = "predicate";
	public static String objectProp = "object";
	/** The string type. String information is stored in the object */
	public static String stringType = "String";

    @Index
    String predicate;

    @Index
    String object;

    @Index
    String objectType;

    public KeywordRDF() {
   }
 
	public KeywordRDF(String key, String access, String owner, String sourceID,
			String predicate, String object, String objectType) {
		super(key,access,owner,sourceID);
		this.predicate = predicate;
		this.object = object;
		this.objectType = objectType;
	}


	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append(getIdentifier());
		build.append(" -> ");
		build.append(predicate);
		build.append(" -> ");
		if(objectType.compareToIgnoreCase(stringType) == 0) {
			build.append(object);
		} else {
			build.append(object);
			build.append(" (");
			build.append(objectType);
			build.append(")");
		}
		build.append("\t[");
		build.append(getIdentifier() + ", " + getAccess() + ", " + getOwner() + "]");
		return build.toString();
	}
}