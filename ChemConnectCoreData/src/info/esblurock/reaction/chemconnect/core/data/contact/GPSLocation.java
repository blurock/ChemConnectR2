package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class GPSLocation extends DatabaseObject {
	
	@Index
	String GPSLatitude;
	@Index
	String GPSLongitude;
	
	public GPSLocation(String identifier, String access, String owner,
			String GPSLatitude, String GPSLongitude) {
		super(identifier, access, owner);
		this.GPSLatitude = GPSLatitude;
		this.GPSLongitude = GPSLongitude;
	}
	
	public String getGPSLatitudeID() {
		return GPSLatitude;
	}
	public String getGPSLongitudeID() {
		return GPSLongitude;
	}

}
