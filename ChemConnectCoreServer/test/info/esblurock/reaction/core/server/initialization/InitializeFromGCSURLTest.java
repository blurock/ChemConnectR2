package info.esblurock.reaction.core.server.initialization;


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
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
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
import info.esblurock.reaction.ontology.QueryBase;

public class InitializeFromGCSURLTest {
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
		try {
			/*
			//String usercat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/ExperimentalSetDataCatagory/Guest-DatasetCatalogHierarchy-ExperimentalSetDataCatagory-ExampleData.yaml?_ga=2.50766240.-802839131.1514489990";
			//String addr = "https://storage.googleapis.com/combustion/Guest/CHEMCONNECT/ChemConnectIsolateBlockUntilBlankLine/Guest-CHEMCONNECT-ChemConnectIsolateBlockUntilBlankLine-IsolateIgnitionDelayTime.yaml";

			String usercat = "https://storage.googleapis.com/combustion/Guest/DatasetCatalogHierarchy/UserDataCatagory/Guest-DatasetCatalogHierarchy-UserDataCatagory-Guest.yaml?_ga=2.104771081.-802839131.1514489990";
			
			URL url = new URL(usercat);
			BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = breader.readLine()) != null) {
		      builder.append(line + "\n");
		    }
			System.out.println(builder.toString());
			DatabaseInterpretBase base = new DatabaseInterpretBase();
			base.readInitializationYamlFromURL(usercat);
			*/
			InitializeFromGCSURL.initialize();
			
			List<DatabaseObject> lstlink = (List<DatabaseObject>) QueryBase.getDatabaseObjects(DataObjectLink.class.getCanonicalName());
			for (DatabaseObject object : lstlink) {
				System.out.println(object.toString());
			}
			System.out.println("---------------------------------------------------------------");
			List<DatabaseObject> lstcat = (List<DatabaseObject>) QueryBase.getDatabaseObjects(DatasetCatalogHierarchy.class.getCanonicalName());
			for (DatabaseObject object : lstcat) {
				System.out.println(object.toString());
			}
			System.out.println("---------------------------------------------------------------");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
