package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.DataManagementPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DataManagementView;

public class DataManagementActivity extends AbstractActivity implements DataManagementView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public DataManagementActivity()  {
	}
	public DataManagementActivity(DataManagementPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		DataManagementView DataManagementView = clientFactory.getDataManagementView();
		DataManagementView.setName(name);
		DataManagementView.setPresenter(this);
		containerWidget.setWidget(DataManagementView.asWidget());
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
