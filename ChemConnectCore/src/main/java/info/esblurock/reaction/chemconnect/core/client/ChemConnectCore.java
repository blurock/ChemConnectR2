package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.client.activity.ClientFactory;
import info.esblurock.reaction.chemconnect.core.client.mvp.AppActivityMapper;
import info.esblurock.reaction.chemconnect.core.client.mvp.AppPlaceHistoryMapper;
import info.esblurock.reaction.chemconnect.core.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.client.async.LoginServiceAsync;

public class ChemConnectCore implements EntryPoint {
	//private Place defaultPlace = new ChemConnectObservationPlace("Top");
	//private Place defaultPlace = new TutorialExamplePlace("Top");
	//private Place defaultPlace = new ChemConnectAdministrationPlace("Top");
	private Place defaultPlace = new FirstSiteLandingPagePlace("Top");
	
	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {
		
		LoginServiceAsync async = LoginService.Util.getInstance();
		SimpleLoginCallback callback = new SimpleLoginCallback();
		async.loginServer("Guest", callback);
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
				
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		TopChemConnectPanel toppanel = new TopChemConnectPanel(clientFactory);
		activityManager.setDisplay(toppanel.getContentPanel());

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		//toppanel.getContentPanel().add(new FirstSiteLandingPage());
		
		RootPanel.get().add(toppanel);
		historyHandler.handleCurrentHistory();
	}

}
