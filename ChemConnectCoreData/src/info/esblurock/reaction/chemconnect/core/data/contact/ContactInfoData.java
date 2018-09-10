package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ContactInfoData extends ChemConnectCompoundDataStructure {

	   	@Index
	   	String contactType;
	    @Index
	    String contact;
	    
	    public ContactInfoData() {
			super();
			this.contactType = "";
			this.contact = "";
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
	    		String contactType, String contact) {
			fill(obj,contactType,contact);
		}

	    public void fill(ChemConnectCompoundDataStructure obj,
	    		String contactType, String contact) {
			super.fill(obj);
			this.contactType = contactType;
			this.contact = contact;
		}
	    @Override
	    public void fill(DatabaseObject object) {
	    		super.fill(object);
	    		ContactInfoData contact = (ContactInfoData) object;
				this.contactType = contact.getContactType();
				this.contact = contact.getContact();	    		
	    }
	    
		public String getContactType() {
			return contactType;
		}

		public String getContact() {
			return contact;
		}

		public void setContactType(String contactType) {
			this.contactType = contactType;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String toString() {
			return toString("");
		}
		public String toString(String prefix) {
			StringBuilder builder = new StringBuilder();
			builder.append(super.toString(prefix));
			builder.append(prefix + "contactType: " + contactType + "\n");
			builder.append(prefix + "contact: " + contact + "\n");
			return builder.toString();
		}	
}
