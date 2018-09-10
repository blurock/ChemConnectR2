package info.esblurock.reaction.chemconnect.core.data.contact;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class IndividualInformation extends ChemConnectDataStructure {

	@Index
	String contactInfoData;
	@Index
	String contactLocationInformationID;
	@Index
	String personalDescriptionID;
	
	public IndividualInformation() {
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
		this.contactInfoData = "";
	}
	public IndividualInformation(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.contactLocationInformationID = "";
		this.personalDescriptionID = "";
		this.contactInfoData = "";
	}
	
	public IndividualInformation(ChemConnectDataStructure datastructure,
			String contactLocationInformationID,
			String personalDescriptionID,
			String contactInfoData) {
		this.fill(datastructure,
				contactLocationInformationID,
				personalDescriptionID,
				contactInfoData);
	}

	public void fill(ChemConnectDataStructure datastructure,
			String contactLocationInformationID,
			String personalDescriptionID,
			String contactInfoData) {
		super.fill(datastructure);
		this.contactLocationInformationID = contactLocationInformationID;
		this.personalDescriptionID = personalDescriptionID;
		this.contactInfoData = contactInfoData;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		IndividualInformation ind = (IndividualInformation) object;
		this.contactLocationInformationID = ind.getContactLocationInformationID();
		this.personalDescriptionID = ind.getPersonalDescriptionID();
		this.contactInfoData = ind.getContactInfoData();
	}
	
	public String getContactLocationInformationID() {
		return contactLocationInformationID;
	}

	public String getPersonalDescriptionID() {
		return personalDescriptionID;
	}

	public String getContactInfoData() {
		return contactInfoData;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);   
		build.append("Contact Location:     " + contactLocationInformationID + "\n");
		build.append(prefix);
		build.append("Personal Description: " +  personalDescriptionID + "\n");
		build.append(prefix);
		build.append("ContactInfoData:      " +  contactInfoData + "\n");
		return build.toString();
	}
}
