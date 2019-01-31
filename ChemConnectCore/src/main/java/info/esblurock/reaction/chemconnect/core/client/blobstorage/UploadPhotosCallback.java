package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;


public class UploadPhotosCallback implements AsyncCallback<ArrayList<UploadedImage>>{

	BlobStorageUploadInterface upload;
	
	
	public UploadPhotosCallback(BlobStorageUploadInterface upload) {
		this.upload = upload;
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable caught) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Upload Photos\n" + caught.toString());
	}

	@Override
	public void onSuccess(ArrayList<UploadedImage> result) {
		MaterialLoader.loading(false);
		for(UploadedImage imageinfo : result) {
			upload.addImage(imageinfo);
		}
	}

}
