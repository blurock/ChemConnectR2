package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class PurposeConceptPair extends DatabaseObject {

	@Index
	String purpose;
	@Index
	String concept;
	
	
	public PurposeConceptPair() {
		purpose = "";
		concept = "";
	}
	
	public PurposeConceptPair(DatabaseObject obj, String purpose, String concept) {
		fill(obj,purpose,concept);
	}
	
	public void fill(DatabaseObject obj, String purpose, String concept) {
		super.fill(obj);
		this.purpose = purpose;
		this.concept = concept;
	}
	
	
	public String getPurpose() {
		return purpose;
	}
	public String getConcept() {
		return concept;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Concept: " + concept + "Purpose: " + purpose + "\n");
		return build.toString();
	}
}
