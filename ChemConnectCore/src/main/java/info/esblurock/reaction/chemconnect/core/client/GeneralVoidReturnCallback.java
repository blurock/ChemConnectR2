package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

public class GeneralVoidReturnCallback implements AsyncCallback<Void> {
	String message;
	public GeneralVoidReturnCallback(String message) {
		this.message = message;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: in call\n" + arg0.toString());
	}

	@Override
	public void onSuccess(Void arg0) {
		MaterialLoader.loading(false);
		MaterialToast.fireToast(message);
	}

}
