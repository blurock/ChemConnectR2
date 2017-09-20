package info.esblurock.reaction.chemconnect.core.client.activity;

import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();

	ChemConnectAdministrationView getChemConnectAdministrationView();
}
