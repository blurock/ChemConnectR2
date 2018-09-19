package info.esblurock.reaction.core.server.services;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSInputFileInterpretation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.GCSServiceRoutines;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;
import info.esblurock.reaction.core.server.read.InterpretSpreadSheet;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;

@SuppressWarnings("serial")
public class SpreadSheetServicesImpl extends ServerBase implements SpreadSheetServices {

	public ArrayList<ObservationValueRow> getSpreadSheetRows(String parent, int start, int limit) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user);

		System.out.println("SpreadSheetServicesImpl: getSpreadSheetRows " + parent + "  "  + start + "  " + limit);
		ArrayList<ObservationValueRow> lst = new ArrayList<ObservationValueRow>();
		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		int startI = start;
		int endI = start + limit;
		QueryPropertyValue startquery = new QueryPropertyValue("rowNumber >=", startI);
		QueryPropertyValue endquery = new QueryPropertyValue("rowNumber <", endI);
		queryvalues.add(startquery);
		queryvalues.add(endquery);
		QuerySetupBase query = new QuerySetupBase(user.getName(),ObservationValueRow.class.getCanonicalName(), queryvalues);
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			for (DatabaseObject obj : result.getResults()) {
				ObservationValueRow row = (ObservationValueRow) obj;
				lst.add(row);
			}
		} catch (Exception e) {
			throw new IOException("getSpreadSheetRows: Error trying to read spread sheet rows: " + e.getClass().getSimpleName());
		}
		return lst;
	}

	
	public DatabaseObjectHierarchy interpretSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid, boolean writeObjects) throws IOException {
		System.out.println("interpretSpreadSheet");
		System.out.println("interpretSpreadSheet: " + input.toString());
		DatabaseObjectHierarchy hierarchy = null;
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		try {
			hierarchy = InterpretSpreadSheet.readSpreadSheet(input,catid);
			if(writeObjects) {
				System.out.println("SpreadSheetServicesImpl: interpretSpreadSheet: writeObjects");
				WriteReadDatabaseObjects.writeDatabaseObjectHierarchyWithTransaction(hierarchy);
			}
		} catch (IOException e) {
			throw new IOException("SpreadSheet error: " + e.toString());
		}
		return hierarchy;
	}

	public DatabaseObjectHierarchy interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, 
			SpreadSheetInputInformation input,
			DataCatalogID catid, boolean writeObjects) throws IOException {

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		QueryPropertyValue filequery = new QueryPropertyValue("source", gcsinfo.getGSFilename());
		queryvalues.add(filequery);
		QuerySetupBase query = new QuerySetupBase(user.getName(),SpreadSheetInputInformation.class.getCanonicalName(), queryvalues);
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		DatabaseObjectHierarchy hierarchy = null;
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			if(result.getResults().size() > 0) {
				System.out.println("interpretSpreadSheetGCS:  already stored");
				//hierarchy = new ObservationsFromSpreadSheet(input);
			} else {
				hierarchy = InterpretSpreadSheet.readSpreadSheetFromGCS(gcsinfo, input,catid);
			}
		} catch (Exception e) {
			throw new IOException("Error trying to read spread sheet rows\n" + e.toString());
		}
		if(writeObjects) {
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);
			GCSBlobFileInformation source = new GCSBlobFileInformation(gcsinfo);
			source.setPath(catid.getFullName("/"));
			source.setBucket(GoogleCloudStorageConstants.storageBucket);
			GCSServiceRoutines.moveBlob(source,gcsinfo);
			GCSInputFileInterpretation transaction = new GCSInputFileInterpretation(hierarchy.getObject(),
					source.getBucket(),source.getGSFilename(),
					hierarchy.getObject().getClass().getCanonicalName());
			DatabaseWriteBase.writeObjectWithTransaction(transaction);
		}
		System.out.println("interpretSpreadSheetGCS: \n" + hierarchy.toString());
		return hierarchy;
	}
	
	public void deleteSpreadSheetTransaction(String filename) throws IOException {
		System.out.println("deleteSpreadSheetTransaction:  " + filename);
		SpreadSheetInputInformation spreadsheet = (SpreadSheetInputInformation) 
				QueryBase.getFirstDatabaseObjectsFromSingleProperty(SpreadSheetInputInformation.class.getCanonicalName(), 
				"source", filename);
		String sourceID = spreadsheet.getSourceID();
		UserImageServiceImpl.deleteTransactionFromSourceID(sourceID);
	}
	
	
	
}
