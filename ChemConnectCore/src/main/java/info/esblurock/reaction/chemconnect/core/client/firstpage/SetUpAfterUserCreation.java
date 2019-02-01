package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.SimpleLoginCallback;
import info.esblurock.reaction.chemconnect.core.client.TopChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetUpAfterUserCreation implements AsyncCallback<DatabaseObjectHierarchy> {

	MaterialCollapsible panel;
	MaterialPanel modalpanel;
	TopChemConnectPanel toppanel;
	
	public SetUpAfterUserCreation(TopChemConnectPanel toppanel, MaterialCollapsible panel,MaterialPanel modalpanel) {
		this.panel = panel;
		this.modalpanel = modalpanel;
		this.toppanel = toppanel;
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Set up after user creation:\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		MaterialLoader.loading(false);
		String account = Cookies.getCookie("account_name");
		Cookies.setCookie("user", account);
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,hierarchy,modalpanel);		
		panel.add(item);
		LoginServiceAsync async = LoginService.Util.getInstance();
		SimpleLoginCallback callback = new SimpleLoginCallback(null,toppanel.getClientFactory());
		async.getUserInfo(callback);
	}

}
