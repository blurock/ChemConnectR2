package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UnverifiedUserAccount extends DatabaseObject {
    @Index
    String username;
    @Index
    String email;
    
    public UnverifiedUserAccount() {
    	
    }
	public UnverifiedUserAccount(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}

}
