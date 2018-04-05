package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class DeleteObjectCallback implements AsyncCallback<Void> {

	String objectname;
	
	public DeleteObjectCallback(String objectname) {
		this.objectname = objectname;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("");
	}

	@Override
	public void onSuccess(Void arg0) {
		MaterialToast.fireToast("Deletion Successful: " + objectname);
	}

}
