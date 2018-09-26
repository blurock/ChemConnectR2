package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.ObservationsFromSpreadSheetInterface;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ReadInSpreadSheetCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	ObservationsFromSpreadSheetInterface top;
	
	public ReadInSpreadSheetCallback(ObservationsFromSpreadSheetInterface top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy results) {
		top.setUpResultMatrix(results);
	}

}
