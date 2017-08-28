package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class Organization extends DatabaseObject {

	@Index
	String contactInfoDataID;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String descriptionDataDataID;
	
	@Index
	String organizationDescriptionID;

	public Organization(String identifier, String access, String owner,
			String descriptionDataDataID, String contactInfoDataID, 
			String contactLocationInformationID, String organizationDescriptionID) {
		super(identifier, access, owner);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.descriptionDataDataID = descriptionDataDataID;
		this.organizationDescriptionID = organizationDescriptionID;
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

	public String getOrganizationDescriptionID() {
		return organizationDescriptionID;
	}
	
	
}
