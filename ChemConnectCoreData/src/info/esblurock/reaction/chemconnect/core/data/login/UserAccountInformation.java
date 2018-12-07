package info.esblurock.reaction.chemconnect.core.data.login;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UserAccountInformation  extends ChemConnectCompoundDataStructure {
	
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
		this.userrole = "";
	}
	public UserAccountInformation(String identifier, String access, String owner, String sourceID,
			String emailS, String userrole) {
		fill(identifier, access, owner,sourceID,emailS,userrole);
	}
	public UserAccountInformation(ChemConnectCompoundDataStructure compound,
			String emailS, String userrole) {
		fill(compound,emailS,userrole);
	}
	
	public void fill(String identifier, String access, String owner, String sourceID,
			String emailS, String userrole) {
		super.fill(identifier, access, owner,sourceID);
		this.email = emailS;
		this.userrole = userrole;
	}
	
	public void fill(ChemConnectCompoundDataStructure compound,
			String emailS, String userrole) {
		super.fill(compound);
		this.email = emailS;
		this.userrole = userrole;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		UserAccountInformation acc = (UserAccountInformation) object;
		this.email = acc.getEmail();
		this.userrole = acc.getUserrole();
	}
	
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	public String getUserrole() {
		return userrole;
	}
	public String getEmail() {
		return email;
	}    
    
}
