package info.esblurock.reaction.chemconnect.core.client.gcs;

import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public enum VisualizeMedia {
	FileTypeSpreadSheetTabDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.TabDelimited,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetTabDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
				insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeSpreadSheetSpaceDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.SpaceDelimited,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetSpaceDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeSpreadSheetCSV {

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
					spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetCSV",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeMSExcel {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeMSExcel",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeExcelOffice {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLSX,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeExcelOffice", catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeImageJPEG {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageJPG {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageBMP {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageGIF {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}
	;
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	
	
	protected void getInterpretedBlobImage(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
			boolean titleGiven, VisualizationOfBlobStorage visual) {
				
		
	}
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual);
	
	
	void interpretSpreadSheetAsMatrix(String type, 
			DataCatalogID catid,
			GCSBlobFileInformation info,
			SpreadSheetInputInformation spread, 
			VisualizationOfBlobStorage visual) {
		VisualizeMediaCallback callback = new VisualizeMediaCallback(type, info.getGSFilename(), visual);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		async.interpretSpreadSheetGCS(info, spread, catid, true, callback);		
	}
	
	void insertSpreadSheetVisualization(DatabaseObjectHierarchy hierarchy, String title, VisualizationOfBlobStorage visual) {
		visual.insertCatalogObject(hierarchy);
	}
	
	
}
