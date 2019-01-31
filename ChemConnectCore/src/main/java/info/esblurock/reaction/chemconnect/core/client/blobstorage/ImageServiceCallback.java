package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;


public class ImageServiceCallback implements AsyncCallback<ImageServiceInformation>{

	BlobStorageUploadInterface uploadPhoto;
	boolean uploadService;
	
	public ImageServiceCallback(boolean uploadService, BlobStorageUploadInterface uploadPhoto) {
		this.uploadPhoto = uploadPhoto;
		this.uploadService = uploadService;
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable caught) {
		MaterialLoader.loading(false);
		Window.alert("startNewBlobstoreSession: " + caught.toString());
	}

	@Override
	public void onSuccess(ImageServiceInformation result) {
		uploadPhoto.fillUpload(result);
		MaterialLoader.loading(false);
	}

}
