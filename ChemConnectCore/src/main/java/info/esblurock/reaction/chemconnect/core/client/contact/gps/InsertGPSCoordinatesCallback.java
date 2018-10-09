package info.esblurock.reaction.chemconnect.core.client.contact.gps;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;

public class InsertGPSCoordinatesCallback implements AsyncCallback<GPSLocation> {

	InsertGPSCoordinatesInterface insert;
	
	public InsertGPSCoordinatesCallback(InsertGPSCoordinatesInterface insert) {
		this.insert = insert;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("Unable to retrieve GPS coordinates");
	}

	@Override
	public void onSuccess(GPSLocation coordinates) {
		insert.insertGPSCoordinates(coordinates);
	}

}
