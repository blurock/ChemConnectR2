package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class UpdateImageCallback implements AsyncCallback<String> {

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(String result) {
		MaterialToast.fireToast(result);
	}

}
