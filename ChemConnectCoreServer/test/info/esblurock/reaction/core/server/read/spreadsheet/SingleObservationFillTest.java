package info.esblurock.reaction.core.server.read.spreadsheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationCorrespondenceSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.methodology.RegisterProtocol;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.RegisterObservationMatrixData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.db.spreadsheet.block.IsolateBlockFromMatrix;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.read.InterpretSpreadSheet;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class SingleObservationFillTest {
	protected Closeable session;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		ObjectifyService.setFactory(new ObjectifyFactory());
	}

	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
		RegisterRDFData.register();
		RegisterTransactionData.register();
		RegistrerDataset.register();
		RegisterUserLoginData.register();
		RegisterImageInformation.register();
		RegisterGCSClasses.register();
		RegisterObservationData.register();
		RegisterObservationMatrixData.register();
		RegisterObservationData.register();
		RegisterProtocol.register();
		ObjectifyService.register(BlobKeyCorrespondence.class);
		ObjectifyService.register(DatabaseObject.class);
		ObjectifyService.register(ChemConnectCompoundMultiple.class);
		ObjectifyService.register(ChemConnectDataStructure.class);
		ObjectifyService.register(ChemConnectCompoundDataStructure.class);
		ObjectifyService.register(ChemConnectCompoundMultiple.class);
		
		System.out.println("Classes Registered");
		this.helper.setUp();
	}

	@After
	public void tearDown() {
		AsyncCacheFilter.complete();
		this.session.close();
		this.helper.tearDown();
	}

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject("id", "Public", "Administration", "1");		

		SpreadSheetInputInformation info = setUpInfo(obj);
		System.out.println(info.toString("SpreadSheetInputInformation: "));		
		DatabaseObjectHierarchy obsblockhierarchy  = createIsolateFirstblock(obj);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(obsblockhierarchy);
		ObservationBlockFromSpreadSheet obsblock = (ObservationBlockFromSpreadSheet) obsblockhierarchy.getObject();

		System.out.println(obsblock.toString("Isolate: "));
		DatabaseObjectHierarchy blockhier = obsblockhierarchy.getSubObject(obsblock.getSpreadBlockIsolation());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(blockhier);
		SpreadSheetBlockIsolation isolate = (SpreadSheetBlockIsolation) blockhier.getObject();
		
		DataCatalogID catid = createCatID(obj);
		DatabaseObjectHierarchy spreadhier = readAMatrix(catid,info);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(spreadhier);
		System.out.println(spreadhier.toString("Read in Matrix: "));
		DatabaseObjectHierarchy firstblock = writeIsolatedBlock(catid,isolate,spreadhier);
		System.out.println(firstblock.toString("First Block"));
		
		DatabaseObjectHierarchy obsspechier = createObservationCorrespondenceSpecification(obj,catid);
		
		ObservationCorrespondenceSpecification obsspec = (ObservationCorrespondenceSpecification) obsspechier.getObject();
		String links = obsspec.getChemConnectObjectLink();
		DatabaseObjectHierarchy speclinks = obsspechier.getSubObject(links);
		ChemConnectCompoundMultiple multlinks = (ChemConnectCompoundMultiple) speclinks.getObject();
		multlinks.setNumberOfElements(1);
		DatabaseObjectHierarchy isolinkhier = InterpretData.DataObjectLink.createEmptyObject(speclinks.getObject());
		DataObjectLink isolink = (DataObjectLink) isolinkhier.getObject();
		isolink.setDataStructure(blockhier.getObject().getIdentifier());
		isolink.setLinkConcept(MetaDataKeywords.conceptLinkBlockIsolation);
		speclinks.addSubobject(isolinkhier);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(obsspechier);
		
		String specID = obsspechier.getObject().getIdentifier();
		
		String observationS = "dataset:RCMPublishedResults";
		String spreadsheetID = spreadhier.getObject().getIdentifier();
		
		try {
			DataCatalogID singlecatid = new DataCatalogID(catid);
			singlecatid.setDataCatalog("dataset:RCMPublishedResults");
			singlecatid.setSimpleCatalogName("IgnitionDelayTimes");
			DatabaseObject singleobj = new DatabaseObject(obj);
			singleobj.setIdentifier(singlecatid.getFullName());
			DatabaseObjectHierarchy singlehier = CreateDefaultObjectsFactory.fillSingleObservationDataset(singleobj, observationS, 
					specID, spreadsheetID, singlecatid);
			System.out.println(singlehier.toString("single: "));
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(singlehier);
			
			ArrayList<String> specificationIDs = new ArrayList<String>();
			DataCatalogID protocoldatid = new DataCatalogID(catid);
			protocoldatid.setDataCatalog("dataset:RapidCompressionMachineReportingProtocol");
			protocoldatid.setSimpleCatalogName("RCMResultsProtocol");
			String protocolS = "dataset:RapidCompressionMachineReportingProtocol";
			specificationIDs.add(obsspechier.getObject().getIdentifier());
			DatabaseObject protocolobj = new DatabaseObject(obj);
			protocolobj.setIdentifier(protocoldatid.getFullName());
			DatabaseObjectHierarchy protocol = CreateDefaultObjectsFactory.fillProtocolDefinition(protocolobj,
					specificationIDs,protocolS,
					"Testing the reporting Final Rapid Compression Results",
					protocoldatid);
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(protocol);
			System.out.println(protocol.toString("Protocol: "));
			String protocolID = protocol.getObject().getIdentifier();
			String title = "Test result of reporting actual values for Rapid Compression Machine";
			DataCatalogID fromprotocoldatid = new DataCatalogID(catid);
			fromprotocoldatid.setDataCatalog("dataset:RapidCompressionMachineReportingProtocol");
			fromprotocoldatid.setSimpleCatalogName("ResultObservationsFromRCM");
			ArrayList<String> observationIDs = new ArrayList<String>();
			observationIDs.add(singlehier.getObject().getIdentifier());
			DatabaseObject fromprotoobj = new DatabaseObject(obj);
			fromprotoobj.setIdentifier(fromprotocoldatid.getFullName());
			DatabaseObjectHierarchy fromprotocol = CreateDefaultObjectsFactory.fillObservationSetFromProtocol(fromprotoobj, observationIDs, 
					protocolS, protocolID, title, fromprotocoldatid);
			System.out.println(fromprotocol.toString("From Protocol: "));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DatabaseObjectHierarchy createObservationCorrespondenceSpecification(DatabaseObject obj, DataCatalogID datid) {
		String parameter = "dataset:RCMPublishedResults";
		String oneline = "The published results of a rapid compression machine";
		DataCatalogID specdatid = new DataCatalogID(datid);
		specdatid.setDataCatalog(parameter);
		specdatid.setSimpleCatalogName("RCMResults");
		DatabaseObject specobj = new DatabaseObject(obj);
		specobj.setIdentifier(specdatid.getFullName());
		DatabaseObjectHierarchy corrshierarchy = CreateDefaultObjectsFactory.fillSetOfObservations(specobj, parameter, oneline, specdatid);
		ObservationCorrespondenceSpecification corrspec = (ObservationCorrespondenceSpecification) corrshierarchy.getObject();
		DatabaseObjectHierarchy specsethier = corrshierarchy.getSubObject(corrspec.getMatrixSpecificationCorrespondenceSet());
		MatrixSpecificationCorrespondenceSet specset = (MatrixSpecificationCorrespondenceSet) specsethier.getObject();
		
		DatabaseObjectHierarchy multiplespec = specsethier.getSubObject(specset.getMatrixSpecificationCorrespondence());
		
		String col1 = "Initial Temperature (K)";
		String col2 = "Initial Pressure (bar)";
		String col3 = "Compression Time (ms)";
		String col4 = "Compressed Temperature (K)";
		String col5 = "1000/Tc (1/K)";
		String col6 = "Compressed Pressure (bar)";
		String col7 = "Ignition Delay (msec)";
		String col8 = "Ignition Delay Unc (msec)";
		
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col4);
		columns.add(col5);
		columns.add(col6);
		columns.add(col7);
		columns.add(col8);
		CreateDefaultObjectsFactory.fillMatrixSpecificationCorrespondence(specsethier,columns);
		String parm1 = "dataset:ExperimentalTemperature";
		String parm2 = "dataset:ExperimentalPressure";
		String parm3 = "dataset:RCMCompressionTime";
		String parm4 = "dataset:RCMCompressionTemperature";
		String parm5 = "dataset:InverseTemperatureParameter";
		String parm6 = "dataset:RCMCompressionPressure";
		String parm7 = "dataset:IgnitionDelayTime";
		String parm8 = "dataset:IgnitionDelayTime";
		HashMap<String,String> colmap = new HashMap<String,String>();
		colmap.put(col1, parm1);
		colmap.put(col2, parm2);
		colmap.put(col3, parm3);
		colmap.put(col4, parm4);
		colmap.put(col5, parm5);
		colmap.put(col6, parm6);
		colmap.put(col7, parm7);
		colmap.put(col8, parm8);
		for(DatabaseObjectHierarchy corrhier : multiplespec.getSubobjects()) {
			MatrixSpecificationCorrespondence corr = (MatrixSpecificationCorrespondence) corrhier.getObject();
			String matcol = colmap.get(corr.getMatrixColumn());
			corr.setSpecificationLabel(matcol);
			if(corr.getMatrixColumn().compareTo(col8) == 0) {
				corr.setIncludesUncertaintyParameter(true);
			}
		}
		return corrshierarchy;
	}
	
	private DataCatalogID createCatID(DatabaseObject obj) {
		DatabaseObjectHierarchy catidhier = InterpretData.DataCatalogID.createEmptyObject(obj);
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setDataCatalog("CSV");
		catid.setCatalogBaseName("NewMatrix");
		System.out.println("DataCatalogID:\n" + catid.toString());
		return catid;
	}
	
	private SpreadSheetInputInformation setUpInfo(DatabaseObject obj) {
		DatabaseObject spreadobj = new DatabaseObject(obj);
		spreadobj.setIdentifier("SpreadSheetFromString");
		DatabaseObjectHierarchy hierarchy = InterpretData.SpreadSheetInputInformation.createEmptyObject(spreadobj);
		SpreadSheetInputInformation info = (SpreadSheetInputInformation) hierarchy.getObject();
		info.setSourceType(SpreadSheetInputInformation.STRINGSOURCE);
		String source = "Initial Temperature (K),Initial Pressure (bar),Compression Time (ms),Compressed Temperature (K),1000/Tc (1/K),Compressed Pressure (bar),Ignition Delay (msec),Ignition Delay Unc (msec)\n" + 
				"413,0.8043,28.7,816,1.2262,14.95,112.55,5.35\n" + 
				"413,0.7689,30.4,824,1.2129,15.05,73.52,3.51\n" + 
				"413,0.7240,31.25,835,1.1974,14.97,49.79,3.07\n" + 
				"413,0.6810,32.7,846,1.1816,14.98,34.60,1.21\n" + 
				"413,0.6427,33.9,855,1.1692,14.95,24.49,1.53\n" + 
				",,,,,,,\n" + 
				"phi = 1.0,O2:N2 = 1:3.76 (Air),Mole Fractions:,Fuel,0.0338,,,\n" + 
				",,,O2,0.2030,,,\n" + 
				",,,N2,0.7632,,,\n" + 
				",,,,,,,";
		info.setSource(source);
		info.setType(SpreadSheetInputInformation.CSV);		
		return info;
	}
	private DatabaseObjectHierarchy readAMatrix(DataCatalogID catid, SpreadSheetInputInformation info) {		
		DatabaseObjectHierarchy spreadhier = null;
		try {
			spreadhier = InterpretSpreadSheet.readSpreadSheet(info, catid);
			System.out.println(spreadhier.toString());
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return spreadhier;
	}
	private DatabaseObjectHierarchy createIsolateFirstblock(DatabaseObject obj) {
		DatabaseObject blockobj = new DatabaseObject(obj);
		blockobj.setIdentifier("IsolateFirstBlockSpecification");
		DatabaseObjectHierarchy obsblockhierarchy = InterpretData.ObservationBlockFromSpreadSheet.createEmptyObject(blockobj);
		ObservationBlockFromSpreadSheet obsblock = (ObservationBlockFromSpreadSheet) obsblockhierarchy.getObject();
		DatabaseObjectHierarchy blockhier = obsblockhierarchy.getSubObject(obsblock.getSpreadBlockIsolation());
		SpreadSheetBlockIsolation isolate = (SpreadSheetBlockIsolation) blockhier.getObject();
		isolate.setStartColumnType(StandardDatasetMetaData.matrixBlockColumnBeginLeft);
		isolate.setEndColumnType(StandardDatasetMetaData.matrixBlockColumnEndMaximum);
		isolate.setStartRowType(StandardDatasetMetaData.beginMatrixTopOfSpreadSheet);
		isolate.setEndRowType(StandardDatasetMetaData.matrixBlockEndAtBlankLine);
		isolate.setTitleIncluded(StandardDatasetMetaData.matrixBlockTitleFirstLine);
		return obsblockhierarchy;
	}
	/*
	private SpreadSheetBlockIsolation createIsolate2ndblock(DatabaseObject obj) {
		DatabaseObject isolateobj = new DatabaseObject(obj);
		isolateobj.setIdentifier("FirstBlockIsolated");
		DatabaseObjectHierarchy blockhier = InterpretData.SpreadSheetBlockIsolation.createEmptyObject(isolateobj);
		SpreadSheetBlockIsolation isolate = (SpreadSheetBlockIsolation) blockhier.getObject();
		isolate.setStartColumnType(StandardDatasetMetaData.matrixBlockColumnBeginAtPosition);
		isolate.setStartColumnInfo("3");
		isolate.setEndColumnType(StandardDatasetMetaData.matrixBlockColumnEndNumberOfColumns);
		isolate.setEndColumnInfo("2");
		isolate.setStartRowType(StandardDatasetMetaData.beginMatrixAtStartsWithSpecifiedIdentifier);
		isolate.setStartRowInfo("phi =");
		isolate.setEndRowType(StandardDatasetMetaData.matrixBlockEndAtBlankLine);
		isolate.setTitleIncluded(Boolean.FALSE.toString());
		return isolate;
	}
	*/
	private DatabaseObjectHierarchy writeIsolatedBlock(DataCatalogID catid, SpreadSheetBlockIsolation isolate, DatabaseObjectHierarchy spreadhier) {
		DatabaseObjectHierarchy newhier1 = null;
		DataCatalogID isocatid = new DataCatalogID(catid);
		
		try {
			System.out.println("Full until blank line======================================================");
			isocatid.setSimpleCatalogName("UntilBlankLine");
			isocatid.setIdentifier(isocatid.getFullName());
			newhier1 = IsolateBlockFromMatrix.isolateFromMatrix(isocatid, spreadhier, isolate);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return newhier1;
	}
}
