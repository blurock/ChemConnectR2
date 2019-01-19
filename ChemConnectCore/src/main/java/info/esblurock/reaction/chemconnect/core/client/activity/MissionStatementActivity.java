package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.MissionStatementPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.MissionStatementView;

public class MissionStatementActivity extends AbstractActivity implements MissionStatementView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public MissionStatementActivity()  {
	}
	public MissionStatementActivity(MissionStatementPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		MissionStatementView missionStatementView = clientFactory.getMissionStatementView();
		missionStatementView.setName(name);
		missionStatementView.setPresenter(this);
		containerWidget.setWidget(missionStatementView.asWidget());
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
