package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class IndividualInformation extends DatabaseObject {
	@Index
	String contactInfoDataID;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String descriptionDataDataID;
	
	@Index
	String personalDescriptionID;
	
	@Index
	String organizationID;

	public IndividualInformation(String identifier, String access, String owner,
			String descriptionDataDataID,
			String contactInfoDataID, String contactLocationInformationID,
			String personalDescriptionID, String organizationID) {
		super(identifier, access, owner);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.descriptionDataDataID = descriptionDataDataID;
		this.personalDescriptionID = personalDescriptionID;
		this.organizationID = organizationID;
	}

	public String getContactInfoDataID() {
		return contactInfoDataID;
	}

	public String getContactLocationInformationID() {
		return contactLocationInformationID;
	}

	public String getDescriptionDataDataID() {
		return descriptionDataDataID;
	}

	public String getPersonalDescriptionID() {
		return personalDescriptionID;
	}

	public String getOrganizationID() {
		return organizationID;
	}
}
