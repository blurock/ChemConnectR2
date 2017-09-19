package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccount  extends DatabaseObject {
	
    @Index
    String username;
    @Index
    String password;
    @Index
    String userrole;
    @Index
    String email;
    
	public UserAccount() {
		super();
	}
	public UserAccount(String username, String password, String userrole, String emailS) {
		super();
		this.username = username;
		this.password = password;
		this.userrole = userrole;
		this.email = emailS;
	}
	public String getUsername() {
		return username;
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
