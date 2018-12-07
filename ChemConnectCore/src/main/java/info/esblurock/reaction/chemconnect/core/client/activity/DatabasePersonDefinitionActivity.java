package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DatabasePersonDefinitionView;

public class DatabasePersonDefinitionActivity extends AbstractActivity implements DatabasePersonDefinitionView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public DatabasePersonDefinitionActivity() {
		
	}
	public DatabasePersonDefinitionActivity(DatabasePersonDefinitionPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		DatabasePersonDefinitionView DatabasePersonDefinitionView = clientFactory.getDatabasePersonDefinitionView();
		DatabasePersonDefinitionView.setName(name);
		DatabasePersonDefinitionView.setPresenter(this);
		containerWidget.setWidget(DatabasePersonDefinitionView.asWidget());
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
