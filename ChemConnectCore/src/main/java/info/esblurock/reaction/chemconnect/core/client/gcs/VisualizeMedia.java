package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetMatrix;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public enum VisualizeMedia {
	SpreadSheet {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			VisualizeMediaCallback callback = new VisualizeMediaCallback("SpreadSheet",
					info.getGSFilename(), visual);
			SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			async.interpretSpreadSheetGCS(info, spread, true, callback);
		}

		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			ObservationsFromSpreadSheet observation = (ObservationsFromSpreadSheet) object;
			String id = observation.getBase().getIdentifier();
			int total = observation.getSizeOfMatrix();
			int numcols = observation.getNumberOfColumns();
			SpreadSheetMatrix matrix = new SpreadSheetMatrix(title,id,numcols ,total);
			visual.insertVisualization(matrix);
		}
		
	};
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual);
}
