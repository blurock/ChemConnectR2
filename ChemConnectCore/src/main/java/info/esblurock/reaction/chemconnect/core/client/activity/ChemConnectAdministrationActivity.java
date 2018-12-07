package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectAdministrationPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectAdministrationView;

public class ChemConnectAdministrationActivity extends AbstractActivity implements ChemConnectAdministrationView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public ChemConnectAdministrationActivity() {
		
	}
	public ChemConnectAdministrationActivity(ChemConnectAdministrationPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ChemConnectAdministrationView chemConnectAdministrationView = clientFactory.getChemConnectAdministrationView();
		chemConnectAdministrationView.setName(name);
		chemConnectAdministrationView.setPresenter(this);
		containerWidget.setWidget(chemConnectAdministrationView.asWidget());
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
