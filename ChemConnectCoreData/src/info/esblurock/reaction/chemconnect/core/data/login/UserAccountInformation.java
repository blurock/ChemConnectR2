package info.esblurock.reaction.chemconnect.core.data.login;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccountInformation  extends DatabaseObject {
	
	@Index
	String contactInfoDataID;
    @Index
    String password;
    @Index
    String email;
    @Index
    HashSet<String> userrole;
    
	public UserAccountInformation() {
		super();
	}
	public UserAccountInformation(String identifier, String sourceID) {
		super(identifier, sourceID);
		this.contactInfoDataID = "";
		this.password = "";
		this.userrole = new HashSet<String>();
		this.email = "";
	}
	public UserAccountInformation(String identifier, String access, String owner, String sourceID,
			String contactInfoDataID, String password, HashSet<String> userrole, String emailS) {
		super(identifier, access, owner,sourceID);
		this.contactInfoDataID = contactInfoDataID;
		this.password = password;
		this.userrole = userrole;
		this.email = emailS;
	}
	public String getPassword() {
		return password;
	}
	public HashSet<String> getUserrole() {
		return userrole;
	}
	public String getEmail() {
		return email;
	}
	public String getContactInfoDataID() {
		return contactInfoDataID;
	}
    
    
}
