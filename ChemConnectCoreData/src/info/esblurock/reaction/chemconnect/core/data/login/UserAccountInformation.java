package info.esblurock.reaction.chemconnect.core.data.login;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccountInformation  extends DatabaseObject {
	
    @Index
    String password;
    @Index
    String email;
    @Index
    String userrole;
    
	public UserAccountInformation() {
		super();
	}
	public UserAccountInformation(String identifier, String sourceID) {
		super(identifier, sourceID);
		this.email = "";
		this.password = "";
		this.userrole = "";
	}
	public UserAccountInformation(String identifier, String access, String owner, String sourceID,
			String emailS, String password, String userrole) {
		super(identifier, access, owner,sourceID);
		this.email = emailS;
		this.password = password;
		this.userrole = userrole;
	}
	
	public String getPassword() {
		return password;
	}
	public String getUserrole() {
		return userrole;
	}
	public String getEmail() {
		return email;
	}    
    
}
