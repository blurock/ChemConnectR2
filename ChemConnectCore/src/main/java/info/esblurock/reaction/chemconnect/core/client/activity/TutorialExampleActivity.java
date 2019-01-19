package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.TutorialExamplePlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.TutorialExampleView;

public class TutorialExampleActivity  extends AbstractActivity implements TutorialExampleView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public TutorialExampleActivity() {
	}
	public TutorialExampleActivity(TutorialExamplePlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		TutorialExampleView tutorialExampleView = clientFactory.getTutorialExampleView();
		tutorialExampleView.setName(name);
		tutorialExampleView.setPresenter(this);
		containerWidget.setWidget(tutorialExampleView.asWidget());
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
