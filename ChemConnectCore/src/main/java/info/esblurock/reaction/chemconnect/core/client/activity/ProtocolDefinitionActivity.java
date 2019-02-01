package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ProtocolDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ProtocolDefinitionView;

public class ProtocolDefinitionActivity extends AbstractActivity implements ProtocolDefinitionView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public ProtocolDefinitionActivity() {
		
	}
	public ProtocolDefinitionActivity(ProtocolDefinitionPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ProtocolDefinitionView ProtocolDefinitionView = clientFactory.getProtocolDefinitionView();
		ProtocolDefinitionView.setName(name);
		ProtocolDefinitionView.setPresenter(this);
		containerWidget.setWidget(ProtocolDefinitionView.asWidget());
		clientFactory.setInUser();
		ProtocolDefinitionView.refresh();
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
