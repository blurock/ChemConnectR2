package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectAdministrationImpl;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;

public class ClientFactoryImpl implements ClientFactory {
	private final SimpleEventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(eventBus);
	private final ChemConnectAdministrationView chemConnectAdministrationView = new ChemConnectAdministrationImpl();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public ChemConnectAdministrationView getChemConnectAdministrationView() {
		// TODO Auto-generated method stub
		return chemConnectAdministrationView;
	}

}
