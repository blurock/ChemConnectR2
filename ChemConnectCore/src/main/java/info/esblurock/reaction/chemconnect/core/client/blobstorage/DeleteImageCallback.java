package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

public class DeleteImageCallback implements AsyncCallback<String> {

	ImageColumn image;
	
	public DeleteImageCallback(ImageColumn image) {
		this.image = image;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable caught) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Delete Image\n" + caught.toString());
	}

	@Override
	public void onSuccess(String result) {
		MaterialLoader.loading(false);
		MaterialToast.fireToast(result);
		image.removeFromParent();
	
	}

}
