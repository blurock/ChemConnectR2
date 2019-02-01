package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;

public class SimpleLoginCallback implements AsyncCallback<UserDTO> {

	ChemConnectCore coreentry;
	ClientFactory clientFactory;
	public SimpleLoginCallback(ChemConnectCore coreentry,ClientFactory clientFactory) {
		this.coreentry = coreentry;
		this.clientFactory= clientFactory;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable caught) {
		MaterialLoader.loading(false);
		Window.alert("Login fail\n" + caught.toString());
		clientFactory.getPlaceController().goTo(new FirstSiteLandingPagePlace("Logged in user"));
	}

	@Override
	public void onSuccess(UserDTO result) {
		MaterialLoader.loading(false);
		SetUpUserCookies.setup(result);
		clientFactory.getTopPanel().setInUser();
		if(coreentry != null) {
			coreentry.setUpInterface(clientFactory);
		}
	}
	

}
