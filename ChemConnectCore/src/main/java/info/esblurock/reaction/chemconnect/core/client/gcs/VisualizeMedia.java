package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.io.Serializable;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetMatrix;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public enum VisualizeMedia {
	SpreadSheetTabDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.TabDelimited,
					spread.getSourceType(),spread.getSource());
			getSpreadSheetInterpretation("SpreadSheetTabDelimited",info,modified,visual);
		}
		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			Window.alert("SpreadSheetTabDelimited: insertVisualization");
				insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, SpreadSheetSpaceDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.SpaceDelimited,
					spread.getSourceType(),spread.getSource());
			getSpreadSheetInterpretation("SpreadSheetSpaceDelimited",info,modified,visual);
		}
		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, SpreadSheetCSV {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.CSV,
					spread.getSourceType(),spread.getSource());
			getSpreadSheetInterpretation("SpreadSheetCSV",info,modified,visual);
		}
		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, MSExcel {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource());
			getSpreadSheetInterpretation("MSExcel",info,modified,visual);
		}
		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, ExcelOffice {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource());
			getSpreadSheetInterpretation("ExcelOffice",info,modified,visual);
		}
		@Override
		public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	};
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(Serializable object, String title, VisualizationOfBlobStorage visual);
	
	
	void getSpreadSheetInterpretation(String type, 
			GCSBlobFileInformation info, 
			SpreadSheetInputInformation spread, 
			VisualizationOfBlobStorage visual) {
		VisualizeMediaCallback callback = new VisualizeMediaCallback(type, info.getGSFilename(), visual);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		async.interpretSpreadSheetGCS(info, spread, true, callback);		
	}
	
	void insertSpreadSheetVisualization(Serializable object, String title, VisualizationOfBlobStorage visual) {
		ObservationsFromSpreadSheet observation = (ObservationsFromSpreadSheet) object;
		String id = observation.getBase().getIdentifier();
		int total = observation.getSizeOfMatrix();
		int numcols = observation.getNumberOfColumns();
		SpreadSheetMatrix matrix = new SpreadSheetMatrix(title,id,numcols ,total);
		visual.insertVisualization(matrix);		
	}
	
	
}
