package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

@RemoteServiceRelativePath("spreadsheet")
public interface SpreadSheetServices extends RemoteService {
	public static class Util {
		private static SpreadSheetServicesAsync instance;

		public static SpreadSheetServicesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(SpreadSheetServices.class);
			}
			return instance;
		}
	}

	ArrayList<ObservationValueRow> getSpreadSheetRows(String parent, int start, int limit) throws IOException;

	DatabaseObjectHierarchy interpretSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid, boolean writeObjects)
			throws IOException;

	DatabaseObjectHierarchy interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input, 
			DataCatalogID catid,
			boolean writeObject) throws IOException;

	void deleteSpreadSheetTransaction(String filename) throws IOException;
	DatabaseObjectHierarchy isolateFromMatrix(DataCatalogID catid,  
			DatabaseObjectHierarchy matrixhier, 
			SpreadSheetBlockIsolation blockisolate) throws IOException;
}
