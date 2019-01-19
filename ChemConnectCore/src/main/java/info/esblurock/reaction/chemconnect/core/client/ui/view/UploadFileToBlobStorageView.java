package info.esblurock.reaction.chemconnect.core.client.ui.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface UploadFileToBlobStorageView extends IsWidget {
	void setName(String helloName);
	void setPresenter(Presenter listener);
	public void refresh();

	public interface Presenter {
		void goTo(Place place);
	}
	
}
