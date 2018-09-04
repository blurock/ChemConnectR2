package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class GeneralVoidReturnCallback implements AsyncCallback<Void> {
	String message;
	public GeneralVoidReturnCallback(String message) {
		this.message = message;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(Void arg0) {
		MaterialToast.fireToast(message);
	}

}
