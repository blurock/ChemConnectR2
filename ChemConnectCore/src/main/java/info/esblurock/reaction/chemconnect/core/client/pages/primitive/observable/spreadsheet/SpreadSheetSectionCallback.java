package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

public class SpreadSheetSectionCallback implements AsyncCallback<ArrayList<SpreadSheetRow>>{

	SpreadSheetMatrix top;	
	public SpreadSheetSectionCallback(SpreadSheetMatrix top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<SpreadSheetRow> results) {
		Window.alert("SpreadSheetSectionCallback: Number of Section:  " + results.size());
		top.setUpResultMatrix(results);
	}

}
