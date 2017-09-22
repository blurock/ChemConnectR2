package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InitializationCallback implements AsyncCallback<Void> {
	String message;
	public InitializationCallback(String message) {
		this.message = message;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(Void arg0) {
		Window.alert(message);
	}

}
