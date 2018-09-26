package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import java.io.IOException;

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
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class AddExtraCatagoryTest {
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
		String sourceID   = "1";
		String username   = "Administration";
		String access     = "Administration";
		String owner      = "Administration";
		String orgname    = "BlurockConsultingAB";
		String title      = "Blurock Consulting AB";
		String userrole   = MetaDataKeywords.accessTypeStandardUser;
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, access, owner,
				orgname, title, sourceID);
		
		
		String uid = "Catalog-Administration-sethier";
		try {
			DatabaseObjectHierarchy cathier = ExtractCatalogInformation.getDatabaseObjectHierarchy(uid);
			System.out.println("----------------------------------------------------------------");
			System.out.println(cathier.toString());
			System.out.println("----------------------------------------------------------------");
			
			String id = "NewPublishedCatagory";
			String onelinedescription = "This is a new catagory for published results";
			String newsourceID = "100";
			String catagorytype = StandardDatasetMetaData.conceptPublishedResultsCatagory;
			
			DatabaseObjectHierarchy orghier = cathier.getSubObject("Catalog-Administration-sethier-BlurockConsultingAB-sethier");
			
			DatabaseObjectHierarchy newhier = ExtractCatalogInformation.createNewCatalogHierarchy(orghier.getObject(), 
					id, onelinedescription, newsourceID,catagorytype);
			System.out.println("----------------------------------------------------------------");
			System.out.println(newhier.toString());
			System.out.println("----------------------------------------------------------------");

			DatabaseObjectHierarchy cathier2 = ExtractCatalogInformation.getDatabaseObjectHierarchy(uid);
			System.out.println("----------------------------------------------------------------");
			System.out.println(cathier2.toString());
			System.out.println("----------------------------------------------------------------");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

}
