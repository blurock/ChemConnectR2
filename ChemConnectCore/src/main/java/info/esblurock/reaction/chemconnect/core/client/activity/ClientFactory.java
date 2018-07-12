package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();

	ChemConnectAdministrationView getChemConnectAdministrationView();
	ChemConnectObservationView getChemConnectObservationView();
}
