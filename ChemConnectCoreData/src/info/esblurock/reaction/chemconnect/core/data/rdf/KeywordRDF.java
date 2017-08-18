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

    @Index
    String subject;

    @Index
    String predicate;

    @Index
    String object;

    @Index
    String user;

    @Index
    String sourceCode;

    public KeywordRDF() {
   }
 
	public KeywordRDF(String subject, String predicate, String object, String user, String sourceCode) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.user = user;
		this.sourceCode = sourceCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		build.append(subject);
		build.append(" -> ");
		build.append(predicate);
		build.append(" -> ");
		if(predicate.endsWith("String")) {
			build.append(object);
		} else {
			build.append("(key");
		}
		return build.toString();
	}
}