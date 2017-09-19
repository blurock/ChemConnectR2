package info.esblurock.reaction.chemconnect.core.data.contact;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class NameOfPerson extends DatabaseObject {

	@Index
	String title;
	@Index
	String givenName;
	@Index
	String familyName;
	
	public NameOfPerson() {
		this.title = "";
		this.givenName = "";
		this.familyName = "";
	}
	
	public NameOfPerson(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.title = "";
		this.givenName = "";
		this.familyName = "";		
	}
	
	public NameOfPerson(String identifier, String access, String owner, String sourceID,
			String title, String givenName, String familyName) {
		fill(identifier, access, owner,sourceID,title,givenName,familyName);
	}

	public void fill(String identifier, String access, String owner, String sourceID,
			String title, String givenName, String familyName) {
		super.fill(identifier, access, owner,sourceID);
		this.title = title;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public String getTitle() {
		return title;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("NameOfPerson(");
		build.append(getIdentifier());
		build.append("): ");
		build.append(title);
		build.append(" ");
		build.append(givenName);
		build.append(" ");
		build.append(familyName);
		return build.toString();
	}
	
	
}
