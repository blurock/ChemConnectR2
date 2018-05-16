package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class Organization extends ChemConnectDataStructure {

	@Index
	String contactInfoDataID;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String organizationDescriptionID;

	public Organization() {
		super();
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
	}
	
	public Organization(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
	}
	
	public Organization(ChemConnectDataStructure datastructure,
			String contactInfoDataID, 
			String contactLocationInformationID, String organizationDescriptionID) {
		fill(datastructure,contactInfoDataID, contactLocationInformationID,organizationDescriptionID);
	}

	public void fill(ChemConnectDataStructure datastructure,
			String contactInfoDataID, 
			String contactLocationInformationID, String organizationDescriptionID) {
		super.fill(datastructure);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.organizationDescriptionID = organizationDescriptionID;		
	}
	
	
	public String getContactInfoDataID() {
		return contactInfoDataID;
	}

	public String getContactLocationInformationID() {
		return contactLocationInformationID;
	}

	public String getOrganizationDescriptionID() {
		return organizationDescriptionID;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Contact: ");
		builder.append(contactInfoDataID + "\n");
		builder.append(prefix + "Location: ");
		builder.append(contactLocationInformationID + "\n");
		builder.append(prefix + "OrgDescr: ");
		builder.append(organizationDescriptionID + "\n");
		return builder.toString();
	}
	
}
