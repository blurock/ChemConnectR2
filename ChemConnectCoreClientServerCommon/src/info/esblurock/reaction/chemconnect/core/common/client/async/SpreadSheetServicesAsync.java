package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public interface SpreadSheetServicesAsync {
	void getSpreadSheetRows(String parent, int start, int limit,
			AsyncCallback<ArrayList<ObservationValueRow>> callback);

	void interpretSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid, boolean writeObjects,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input, DataCatalogID catid,
			boolean writeObject, AsyncCallback<DatabaseObjectHierarchy> callback);

	void deleteSpreadSheetTransaction(String filename, AsyncCallback<Void> callback);

	void isolateFromMatrix(DataCatalogID catid, DatabaseObjectHierarchy matrixhier,
			SpreadSheetBlockIsolation blockisolate, AsyncCallback<DatabaseObjectHierarchy> callback);
}
