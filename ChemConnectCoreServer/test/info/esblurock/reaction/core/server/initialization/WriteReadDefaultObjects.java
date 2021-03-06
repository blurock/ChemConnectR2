package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

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
import info.esblurock.reaction.chemconnect.core.data.register.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterDataset;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;

public class WriteReadDefaultObjects {
	protected Closeable session;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		//ObjectifyService.setFactory(new ObjectifyFactory());
	}
	
	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
		RegisterRDFData.register();
		RegisterTransactionData.register();
		RegisterDataset.register();
		RegisterUserLoginData.register();
		RegisterImageInformation.register();
		RegisterGCSClasses.register();
		RegisterObservationData.register();
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
		
		/*
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillParameterValueAndSpecification(obj,
				"dataset:ThermocouplePositionInBurner",
				false);
		System.out.println("fillSetOfObservations\n" + hierarchy.toString());
		
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		
		try {
			DatabaseObject measure = QueryBase.getDatabaseObjectFromIdentifier(MeasurementParameterValue.class.getCanonicalName(),
					"AdministrationCatalog-ThermocouplePositionInBurner");
			System.out.println("Read:   \n" + measure.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-ThermocouplePositionInBurner", 
				"dataset:MeasurementParameterValue");
		System.out.println("fillSetOfObservations   ExtractCatalogInformation.getCatalogObject\n" + 
				readhierarchy.toString());
				*/
		/*
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillSetOfObservations(obj,"dataset:BurnerPlateObservations",
				"Set of burner plate observations","dataset:HeatFluxBurnerObservation","dataset:LaminarFlame");
		System.out.println("fillSetOfObservations\n" + hierarchy.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-setofvalues", "dataset:ObservationCorrespondenceSpecification");
		System.out.println(readhierarchy.toString());
		*/
		/*
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		NameOfPerson person = new NameOfPerson(obj,"Prof.", "Looney","Tunes");
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj,"dataset:",person);
		System.out.println("fillMinimalPersonDescription\n" + hierarchy.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-usrinfo", "dataset:DatabasePerson");
		System.out.println(readhierarchy.toString());
*/
		/*
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		String company = "Whatsamadder U";
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillOrganization(obj,company);
		System.out.println("fillOrganization\n" + hierarchy.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-org", "dataset:Organization");
		System.out.println(readhierarchy.toString());
*/
/*
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		String devicename = "dataset:HeatFluxBurner";
		String purpose = "dataset:FlameVelocityMeasurements";
		String concept = "dataset:FlameStudies";
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillSubSystemDescription(obj, devicename, purpose, concept);
		System.out.println("fillSubSystemDescription\n" + hierarchy.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-subsys", "dataset:SubSystemDescription");
		System.out.println(readhierarchy.toString());
*/
	}

}
