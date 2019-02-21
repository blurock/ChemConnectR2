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
		SetUpUserCookies.setupDefaultGuestUserCookies();
		MaterialLoader.loading(false);
		Window.alert("Login fail\n" + caught.toString());
		if(coreentry != null) {
			coreentry.setUpInterface(clientFactory);
			clientFactory.getTopPanel().setInUser();
		}
		
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
