package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class UserAccount extends ChemConnectDataStructure {

	@Index
	String DatabaseUserID;
	@Index
	String UserAccountInformationID;
	
	public UserAccount() {
		DatabaseUserID = "";
		UserAccountInformationID = "";
	}
	
	public UserAccount(String identifier, String sourceID) {
		super(identifier,sourceID);
		DatabaseUserID = "";
		UserAccountInformationID = "";
	}
	
	public UserAccount(ChemConnectDataStructure datastructure,
			String databaseUserID, String userAccountInformationID) {
		super(datastructure);
		this.DatabaseUserID = databaseUserID;
		this.UserAccountInformationID = userAccountInformationID;		
	}
		
	public void fill(ChemConnectDataStructure datastructure,
			String databaseUserID, String userAccountInformationID) {
		super.fill(datastructure);
		this.DatabaseUserID = databaseUserID;
		this.UserAccountInformationID = userAccountInformationID;		
	}

	
	public String getDatabaseUserID() {
		return DatabaseUserID;
	}
	public String getUserAccountInformationID() {
		return UserAccountInformationID;
	}
	
	
	
}
