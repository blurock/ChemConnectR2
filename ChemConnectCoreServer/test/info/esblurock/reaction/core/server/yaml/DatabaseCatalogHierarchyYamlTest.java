package info.esblurock.reaction.core.server.yaml;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
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
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;

public class DatabaseCatalogHierarchyYamlTest {
	protected Closeable session;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		//ObjectifyService.setFactory(new ObjectifyFactory());
		ObjectifyService.init();
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
		String sourceID = "1";
		//String username = "Administration";
		//String access = "Administration";
		String owner = "Administration";
		//String orgname = "BlurockConsultingAB";
		//String title = "Blurock Consulting AB";

		//String userrole = MetaDataKeywords.accessTypeStandardUser;
		/*
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, access, owner,
				orgname, title, sourceID);
*/
		System.out.println("---------------------------------------------------------------");
		String id = "Administration-UserDataCatagory-Administration-sethier";
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(id, MetaDataKeywords.datasetCatalogHierarchy);
		System.out.println("---------------------------------------------------------------");
		System.out.println(hierarchy.toString("Single Hierarchy: "));
		System.out.println("---------------------------------------------------------------");
		try {
			DatabaseObject obj = hierarchy.getObject();
			id = obj.getIdentifier();
			String simpleName = "NewCatagory";
			String onelinedescription = "New added catagory";
			String catagorytype = MetaDataKeywords.linkSubCatalog;
			DatabaseObjectHierarchy newhierarchy = ExtractCatalogInformation.createNewCatalogHierarchy(obj, 
					simpleName,
					id, onelinedescription,sourceID, catagorytype);
			String dataType = "dataset:DataObjectLink";
			System.out.println("======================================================================");
			System.out.println(dataType + " ---------------------------------------------------------------------------");
			ArrayList<DatabaseObjectHierarchy> objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(owner,dataType);
			for(DatabaseObjectHierarchy hier : objects) {
				System.out.println(hier.toString());
			}
			System.out.println(dataType + " ---------------------------------------------------------------------------");
			System.out.println("======================================================================");

			System.out.println(newhierarchy.toString("NewHierarchy: "));
			DatabaseObjectHierarchy collected= 
					ReadWriteYamlDatabaseObjectHierarchy.collectDatasetCatalogHierarchy(hierarchy.getObject().getIdentifier());
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(collected.toString("collected: "));
			System.out.println("-----------------------------------------------------------------------------------");
			String yaml = ReadWriteYamlDatabaseObjectHierarchy.yamlStringFromDatabaseObjectHierarchy(collected);
			System.out.println(hierarchy.toString("writeDatasetCatalogHierarchyAsYaml: "));
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(yaml);
			System.out.println("-----------------------------------------------------------------------------------");
			DatabaseObject top = null;
			Map<String, Object> mapping = ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap(yaml);
			DatabaseObjectHierarchy subhier = ReadWriteYamlDatabaseObjectHierarchy.readYamlDatabaseObjectHierarchy(top, mapping, sourceID);
			System.out.println(subhier.toString("fromYaml: "));
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
