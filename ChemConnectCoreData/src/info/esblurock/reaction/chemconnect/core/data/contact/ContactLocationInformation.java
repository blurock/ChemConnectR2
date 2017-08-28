package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class ContactLocationInformation extends DatabaseObject {

	  @Index
	    String addressAddress;
	  @Index
	    String city;
	  @Index
	    String country;
	  @Index
	    String postcode;
	  @Index
	    String gpsLocationID;

	  public ContactLocationInformation() {
			super();
			this.addressAddress = null;
			this.city = null;
			this.country = null;
			this.postcode = null;
			this.gpsLocationID = null;
		}

		public ContactLocationInformation(String identifier, String access, String owner,
				String addressAddress, String city, String country, String postcode,
			String gpsLocationID) {
		super(identifier,access,owner);
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpsLocationID = gpsLocationID;
	}

		public String getAddressAddress() {
			return addressAddress;
		}
		public String getCity() {
			return city;
		}
		public String getCountry() {
			return country;
		}
		public String getPostcode() {
			return postcode;
		}
		public String getGpsLocationID() {
			return gpsLocationID;
		}

		
}
