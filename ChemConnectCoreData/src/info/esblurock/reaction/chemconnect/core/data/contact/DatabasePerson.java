package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class DatabasePerson extends ChemConnectDataStructure {

	@Index
	String contactInfoDataID;
	@Index
	String contactLocationInformationID;
	@Index
	String personalDescriptionID;
	
	public DatabasePerson() {
	}
	
	public DatabasePerson(ChemConnectDataStructure structure, String contactInfoDataID, 
			String contactLocationInformationID, String personalDescriptionID) {
		fill(structure, contactInfoDataID, 
			contactLocationInformationID, personalDescriptionID);
	}
	
	public void fill(ChemConnectDataStructure structure, String contactInfoDataID, 
			String contactLocationInformationID, String personalDescriptionID) {
		super.fill(structure);
		this.contactInfoDataID = contactInfoDataID;
		this.contactLocationInformationID = contactLocationInformationID;
		this.personalDescriptionID = personalDescriptionID;
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
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Contact Info:    " +  contactInfoDataID + "\n");
		builder.append(prefix + "Contact Location " +  contactLocationInformationID + "\n");
		builder.append(prefix + "Description:     " +  personalDescriptionID + "\n");
		
		return builder.toString();
	}
}
