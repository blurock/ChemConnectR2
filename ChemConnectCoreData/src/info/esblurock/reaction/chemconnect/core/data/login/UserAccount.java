package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class UserAccount extends ChemConnectDataStructure {

	@Index
	String accountUserName;
	@Index
	String authorizationName;
	@Index
	String authorizationType;
	@Index
	String accountPrivilege;
	
	public UserAccount() {
		accountUserName = "";
		authorizationName = "";
		authorizationType = "";
	}
	
	public UserAccount(ChemConnectDataStructure datastructure,
			String accountUserName, String authorizationName, String authorizationType,
			String accountPrivilege) {
		fill(datastructure,accountUserName,authorizationName,authorizationType,accountPrivilege);
	}
		
	public void fill(ChemConnectDataStructure datastructure,
			String accountUserName, String authorizationName, String authorizationType,
			String accountPrivilege) {
		super.fill(datastructure);
		this.accountUserName = accountUserName;
		this.authorizationName = authorizationName;		
		this.authorizationType = authorizationType;	
		this.accountPrivilege = accountPrivilege;
	}
	
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
	}

	public String getAccountUserName() {
		return accountUserName;
	}

	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	public String getAuthorizationName() {
		return authorizationName;
	}

	public void setAuthorizationName(String authorizationName) {
		this.authorizationName = authorizationName;
	}

	public String getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}

	public String getAccountPrivilege() {
		return accountPrivilege;
	}

	public void setAccountPrivilege(String accountPrivilege) {
		this.accountPrivilege = accountPrivilege;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));

		build.append(prefix + " accountUserName  :" + accountUserName + "\n");	
		build.append(prefix + " authorizationName:" + authorizationName + "\n");	
		build.append(prefix + " authorizationType:" + authorizationType + "\n");	
		build.append(prefix + " accountPrivilege :" + accountPrivilege + "\n");	
		return build.toString();
	}
	
}
