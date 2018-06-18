package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ContactInfoData extends ChemConnectCompoundDataStructure {

	   	@Index
	   	String email;
	    @Index
	    String topSites;
	    @Index
	    String hasSites;
	    
	    public ContactInfoData() {
			super();
			this.email = "";
			this.topSites = "";
			this.hasSites = "";
		}
	    public ContactInfoData(String identifier, String sourceID) {
	    	super(identifier,sourceID);
			this.email = "";
			this.topSites = "";
			this.hasSites = "";
		}
	    public ContactInfoData(ChemConnectCompoundDataStructure obj) {
	    	super(obj);
			this.email = "";
			this.topSites = "";
			this.hasSites = "";
		}
	    
	    /**
	     * @param identifier The id of contact info
	     * @param owner The owner of contact info
	     * @param access The accessibility of contact info
	     * @param email The full email address
	     * @param topSite The http address of the encompasing structure of the contact
	     * @param hasSite The web-site associated with the contact
	     */
	    public ContactInfoData(ChemConnectCompoundDataStructure obj,
	    		String email, String topSites, String hasSites) {
			fill(obj,email,topSites,hasSites);
		}

	    public void fill(ChemConnectCompoundDataStructure obj,
	    		String email, String topSites, String hasSites) {
			super.fill(obj);
			this.email = email;
			this.topSites = topSites;
			this.hasSites = hasSites;
		}
	    @Override
	    public void fill(DatabaseObject object) {
	    		super.fill(object);
	    		ContactInfoData contact = (ContactInfoData) object;
				this.email = contact.getEmail();
				this.topSites = contact.getTopSite();
				this.hasSites = contact.getHasSite();	    		
	    }
	    
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		public String getTopSite() {
			return topSites;
		}

		public String getHasSite() {
			return hasSites;
		}

		public void setTopSite(String topSites) {
			this.topSites = topSites;
		}

		public void setHasSite(String hasSites) {
			this.hasSites = hasSites;
		}
		public String toString() {
			return toString("");
		}
		public String toString(String prefix) {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString(prefix));
			builder.append(prefix + "email: " + email + "\n");
			builder.append(prefix + "topSite: " + topSites + "\n");
			builder.append(prefix + "hasSite: " + hasSites + "\n");
			return builder.toString();
		}	
}
