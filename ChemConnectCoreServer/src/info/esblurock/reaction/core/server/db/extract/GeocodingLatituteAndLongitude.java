package info.esblurock.reaction.core.server.db.extract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;


import info.esblurock.reaction.core.server.services.ServerBase;


public class GeocodingLatituteAndLongitude {

	String msg;
	JSONObject jsonobj;
	String latitude;
	String longitude;
	String location;
	
	public void coordinates(String cityS, String countryS) throws IOException {
		String locationS = cityS + " " + countryS;
		coordinates(locationS);
	}
	
	public void coordinates(String locationS) throws IOException {

		String msg = null;
		try {
			String charset = StandardCharsets.UTF_8.name();
			String api = "https://maps.googleapis.com/maps/api/geocode/json";
			String address = "?address=" + URLEncoder.encode(locationS, charset);
			String key = "&key=" + ServerBase.googleAPIKey;
			
			URL url = new URL(api + address  + key);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuilder build = new StringBuilder();
			while ((output = br.readLine()) != null) {
				build.append(output);
				build.append("\n");
			}
			msg = build.toString();

			jsonobj = new JSONObject(msg);
			JSONArray results = jsonobj.getJSONArray("results");
			boolean notdone = true;
			int i=0;
			while(notdone && i<results.length()) {
				JSONObject subobj = (JSONObject) results.getJSONObject(i);
				if(subobj.has("geometry")) {
					notdone = process(subobj);
				}
				i++;
			}
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	private boolean process(JSONObject subobj) {
		boolean ans = false;
		JSONObject geoobj = subobj.getJSONObject("geometry");
		JSONObject loc = geoobj.getJSONObject("location");
		Object latS = loc.get("lat");
		latitude = latS.toString();
		Object lngS = loc.get("lng");
		longitude = lngS.toString();
		System.out.println("Latitute:  " + latitude);
		System.out.println("Longitude: " + longitude);
		return ans;
	}
	
	public String getMessage() {
		return msg;
	}
	public JSONObject getJSONObject() {
		return jsonobj;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getLocation() {
		return location;
	}

}
