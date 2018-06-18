package info.esblurock.reaction.core.server.initialization;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

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
import info.esblurock.reaction.chemconnect.core.data.dataset.MeasurementParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.io.db.QueryBase;

public class UpdateDatabaseObjects {
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
			
			MeasurementParameterValue value = (MeasurementParameterValue) hierarchy.getObject();
			value.setUncertainty("new uncertainty");
			value.setValueAsString("20000000");

			measure = QueryBase.getDatabaseObjectFromIdentifier(MeasurementParameterValue.class.getCanonicalName(),
					"AdministrationCatalog-ThermocouplePositionInBurner");
			System.out.println("Read:   \n" + measure.toString());
} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);	
		
		List<MeasurementParameterValue> lst = ObjectifyService.ofy().load().type(MeasurementParameterValue.class).list();
		for(MeasurementParameterValue value : lst) {
			System.out.println("------------------------------------------------------");
			System.out.println(value.toString());
		}
		
		/*
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-ThermocouplePositionInBurner", 
				"dataset:MeasurementParameterValue");
				
		System.out.println("fillSetOfObservations   ExtractCatalogInformation.getCatalogObject\n" + 
				readhierarchy.toString());
				*/
	}

}
