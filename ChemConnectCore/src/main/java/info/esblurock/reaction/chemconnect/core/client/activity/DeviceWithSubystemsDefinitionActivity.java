package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.DeviceWithSubystemsDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.DeviceWithSubystemsDefinitionView;

public class DeviceWithSubystemsDefinitionActivity extends AbstractActivity implements DeviceWithSubystemsDefinitionView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public DeviceWithSubystemsDefinitionActivity() {
		
	}
	public DeviceWithSubystemsDefinitionActivity(DeviceWithSubystemsDefinitionPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		DeviceWithSubystemsDefinitionView DeviceWithSubystemsDefinitionView = clientFactory.getDeviceWithSubystemsDefinitionView();
		DeviceWithSubystemsDefinitionView.setName(name);
		DeviceWithSubystemsDefinitionView.setPresenter(this);
		clientFactory.setInUser();
		DeviceWithSubystemsDefinitionView.refresh();
		containerWidget.setWidget(DeviceWithSubystemsDefinitionView.asWidget());
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
