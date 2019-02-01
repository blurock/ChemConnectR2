package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectObservationPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ChemConnectObservationView;


public class SetOfObservationsDefinitionActivity extends AbstractActivity implements ChemConnectObservationView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public SetOfObservationsDefinitionActivity() {
		
	}
	public SetOfObservationsDefinitionActivity(ChemConnectObservationPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ChemConnectObservationView SetOfObservationsDefinitionView = clientFactory.getChemConnectObservationView();
		SetOfObservationsDefinitionView.setName(name);
		SetOfObservationsDefinitionView.setPresenter(this);
		containerWidget.setWidget(SetOfObservationsDefinitionView.asWidget());
		clientFactory.setInUser();
		SetOfObservationsDefinitionView.refresh();
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
