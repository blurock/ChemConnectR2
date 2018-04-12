package info.esblurock.reaction.chemconnect.core.data.contact;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ContactInfoData extends ChemConnectCompoundDataStructure {

	   	@Index
	   	String email;
	    @Unindex
	    HashSet<String> topSite;
	    @Unindex
	    HashSet<String> hasSite;
	    
	    public ContactInfoData() {
			super();
			this.email = "";
			this.topSite = new HashSet<String>();
			this.hasSite = new HashSet<String>();
		}
	    public ContactInfoData(String identifier, String sourceID) {
	    	super(identifier,sourceID);
			this.email = "";
			this.topSite = new HashSet<String>();
			this.hasSite = new HashSet<String>();
		}
	    public ContactInfoData(ChemConnectCompoundDataStructure obj) {
	    	super(obj);
			this.email = "";
			this.topSite = new HashSet<String>();
			this.hasSite = new HashSet<String>();
		}
	    
	    /**
	     * @param identifier The id of contact info
	     * @param owner The owner of contact info
	     * @param access The accessibility of contact info
	     * @param email The full email address
	     * @param topSite The http address of the encompasing structure of the contact
	     * @param hasSite The web-site associated with the contact
	     */
	    public ContactInfoData(String identifier, String owner, String access, String sourceID,
	    		String email, HashSet<String> topSite, HashSet<String> hasSite) {
			fill(identifier,owner,access,sourceID,email,topSite,hasSite);
		}

	    public void fill(String identifier, String owner, String access, String sourceID,
	    		String email, HashSet<String> topSite, HashSet<String> hasSite) {
			super.fill(identifier,owner,access,sourceID);
			this.email = email;
			this.topSite = topSite;
			this.hasSite = hasSite;
		}
	    
	    
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		public HashSet<String> getTopSite() {
			return topSite;
		}

		public HashSet<String> getHasSite() {
			return hasSite;
		}

		public void setTopSite(HashSet<String> topSite) {
			this.topSite = topSite;
		}

		public void setHasSite(HashSet<String> hasSite) {
			this.hasSite = hasSite;
		}
		public String toString() {
			return toString("");
		}
		public String toString(String prefix) {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString(prefix));
			builder.append(prefix + "email: " + email + "\n");
			builder.append(prefix + "topSite: " + topSite + "\n");
			builder.append(prefix + "hasSite: " + hasSite + "\n");
			return builder.toString();
		}	
}
