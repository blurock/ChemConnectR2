package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.ObservationsFromSpreadSheetInterface;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ReadInSpreadSheetCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	ObservationsFromSpreadSheetInterface top;
	
	public ReadInSpreadSheetCallback(ObservationsFromSpreadSheetInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable ex) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Spread Sheet\n" + ex.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy results) {
		top.setUpResultMatrix(results);
		MaterialLoader.loading(false);
	}

}
