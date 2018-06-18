package info.esblurock.reaction.chemconnect.core.data.login;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccountInformation  extends ChemConnectCompoundDataStructure {
	
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
		fill(identifier, access, owner,sourceID,emailS,password,userrole);
	}
	public UserAccountInformation(ChemConnectCompoundDataStructure compound,
			String emailS, String password, String userrole) {
		fill(compound,emailS,password,userrole);
	}
	
	public void fill(String identifier, String access, String owner, String sourceID,
			String emailS, String password, String userrole) {
		super.fill(identifier, access, owner,sourceID);
		this.email = emailS;
		this.password = password;
		this.userrole = userrole;
	}
	
	public void fill(ChemConnectCompoundDataStructure compound,
			String emailS, String password, String userrole) {
		super.fill(compound);
		this.email = emailS;
		this.password = password;
		this.userrole = userrole;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		UserAccountInformation acc = (UserAccountInformation) object;
		this.email = acc.getEmail();
		this.password = acc.getPassword();
		this.userrole = acc.getUserrole();
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
