package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class PersonalDescription extends DatabaseObject {

	@Index
	String UserClassification;
	
	@Index
	String nameOfPersonIdentifier;

	public PersonalDescription(String identifier, String access, String owner,
			String userClassification, String nameOfPersonIdentifier) {
		super(identifier,access,owner);
		UserClassification = userClassification;
		this.nameOfPersonIdentifier = nameOfPersonIdentifier;
	}
	public PersonalDescription(String identifier, String access, String owner,
			String userClassification, NameOfPerson name) {
		super(identifier,access,owner);
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
