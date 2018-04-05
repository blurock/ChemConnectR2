package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;
import info.esblurock.reaction.chemconnect.core.data.observations.VisualizeObservationBase;

public interface SpreadSheetServicesAsync {
	void getSpreadSheetRows(String parent, int start, int limit,
			AsyncCallback<ArrayList<SpreadSheetRow>> callback);

	void interpretSpreadSheet(SpreadSheetInputInformation input, boolean writeObjects,
			AsyncCallback<ObservationsFromSpreadSheet> callback);

	void interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input, boolean writeObject,
			AsyncCallback<VisualizeObservationBase> callback);

	void deleteSpreadSheetTransaction(String filename, AsyncCallback<Void> callback);
}
