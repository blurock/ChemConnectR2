package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.io.Serializable;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.SpreadSheetMatrix;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInterpretation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public enum VisualizeMedia {
	SpreadSheetTabDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.TabDelimited,
					spread.getSourceType(),spread.getSource(),titleGiven);
			getSpreadSheetInterpretation("SpreadSheetTabDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			Window.alert("SpreadSheetTabDelimited: insertVisualization");
				insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, SpreadSheetSpaceDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.SpaceDelimited,
					spread.getSourceType(),spread.getSource(),
					titleGiven);
			getSpreadSheetInterpretation("SpreadSheetSpaceDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, SpreadSheetCSV {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.CSV,
					spread.getSourceType(),
					spread.getSource(),
					titleGiven);
			getSpreadSheetInterpretation("SpreadSheetCSV",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, MSExcel {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource(),
					titleGiven);
			getSpreadSheetInterpretation("MSExcel",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, ExcelOffice {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource(),
					titleGiven);
			getSpreadSheetInterpretation("ExcelOffice", catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	};
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual);
	
	
	void getSpreadSheetInterpretation(String type, 
			DataCatalogID catid,
			GCSBlobFileInformation info,
			SpreadSheetInputInformation spread, 
			VisualizationOfBlobStorage visual) {
		VisualizeMediaCallback callback = new VisualizeMediaCallback(type, info.getGSFilename(), visual);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		DatabaseObject obj = new DatabaseObject(spread);
		async.interpretSpreadSheetGCS(info, spread, catid, true, callback);		
	}
	
	void insertSpreadSheetVisualization(DatabaseObjectHierarchy hierarchy, String title, VisualizationOfBlobStorage visual) {
		ObservationsFromSpreadSheet observation = (ObservationsFromSpreadSheet) hierarchy.getObject();
		
		DatabaseObjectHierarchy inputhier = hierarchy.getSubObject(observation.getSpreadSheetInputInformation());
		SpreadSheetInterpretation values = (SpreadSheetInterpretation) inputhier.getObject();
		
		String id = observation.getIdentifier();
		int total = values.getEndRow() - values.getStartRow();
		int numcols = values.getEndColumn() - values.getStartColumn();
		SpreadSheetMatrix matrix = new SpreadSheetMatrix(title,id,numcols ,total);
		visual.insertVisualization(matrix);		
	}
	
	
}
