package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;


public class ListOfUsersCallback implements AsyncCallback<ArrayList<String>> {
	
	public ListOfUsersCallback() {
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: List of users\n" + arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<String> users) {
		MaterialLoader.loading(false);
		Window.alert("ListOfUsersCallback: \n" + users.toString());
	}

}
