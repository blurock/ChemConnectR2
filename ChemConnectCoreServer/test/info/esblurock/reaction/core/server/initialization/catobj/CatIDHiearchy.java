package info.esblurock.reaction.core.server.initialization.catobj;

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

public class CatIDHiearchy {
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
		String sourceID = "1";
		String username = "Administration";
		String access = "Administration";
		String owner = "Administration";
		String orgname = "BlurockConsultingAB";
		String title = "Blurock Consulting AB";
		String userrole = MetaDataKeywords.accessTypeStandardUser;
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, access, owner,
				orgname, title, sourceID);
		
		System.out.println("---------------------------------------------------------------");
		List<DatabaseObject> lstcat;
		try {
			lstcat = QueryBase.getDatabaseObjects(DataCatalogID.class.getCanonicalName());
			for (DatabaseObject object : lstcat) {
				System.out.println(object.toString());
			}
			} catch(Exception ex ) {
				
			}
			System.out.println("---------------------------------------------------------------");

			try {
				HierarchyNode topnode = WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogIDAndClassType(username, 
						"Administration-Organization","dataset:Organization");
				System.out.println("Node tree");
				System.out.println(topnode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			
			*/
	}
}
