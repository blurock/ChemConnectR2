package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class ContactInfoData extends DatabaseObject {

	   @Index
	    String email;
	    @Index
	    String topSite;
	    @Index
	    String hasSite;
	    
	    public ContactInfoData() {
			super();
			this.email = null;
			this.topSite = null;
			this.hasSite = null;
		}
	    
	    /**
	     * @param identifier The id of contact info
	     * @param email The full email address
	     * @param topSite The http address of the encompasing structure of the contact
	     * @param hasSite The web-site associated with the contact
	     */
	    public ContactInfoData(String identifier, String email, String topSite, String hasSite) {
			super(identifier);
			this.email = email;
			this.topSite = topSite;
			this.hasSite = hasSite;
		}
	   
	    
	
}
