package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationService;
import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationServiceAsync;

public class ChemConnectCore implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new Button("Press me"));
		InitializationServiceAsync async = GWT.create(InitializationService.class);
		InitializationCallback callback = new InitializationCallback();
		async.initializeDatabaseObjects(callback);
	}

}
