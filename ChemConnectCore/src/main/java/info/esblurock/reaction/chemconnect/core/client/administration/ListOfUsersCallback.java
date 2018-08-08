package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class ListOfUsersCallback implements AsyncCallback<ArrayList<String>> {

	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<String> users) {
		Window.alert("ListOfUsersCallback: " + users.toString());
	}

}
