package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class Organization extends ChemConnectDataStructure {

	@Index
	String contactInfoData;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String organizationDescriptionID;

	public Organization() {
		super();
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
		this.contactInfoData = "";
	}
	
	public Organization(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
		this.contactInfoData = "";
	}
	
	public Organization(ChemConnectDataStructure datastructure,
			String contactLocationInformationID, String organizationDescriptionID,
			String contactInfoData) {
		this.fill(datastructure, contactLocationInformationID,organizationDescriptionID,contactInfoData);
	}

	public void fill(ChemConnectDataStructure datastructure,
			String contactLocationInformationID, String organizationDescriptionID,
			String contactInfoData) {
		super.fill(datastructure);
		this.contactLocationInformationID = contactLocationInformationID;
		this.organizationDescriptionID = organizationDescriptionID;		
		this.contactInfoData = contactInfoData;		
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		Organization org = (Organization) object;
		this.contactLocationInformationID = org.getContactLocationInformationID();
		this.organizationDescriptionID = org.getOrganizationDescriptionID();		
		this.contactInfoData = org.getContactInfoData();		
	}
	
	public String getContactLocationInformationID() {
		return contactLocationInformationID;
	}

	public String getOrganizationDescriptionID() {
		return organizationDescriptionID;
	}

	public String getContactInfoData() {
		return contactInfoData;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Contacts: ");
		builder.append(contactInfoData + "\n");
		builder.append(prefix + "Location: ");
		builder.append(contactLocationInformationID + "\n");
		builder.append(prefix + "OrgDescr: ");
		builder.append(organizationDescriptionID + "\n");
		return builder.toString();
	}
	
}
