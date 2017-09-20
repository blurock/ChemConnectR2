package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;


import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectAdministrationImpl;
import info.esblurock.reaction.chemconnect.core.client.mvp.AppActivityMapper;
import info.esblurock.reaction.chemconnect.core.client.mvp.AppPlaceHistoryMapper;
import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectAdministrationPlace;
import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationService;
import info.esblurock.reaction.chemconnect.core.common.client.async.InitializationServiceAsync;

public class ChemConnectCore implements EntryPoint {
	private Place defaultPlace = new ChemConnectAdministrationPlace("Top");
	private SimplePanel appWidget = new SimplePanel();

	@Override
	public void onModuleLoad() {
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		//RootPanel.get().add(appWidget);
		ChemConnectAdministrationImpl impl = new ChemConnectAdministrationImpl();
		RootPanel.get().add(impl);
		//historyHandler.handleCurrentHistory();
			
		
		/*
		RootPanel.get().add(new Button("Press me now please"));
		InitializationServiceAsync async = GWT.create(InitializationService.class);
		InitializationCallback callback = new InitializationCallback();
		async.initializeDatabaseObjects(callback);
		*/
	}

}
