package info.esblurock.reaction.core.server.url;

//import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.reaction.core.server.db.extract.GeocodingLatituteAndLongitude;

public class FindCoordinatesTests {

	@Test
	public void test() {
		GeocodingLatituteAndLongitude geo = new GeocodingLatituteAndLongitude();
		try {
			geo.coordinates("Paris", "France");
			System.out.println(geo.getLatitude() + ", " + geo.getLongitude());
			
			geo.coordinates("Laguna Beach", "USA");
			System.out.println(geo.getLatitude() + ", " + geo.getLongitude());
			
			geo.coordinates("New York", "USA");
			System.out.println(geo.getLatitude() + ", " + geo.getLongitude());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
