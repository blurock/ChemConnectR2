package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectPartnersPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectPartnersView;

public class ChemConnectPartnersActivity extends AbstractActivity implements ChemConnectPartnersView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public ChemConnectPartnersActivity()  {
	}
	public ChemConnectPartnersActivity(ChemConnectPartnersPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ChemConnectPartnersView chemConnectPartnersView = clientFactory.getChemConnectPartnersView();
		chemConnectPartnersView.setName(name);
		chemConnectPartnersView.setPresenter(this);
		containerWidget.setWidget(chemConnectPartnersView.asWidget());
	}
	   @Override
	    public String mayStop() {
			return null;
	    }

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
