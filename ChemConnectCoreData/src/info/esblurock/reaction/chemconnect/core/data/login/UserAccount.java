package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class UserAccount extends DatabaseObject {

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
	
	public UserAccount(String identifier, String access, String owner, String sourceID,
			String databaseUserID, String userAccountInformationID) {
		fill(identifier, access, owner,sourceID,databaseUserID,userAccountInformationID);
	}
	
	public void fill(String identifier, String access, String owner, String sourceID,
			String databaseUserID, String userAccountInformationID) {
		super.fill(identifier, access, owner, sourceID);
		DatabaseUserID = databaseUserID;
		UserAccountInformationID = userAccountInformationID;		
	}
	
	public String getDatabaseUserID() {
		return DatabaseUserID;
	}
	public String getUserAccountInformationID() {
		return UserAccountInformationID;
	}
	
	
	
}
