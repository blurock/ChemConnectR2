package info.esblurock.reaction.core.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSInputFileInterpretation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.GCSServiceRoutines;
import info.esblurock.reaction.core.server.db.spreadsheet.block.IsolateBlockFromMatrix;
import info.esblurock.reaction.core.server.read.InterpretSpreadSheet;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

@SuppressWarnings("serial")
public class SpreadSheetServicesImpl extends ServerBase implements SpreadSheetServices {

	public ArrayList<ObservationValueRow> getSpreadSheetRows(String parent, int start, int limit) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), 
				getThreadLocalRequest().getSession());
		UserDTO user = util.getUserInfo();
		ArrayList<ObservationValueRow> lst = new ArrayList<ObservationValueRow>();
		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		int startI = start;
		int endI = start + limit;
		QueryPropertyValue startquery = new QueryPropertyValue("rowNumber >=", startI);
		QueryPropertyValue endquery = new QueryPropertyValue("rowNumber <", endI);
		QueryPropertyValue parentquery = new QueryPropertyValue("parentLink", parent);
		queryvalues.add(startquery);
		queryvalues.add(endquery);
		queryvalues.add(parentquery);
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
		Collections.sort(lst, new Comparator<ObservationValueRow>() {
		    public int compare(ObservationValueRow lhs, ObservationValueRow rhs) {
		        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
		        return lhs.getRowNumber() - rhs.getRowNumber();
		    }
		});
		return lst;
	}

	
	public DatabaseObjectHierarchy interpretSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid, boolean writeObjects) throws IOException {
		DatabaseObjectHierarchy hierarchy = null;
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		try {
			hierarchy = InterpretSpreadSheet.readSpreadSheet(input,catid);
			if(writeObjects) {
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

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(),
				getThreadLocalRequest().getSession());
		UserDTO user = util.getUserInfo();
		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		QueryPropertyValue filequery = new QueryPropertyValue("source", gcsinfo.getGSFilename());
		queryvalues.add(filequery);
		QuerySetupBase query = new QuerySetupBase(user.getName(),SpreadSheetInputInformation.class.getCanonicalName(), queryvalues);
		String sourceID = QueryBase.getDataSourceIdentification(input.getOwner());
		input.setSourceID(sourceID);
		DatabaseObjectHierarchy hierarchy = null;
		
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(MetaDataKeywords.observationsFromSpreadSheetFull);
		String obsid = InterpretData.createSuffix(catid, element);

		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			String originalid = null;
			if(result.getResults().size() > 0) {
				for(DatabaseObject obj: result.getResults()) {
					SpreadSheetInputInformation spreadinput = (SpreadSheetInputInformation) obj;
					String parentid = spreadinput.getParentLink();
					if(obsid.compareTo(parentid) == 0) {
						System.out.println("The new one is already: " + obsid);
						originalid = parentid;
					} else {
						System.out.println("Not the same as: " + obsid);
					}
				}
			} 
			if(originalid != null) {
				hierarchy = ExtractCatalogInformation.getCatalogObject(originalid, MetaDataKeywords.observationsFromSpreadSheetFull);
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
		return hierarchy;
	}
	
	public void deleteSpreadSheetTransaction(String filename) throws IOException {
		SpreadSheetInputInformation spreadsheet = (SpreadSheetInputInformation) 
				QueryBase.getFirstDatabaseObjectsFromSingleProperty(SpreadSheetInputInformation.class.getCanonicalName(), 
				"source", filename);
		TransactionInfo info = (TransactionInfo)
				QueryBase.getFirstDatabaseObjectsFromSingleProperty(SpreadSheetInputInformation.class.getCanonicalName(), 
				"identifier", spreadsheet.getIdentifier());
		DatabaseWriteBase.deleteTransactionInfo(info, MetaDataKeywords.spreadSheetInputInformation);
	}
	
	public DatabaseObjectHierarchy isolateFromMatrix(DataCatalogID catid,  
			DatabaseObjectHierarchy matrixhier, 
			SpreadSheetBlockIsolation blockisolate) throws IOException {
		DatabaseObjectHierarchy isolated = null;
		isolated = IsolateBlockFromMatrix.isolateFromMatrix(catid, matrixhier, blockisolate);
		WriteReadDatabaseObjects.deleteObject(isolated.getObject().getIdentifier(), MetaDataKeywords.observationsFromSpreadSheet);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(isolated);
		return isolated;
	}
	
	
}
