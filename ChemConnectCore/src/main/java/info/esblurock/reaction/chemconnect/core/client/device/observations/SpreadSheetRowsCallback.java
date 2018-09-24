package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadResult;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class SpreadSheetRowsCallback implements AsyncCallback<ArrayList<ObservationValueRow>>{

	LoadCallback<ObservationValueRow> callback;
	int start;
	int total;
	
	public SpreadSheetRowsCallback(LoadCallback<ObservationValueRow> callback, int start, int total) {
		this.callback = callback;
		this.start = start;
		this.total = total;
	}
	@Override
	public void onFailure(Throwable caught) {
        GWT.log("Getting people async call failed.", caught);
        callback.onFailure(caught);
	}

	@Override
	public void onSuccess(ArrayList<ObservationValueRow> result) {
		callback.onSuccess(new LoadResult<ObservationValueRow>(result, start, total));
		
	}

}
