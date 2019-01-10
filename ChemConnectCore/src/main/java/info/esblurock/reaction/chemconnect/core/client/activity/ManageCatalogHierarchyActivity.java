package info.esblurock.reaction.chemconnect.core.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ManageCatalogHierarchyView;

public class ManageCatalogHierarchyActivity extends AbstractActivity implements ManageCatalogHierarchyView.Presenter {
	private ClientFactory clientFactory;
	private String name;
	
	public ManageCatalogHierarchyActivity() {
		
	}
	public ManageCatalogHierarchyActivity(ManageCatalogHierarchyPlace place, ClientFactory clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ManageCatalogHierarchyView ManageCatalogHierarchyView = clientFactory.getManageCatalogHierarchyView();
		ManageCatalogHierarchyView.setName(name);
		ManageCatalogHierarchyView.setPresenter(this);
		ManageCatalogHierarchyView.setUpHierarchyFromDatabase();
		containerWidget.setWidget(ManageCatalogHierarchyView.asWidget());
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
