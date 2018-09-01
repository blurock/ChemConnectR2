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
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
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
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.io.db.QueryBase;

public class LoginSetupTest {
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
			lstcat = QueryBase.getDatabaseObjects(DatasetCatalogHierarchy.class.getCanonicalName());
			for (DatabaseObject object : lstcat) {
				System.out.println(object.toString());
			}
			System.out.println("---------------------------------------------------------------");
			String catid = "Catalog-Administration-sethier";
			DatabaseObjectHierarchy ucathier = ExtractCatalogInformation.getDatabaseObjectHierarchy(catid);
			System.out.println(ucathier.toString());
			//System.out.println("---------------------------------------------------------------");
			//String orgid = "Catalog-Administration-org-sethier";
			//DatabaseObjectHierarchy ocathier = ExtractCatalogInformation.getCatalogObject(orgid, "dataset:DatasetCatalogHierarchy");
			//System.out.println(ocathier.toString());
			
			
			/*
			System.out.println("---------------------------------------------------------------");
			List<DatabaseObject> lstlnk = QueryBase.getDatabaseObjects(DataObjectLink.class.getCanonicalName());
			for (DatabaseObject object : lstlnk) {
				System.out.println(object.toString());
			}
			System.out.println("---------------------------------------------------------------");
			List<DatabaseObject> lstorg = QueryBase.getDatabaseObjects(Organization.class.getCanonicalName());
			for (DatabaseObject object : lstorg) {
				System.out.println(object.toString());
			}
			System.out.println("---------------------------------------------------------------");
			List<DatabaseObject> lstuser = QueryBase.getDatabaseObjects(IndividualInformation.class.getCanonicalName());
			for (DatabaseObject object : lstuser) {
				System.out.println(object.toString());
			}
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---------------------------------------------------------------");

	}

}
