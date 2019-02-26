package info.esblurock.reaction.chemconnect.core.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
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
	
	@Override
	public void onModuleLoad() {
		/*
		InitializationServiceAsync initialize = GWT.create(InitializationService.class);
		GeneralVoidReturnCallback initialcallback = new GeneralVoidReturnCallback("Initialization completed");
		initialize.initializeDatabaseObjects(initialcallback);
		*/
		String redirect = Cookies.getCookie("redirect");
		String account_name = Cookies.getCookie("account_name");
		Cookies.removeCookie("redirect");
		boolean firsttime = true;
		if(redirect == null || account_name == null) {
			firsttime = true;
		}  else if(redirect.compareTo(account_name) == 0) {
			firsttime = false;
		}
		if(firsttime) {
			SetUpUserCookies.setupDefaultGuestUserCookies();
			ClientFactory clientFactory = GWT.create(ClientFactory.class);
			LoginServiceAsync async = LoginService.Util.getInstance();
			SimpleLoginCallback callback = new SimpleLoginCallback(this,clientFactory);
			async.loginGuestServer(callback);
		} else {
			ClientFactory clientFactory = GWT.create(ClientFactory.class);
			setUpInterface(clientFactory);
		}
	}
	@SuppressWarnings("deprecation")
	public void setUpInterface(ClientFactory clientFactory) {
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
				
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		TopChemConnectPanel toppanel = clientFactory.getTopPanel();
		//TopChemConnectPanel toppanel = new TopChemConnectPanel(clientFactory);
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
