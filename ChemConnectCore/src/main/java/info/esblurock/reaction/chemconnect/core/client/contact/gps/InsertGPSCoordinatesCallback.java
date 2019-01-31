package info.esblurock.reaction.chemconnect.core.client.contact.gps;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;

public class InsertGPSCoordinatesCallback implements AsyncCallback<GPSLocation> {

	InsertGPSCoordinatesInterface insert;
	
	public InsertGPSCoordinatesCallback(InsertGPSCoordinatesInterface insert) {
		this.insert = insert;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("Unable to retrieve GPS coordinates");
	}

	@Override
	public void onSuccess(GPSLocation coordinates) {
		MaterialLoader.loading(false);
		insert.insertGPSCoordinates(coordinates);
	}

}
