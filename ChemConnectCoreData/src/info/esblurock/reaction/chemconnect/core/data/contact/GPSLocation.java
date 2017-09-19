package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class GPSLocation extends DatabaseObject {
	
	@Index
	String GPSLatitude;
	@Index
	String GPSLongitude;
	
	public GPSLocation() {
		this.GPSLatitude = "";
		this.GPSLongitude = "";		
	}
	public GPSLocation(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.GPSLatitude = "";
		this.GPSLongitude = "";		
	}
	
	public GPSLocation(String identifier, String access, String owner, String sourceID,
			String GPSLatitude, String GPSLongitude) {
		fill(identifier, access, owner,sourceID,GPSLatitude,GPSLongitude);
	}
	
	public void fill(String identifier, String access, String owner, String sourceID,
			String GPSLatitude, String GPSLongitude) {
		super.fill(identifier, access, owner,sourceID);
		this.GPSLatitude = GPSLatitude;
		this.GPSLongitude = GPSLongitude;
	}
	
	public String getGPSLatitude() {
		return GPSLatitude;
	}
	public String getGPSLongitude() {
		return GPSLongitude;
	}

}
