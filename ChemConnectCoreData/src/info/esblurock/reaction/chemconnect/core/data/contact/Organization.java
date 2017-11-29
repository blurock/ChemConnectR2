package info.esblurock.reaction.chemconnect.core.data.contact;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

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

	@Unindex
	HashSet<String> userAccounts;

	public Organization() {
		super();
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
		this.userAccounts = new HashSet<String>();
	}
	
	public Organization(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.organizationDescriptionID = "";
		this.userAccounts = new HashSet<String>();
	}
	
	public Organization(ChemConnectDataStructure datastructure,
			String contactInfoDataID, 
			String contactLocationInformationID, String organizationDescriptionID,
			HashSet<String> userAccounts) {
		super(datastructure);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.organizationDescriptionID = organizationDescriptionID;
		this.userAccounts = userAccounts;
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

	public HashSet<String> getUserAccounts() {
		return userAccounts;
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
		builder.append(contactInfoDataID);
		builder.append(", Location: ");
		builder.append(contactLocationInformationID);
		builder.append("\n" + prefix + "OrgDescr: ");
		builder.append(organizationDescriptionID);
		builder.append(", UserAccoounts: ");
		builder.append(userAccounts);
		builder.append("\n");
		return builder.toString();
	}
	
}
