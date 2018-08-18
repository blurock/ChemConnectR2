package info.esblurock.reaction.core.server.services;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.VisualizeObservationBase;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
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
			throw new IOException("getSpreadSheetRows: Error trying to read spread sheet rows");
		}
		return lst;
	}

	public ObservationsFromSpreadSheet interpretSpreadSheet(SpreadSheetInputInformation input, boolean writeObjects) throws IOException {
		System.out.println("interpretSpreadSheet");
		System.out.println("interpretSpreadSheet: " + input.toString());
		
		
		
		ObservationsFromSpreadSheet obs = null;
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		try {
			obs = InterpretSpreadSheet.readSpreadSheet(writeObjects,input);
		} catch (IOException e) {
			throw new IOException("SpreadSheet error: " + e.toString());
		}
		return obs;
	}

	public VisualizeObservationBase interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input,
			boolean writeObjects) throws IOException {

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user);

		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		QueryPropertyValue filequery = new QueryPropertyValue("source", gcsinfo.getGSFilename());
		queryvalues.add(filequery);
		QuerySetupBase query = new QuerySetupBase(user.getName(),SpreadSheetInputInformation.class.getCanonicalName(), queryvalues);
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		ObservationsFromSpreadSheet obs = null;
		try {
			System.out.println("interpretSpreadSheetGCS:  " + query.toString());
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			System.out.println("interpretSpreadSheetGCS: numbrer of results " + result.getResults().size());
			if(result.getResults().size() > 0) {
				System.out.println("interpretSpreadSheetGCS:  already stored");
				obs = new ObservationsFromSpreadSheet(input);
			} else {
				System.out.println("interpretSpreadSheetGCS:  interpret spreadsheet");
				obs = InterpretSpreadSheet.readSpreadSheetFromGCS( gcsinfo, input,writeObjects);
			}
		} catch (Exception e) {
			throw new IOException("Error trying to read spread sheet rows\n" + e.toString());
		}
		return obs;
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
