package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;


public class ImageServiceCallback implements AsyncCallback<ImageServiceInformation>{

	UploadPhoto uploadPhoto;
	boolean uploadService;
	
	public ImageServiceCallback(boolean uploadService, UploadPhoto uploadPhoto) {
		this.uploadPhoto = uploadPhoto;
		this.uploadService = uploadService;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("startNewBlobstoreSession: " + caught.toString());
	}

	@Override
	public void onSuccess(ImageServiceInformation result) {
		uploadPhoto.fillUpload(result);
	}

}
