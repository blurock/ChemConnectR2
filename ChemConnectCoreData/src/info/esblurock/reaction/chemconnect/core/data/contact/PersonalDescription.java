package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class PersonalDescription extends DatabaseObject {

	@Index
	String UserClassification;
	
	@Index
	String nameOfPersonIdentifier;
	
	public PersonalDescription() {
		UserClassification = "";
		this.nameOfPersonIdentifier = "";
		
	}
	public PersonalDescription(String identifier, String sourceID) {
		super(identifier,sourceID);
		UserClassification = "";
		this.nameOfPersonIdentifier = "";
		
	}
	public PersonalDescription(String identifier, String access, String owner, String sourceID,
			String userClassification, String nameOfPersonIdentifier) {
		super(identifier,access,owner,sourceID);
		UserClassification = userClassification;
		this.nameOfPersonIdentifier = nameOfPersonIdentifier;
	}
	public PersonalDescription(String identifier, String access, String owner, String sourceID,
			String userClassification, NameOfPerson name) {
		fill(identifier,access,owner,sourceID,userClassification,name);
	}

	public void fill(String identifier, String access, String owner, String sourceID,
			String userClassification, NameOfPerson name) {
		super.fill(identifier,access,owner,sourceID);
		UserClassification = userClassification;
		this.nameOfPersonIdentifier = name.getIdentifier();
	}
	
	
	public String getUserClassification() {
		return UserClassification;
	}

	public String getNameOfPersonIdentifier() {
		return nameOfPersonIdentifier;
	}
	
	
}
