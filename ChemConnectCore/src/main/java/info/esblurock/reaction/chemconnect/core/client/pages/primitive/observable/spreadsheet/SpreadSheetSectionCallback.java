package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class SpreadSheetSectionCallback implements AsyncCallback<ArrayList<ObservationValueRow>>{

	SpreadSheetMatrix top;	
	public SpreadSheetSectionCallback(SpreadSheetMatrix top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Spreadsheet\n" + arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<ObservationValueRow> results) {
		MaterialLoader.loading(false);
		Window.alert("SpreadSheetSectionCallback: Number of Section:  " + results.size());
		top.setUpResultMatrix(results);
	}

}
