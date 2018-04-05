package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;
import info.esblurock.reaction.chemconnect.core.data.observations.VisualizeObservationBase;

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

	ArrayList<SpreadSheetRow> getSpreadSheetRows(String parent, int start, int limit) throws IOException;

	ObservationsFromSpreadSheet interpretSpreadSheet(SpreadSheetInputInformation input, boolean writeObjects)
			throws IOException;

	VisualizeObservationBase interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input,
			boolean writeObject) throws IOException;

	void deleteSpreadSheetTransaction(String filename) throws IOException;

}
