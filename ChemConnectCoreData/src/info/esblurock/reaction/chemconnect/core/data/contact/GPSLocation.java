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
	public GPSLocation(DatabaseObject obj) {
		super(obj);
		this.GPSLatitude = "";
		this.GPSLongitude = "";		
	}
	
	public GPSLocation(String identifier, String access, String owner, String sourceID,
			String GPSLatitude, String GPSLongitude) {
		fill(identifier, access, owner,sourceID,GPSLatitude,GPSLongitude);
	}
	public GPSLocation(DatabaseObject obj,
			String GPSLatitude, String GPSLongitude) {
		fill(obj,GPSLatitude,GPSLongitude);
	}
	
	public void fill(DatabaseObject obj, String GPSLatitude, String GPSLongitude) {
		super.fill(obj);
		this.GPSLatitude = GPSLatitude;
		this.GPSLongitude = GPSLongitude;		
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
	
	
	public void setGPSLatitude(String gPSLatitude) {
		GPSLatitude = gPSLatitude;
	}
	public void setGPSLongitude(String gPSLongitude) {
		GPSLongitude = gPSLongitude;
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Lat: ");
		builder.append(GPSLatitude);
		builder.append(", Long: ");
		builder.append(GPSLongitude);
		builder.append("\n");
		return builder.toString();
	}

}
