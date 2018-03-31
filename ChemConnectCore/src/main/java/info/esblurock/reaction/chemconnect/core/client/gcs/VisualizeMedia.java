package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.io.Serializable;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetMatrix;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
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
			ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			async.interpretSpreadSheetGCS(info, spread, callback);
			
			Window.alert("VisualizeMedia:  getInterpretedBlob");
		}

		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			Window.alert("VisualizeMedia:  insertVisualization 1");
			ObservationsFromSpreadSheet observation = (ObservationsFromSpreadSheet) object;
			SpreadSheetMatrix matrix = new SpreadSheetMatrix(title, observation.getMatrix());
			visual.insertVisualization(matrix);
			Window.alert("VisualizeMedia:  insertVisualization 2");
		}
		
	};
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual);
}
