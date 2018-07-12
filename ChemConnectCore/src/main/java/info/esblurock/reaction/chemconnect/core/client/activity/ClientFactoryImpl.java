package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectAdministrationImpl;
import info.esblurock.reaction.chemconnect.core.client.observations.ChemConnectObservationImpl;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;

public class ClientFactoryImpl implements ClientFactory {
	private final SimpleEventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(eventBus);
	private final ChemConnectAdministrationView chemConnectAdministrationView = new ChemConnectAdministrationImpl();
	private final ChemConnectObservationView chemConnectObservationView = new ChemConnectObservationImpl();

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
		return chemConnectAdministrationView;
	}
	@Override
	public ChemConnectObservationView getChemConnectObservationView() {
		return chemConnectObservationView;
	}

}
