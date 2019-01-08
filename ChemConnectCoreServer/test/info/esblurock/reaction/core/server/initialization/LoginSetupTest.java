package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
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
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;
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
		String username = "GUEST";
		String access = "GUEST";
		String userrole = MetaDataKeywords.accessTypeStandardUser;
		String owner = "GUEST";
		String orgname = "CHEMCONNECT";
		String title = "CHEMCONNECT";
		String sourceID = "0";
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, access, owner,
				orgname, title, sourceID);

		List<DatabaseObject> lstcat;
		try {
			lstcat = QueryBase.getDatabaseObjects(DatasetCatalogHierarchy.class.getCanonicalName());
			for (DatabaseObject object : lstcat) {
				System.out.println(object.getIdentifier().toString());
			}
			System.out.println("---------------------------------------------------------------");
			String id = "GUEST-UserDataCatagory-GUEST-sethier";
			DatabaseObjectHierarchy cat = ExtractCatalogInformation.getDatabaseObjectHierarchy(id);
			System.out.println(cat.toString("Catalog: "));
			
			String yaml = ReadWriteYamlDatabaseObjectHierarchy.yamlStringFromDatabaseObjectHierarchy(cat);
			System.out.println(yaml);
			
			Map<String, Object> readmap = ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap(yaml);
			MapUtils	.debugPrint(System.out, "Map: ", readmap);
			DatabaseObjectHierarchy newhier = ReadWriteYamlDatabaseObjectHierarchy.readYamlDatabaseObjectHierarchy(cat.getObject(), readmap, "999");
			System.out.println(newhier.toString("fromYaml: "));
			lstcat = QueryBase.getDatabaseObjects(DatasetCatalogHierarchy.class.getCanonicalName());
			for (DatabaseObject object : lstcat) {
				System.out.println(object.getIdentifier().toString());
			}
			/*
			System.out.println("---------------------------------------------------------------");
			
			String catid = CreateDefaultObjectsFactory.userCatalogHierarchyID(username);
			DatabaseObjectHierarchy ucathier = ExtractCatalogInformation.getDatabaseObjectHierarchy(catid);
			System.out.println(ucathier.toString());

			System.out.println("---------------------------------------------------------------");
			DatabaseObjectHierarchy objhier = ExtractLinkObjectFromStructure.extract(ucathier, "dataset:ConceptLinkUserInformation");
			System.out.println(objhier.toString("LinkedUser: "));
			//System.out.println("---------------------------------------------------------------");
			//String orgid = "Catalog-Administration-org-sethier";
			//DatabaseObjectHierarchy ocathier = ExtractCatalogInformation.getCatalogObject(orgid, "dataset:DatasetCatalogHierarchy");
			//System.out.println(ocathier.toString());
			*/
			
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
