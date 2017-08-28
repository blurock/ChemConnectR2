package info.esblurock.reaction.chemconnect.core.data.contact;

import java.util.ArrayList;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class ContactInfoData extends DatabaseObject {

	   	@Index
	   	String email;
	    @Unindex
	    HashSet<String> topSite;
	    @Unindex
	    HashSet<String> hasSite;
	    
	    public ContactInfoData() {
			super();
			this.email = null;
			this.topSite = null;
			this.hasSite = null;
		}
	    
	    /**
	     * @param identifier The id of contact info
	     * @param owner The owner of contact info
	     * @param access The accessibility of contact info
	     * @param email The full email address
	     * @param topSite The http address of the encompasing structure of the contact
	     * @param hasSite The web-site associated with the contact
	     */
	    public ContactInfoData(String identifier, String owner, String access, 
	    		String email, HashSet<String> topSite, HashSet<String> hasSite) {
			super(identifier,owner,access);
			this.email = email;
			this.topSite = topSite;
			this.hasSite = hasSite;
		}

		public String getEmail() {
			return email;
		}

		public HashSet<String> getTopSite() {
			return topSite;
		}

		public HashSet<String> getHasSite() {
			return hasSite;
		}
	   
	    
	
}
