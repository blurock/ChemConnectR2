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
	    String gpslatitute;
	  @Index
	    String gpslongitude;

	  public ContactLocationInformation() {
			super();
			this.addressAddress = null;
			this.city = null;
			this.country = null;
			this.postcode = null;
			this.gpslatitute = null;
			this.gpslongitude = null;
		}

		public ContactLocationInformation(String identifier, String addressAddress, String city, String country, String postcode,
			String gpslatitute, String gpslongitude) {
		super(identifier);
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpslatitute = gpslatitute;
		this.gpslongitude = gpslongitude;
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

		public String getGpslatitute() {
			return gpslatitute;
		}

		public String getGpslongitude() {
			return gpslongitude;
		}	
}
