package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.IsolateMatrixBlockView;

public class IsolateMatrixBlockActivity extends AbstractActivity implements IsolateMatrixBlockView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public IsolateMatrixBlockActivity() {
		
	}
	public IsolateMatrixBlockActivity(IsolateMatrixBlockPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		IsolateMatrixBlockView IsolateMatrixBlockView = clientFactory.getIsolateMatrixBlockView();
		IsolateMatrixBlockView.setName(name);
		IsolateMatrixBlockView.setPresenter(this);
		clientFactory.setInUser();
		IsolateMatrixBlockView.refresh();
		containerWidget.setWidget(IsolateMatrixBlockView.asWidget());
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
