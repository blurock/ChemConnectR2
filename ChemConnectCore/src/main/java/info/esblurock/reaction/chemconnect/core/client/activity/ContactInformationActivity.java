package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ContactInformationPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ContactInformationView;

public class ContactInformationActivity extends AbstractActivity implements ContactInformationView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public ContactInformationActivity()  {
	}
	public ContactInformationActivity(ContactInformationPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ContactInformationView ContactInformationView = clientFactory.getContactInformationView();
		ContactInformationView.setName(name);
		ContactInformationView.setPresenter(this);
		containerWidget.setWidget(ContactInformationView.asWidget());
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
