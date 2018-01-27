package info.esblurock.reaction.chemconnect.core.data.contact;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class IndividualInformation extends ChemConnectDataStructure {
	@Index
	String contactInfoDataID;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String personalDescriptionID;
	
	@Unindex
	HashSet<String> organizationID;

	public IndividualInformation() {
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
		this.organizationID = new HashSet<String>();		
	}
	public IndividualInformation(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
		this.organizationID = new HashSet<String>();		
	}
	
	public IndividualInformation(ChemConnectDataStructure datastructure,
			String contactInfoDataID, String contactLocationInformationID,
			String personalDescriptionID, HashSet<String> organizationID) {
		fill(datastructure,
				contactInfoDataID,contactLocationInformationID,
				personalDescriptionID,organizationID);
	}

	public void fill(ChemConnectDataStructure datastructure,
			String contactInfoDataID, String contactLocationInformationID,
			String personalDescriptionID, HashSet<String> organizationID) {
		super.fill(datastructure);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.personalDescriptionID = personalDescriptionID;
		this.organizationID = organizationID;
	}
	
	public String getContactInfoDataID() {
		return contactInfoDataID;
	}

	public String getContactLocationInformationID() {
		return contactLocationInformationID;
	}

	public String getPersonalDescriptionID() {
		return personalDescriptionID;
	}

	public HashSet<String> getOrganizationID() {
		return organizationID;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append("Contact Info: " + contactInfoDataID + ",  ");
		build.append("Contact Location: " + contactLocationInformationID + "\n");
		build.append(prefix);
		build.append("Personal Description: " +  personalDescriptionID + ",  ");
		build.append("Organizations" + organizationID + "\n");
		return build.toString();
	}
}
