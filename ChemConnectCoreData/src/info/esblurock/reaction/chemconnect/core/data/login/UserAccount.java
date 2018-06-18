package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class UserAccount extends ChemConnectDataStructure {

	@Index
	String databaseUser;
	@Index
	String userAccountInformation;
	
	public UserAccount() {
		databaseUser = "";
		userAccountInformation = "";
	}
	
	public UserAccount(String identifier, String sourceID) {
		super(identifier,sourceID);
		databaseUser = "";
		userAccountInformation = "";
	}
	
	public UserAccount(ChemConnectDataStructure datastructure,
			String databaseUser, String userAccountInformation) {
		super(datastructure);
		this.databaseUser = databaseUser;
		this.userAccountInformation = userAccountInformation;		
	}
		
	public void fill(ChemConnectDataStructure datastructure,
			String databaseUser, String userAccountInformation) {
		super.fill(datastructure);
		this.databaseUser = databaseUser;
		this.userAccountInformation = userAccountInformation;		
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		UserAccount user = (UserAccount) object;
		this.databaseUser = user.getDatabaseUser();
		this.userAccountInformation = user.getUserAccountInformation();		
	}

	public String getDatabaseUser() {
		return databaseUser;
	}
	public String getUserAccountInformation() {
		return userAccountInformation;
	}
	
	
	
}
