package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

public class DeleteObjectCallback implements AsyncCallback<Void> {

	String objectname;
	
	public DeleteObjectCallback(String objectname) {
		this.objectname = objectname;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Delete\n" + arg0.toString());
	}

	@Override
	public void onSuccess(Void arg0) {
		MaterialLoader.loading(false);
		MaterialToast.fireToast("Deletion Successful: " + objectname);
	}

}
