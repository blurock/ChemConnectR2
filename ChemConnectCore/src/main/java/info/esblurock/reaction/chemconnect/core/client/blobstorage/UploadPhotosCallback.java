package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;


public class UploadPhotosCallback implements AsyncCallback<ArrayList<UploadedImage>>{

	UploadPhoto upload;
	
	
	public UploadPhotosCallback(UploadPhoto upload) {
		this.upload = upload;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(ArrayList<UploadedImage> result) {
		Window.alert("UploadPhotosCallback: " + result.size());
		for(UploadedImage imageinfo : result) {
			upload.addImage(imageinfo);
		}
		//upload.startNewBlobstoreSession(false);
	}

}
