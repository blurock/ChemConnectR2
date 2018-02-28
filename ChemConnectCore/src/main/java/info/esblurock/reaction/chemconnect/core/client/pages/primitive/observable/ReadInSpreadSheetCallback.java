package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;

public class ReadInSpreadSheetCallback  implements AsyncCallback<ObservationsFromSpreadSheet> {

	PrimitiveObservationVauesWithSpecificationRow top;
	
	public ReadInSpreadSheetCallback(PrimitiveObservationVauesWithSpecificationRow top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}

	@Override
	public void onSuccess(ObservationsFromSpreadSheet results) {
		top.setUpResultMatrix(results);
	}

}