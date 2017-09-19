package info.esblurock.reaction.chemconnect.core.data.login;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccount  extends DatabaseObject {
	
	@Index
	String contactInfoDataID;
    @Index
    String password;
    @Index
    String userrole;
    @Index
    String email;
    
	public UserAccount() {
		super();
	}
	public UserAccount(String identifier, String sourceID) {
		super(identifier, sourceID);
		this.contactInfoDataID = "";
		this.password = "";
		this.userrole = "";
		this.email = "";
	}
	public UserAccount(String identifier, String access, String owner, String sourceID,
			String contactInfoDataID, String password, String userrole, String emailS) {
		super(identifier, access, owner,sourceID);
		this.contactInfoDataID = contactInfoDataID;
		this.password = password;
		this.userrole = userrole;
		this.email = emailS;
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
	public String getContactInfoDataID() {
		return contactInfoDataID;
	}
    
    
}
