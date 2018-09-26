package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.SpreadSheetBlockMatrix;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
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
		async.interpretSpreadSheetGCS(info, spread, catid, true, callback);		
	}
	
	void insertSpreadSheetVisualization(DatabaseObjectHierarchy hierarchy, String title, VisualizationOfBlobStorage visual) {
		Window.alert("VisualizeMedia  insertSpreadSheetVisualization: \n" + 
				hierarchy.getObject().getClass().getSimpleName());
		SpreadSheetBlockMatrix matrix = new SpreadSheetBlockMatrix(hierarchy);
		visual.insertVisualization(matrix);	
		
		/*
		ObservationsFromSpreadSheet observation = (ObservationsFromSpreadSheet) hierarchy.getObject();
		DatabaseObjectHierarchy inputhier = hierarchy.getSubObject(observation.getSpreadSheetInterpretation());
		SpreadSheetInterpretation input = (SpreadSheetInterpretation) inputhier.getObject();
		String id = observation.getIdentifier();
		int total = input.getEndRow() - input.getStartRow();
		int numcols = input.getEndColumn() - input.getStartColumn();
		SpreadSheetMatrix matrix = new SpreadSheetMatrix(title,id,numcols ,total);
		DatabaseObjectHierarchy obsrowshier = hierarchy.getSubObject(observation.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) obsrowshier.getObject();
		DatabaseObjectHierarchy multrowshier = obsrowshier.getSubObject(values.getObservationRowValue());
		
		ChemConnectCompoundMultiple multrows = (ChemConnectCompoundMultiple) multrowshier.getObject();
		ArrayList<ObservationValueRow> lst = new ArrayList<ObservationValueRow>();
		for(DatabaseObjectHierarchy rowhier : multrowshier.getSubobjects()) {
			ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
			lst.add(row);
		}
		matrix.setUpResultMatrix(lst);
		visual.insertVisualization(matrix);		
		*/
	}
	
	
}
