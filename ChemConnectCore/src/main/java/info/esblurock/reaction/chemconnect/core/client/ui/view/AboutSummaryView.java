package info.esblurock.reaction.chemconnect.core.client.ui.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface AboutSummaryView  extends IsWidget {
	void setName(String helloName);
	void setPresenter(Presenter listener);
	
	public interface Presenter {
		void goTo(Place place);
	}

}
