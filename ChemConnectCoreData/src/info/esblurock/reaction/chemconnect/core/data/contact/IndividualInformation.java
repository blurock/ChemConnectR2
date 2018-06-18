package info.esblurock.reaction.chemconnect.core.data.contact;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class IndividualInformation extends ChemConnectDataStructure {
	@Index
	String contactInfoDataID;
	
	@Index
	String contactLocationInformationID;
	
	@Index
	String personalDescriptionID;
	
	public IndividualInformation() {
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
	}
	public IndividualInformation(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactInfoDataID = "";
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
	}
	
	public IndividualInformation(ChemConnectDataStructure datastructure,
			String contactInfoDataID, String contactLocationInformationID,
			String personalDescriptionID) {
		this.fill(datastructure,
				contactInfoDataID,contactLocationInformationID,
				personalDescriptionID);
	}

	public void fill(ChemConnectDataStructure datastructure,
			String contactInfoDataID, String contactLocationInformationID,
			String personalDescriptionID) {
		super.fill(datastructure);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.personalDescriptionID = personalDescriptionID;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		IndividualInformation ind = (IndividualInformation) object;
		this.contactInfoDataID = ind.getContactInfoDataID();
		this.contactLocationInformationID = ind.getContactLocationInformationID();
		this.personalDescriptionID = ind.getPersonalDescriptionID();
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

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append("Contact Info: " + contactInfoDataID + "\n");
		build.append(prefix);
		build.append("Contact Location: " + contactLocationInformationID + "\n");
		build.append(prefix);
		build.append("Personal Description: " +  personalDescriptionID + "\n");
		return build.toString();
	}
}
