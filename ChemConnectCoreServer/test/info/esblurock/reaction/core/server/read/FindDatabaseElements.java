package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

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
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class FindDatabaseElements {
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
		String username = "Administration";
		String name = DatasetOntologyParsing.getChemConnectDirectTypeHierarchy("dataset:Organization");
		System.out.println("dataset:Organization    \t" + name);
		InterpretData interpret = InterpretData.valueOf(name);
		System.out.println(interpret.canonicalClassName());
		/*
		String sourceID = "1";
		
		//String access = "Administration";
		String owner = "Administration";
		String title = "Blurock Consulting AB";
		String userrole = MetaDataKeywords.accessTypeAdministrator;
		
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, username, owner,
				"BlurockConsultingAB-admin", title, sourceID);
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories("SecondUser", MetaDataKeywords.accessTypeStandardUser, "OtherUser", "OtherUser",
				"BlurockConsultingAB-other", title, sourceID);
		CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories("ThirdUser", MetaDataKeywords.accessTypeQuery, "Public", "OtherUser",
				"BlurockConsultingAB-public", title, sourceID);
		
		*/
		
		try {
			
			ArrayList<DatabaseObjectHierarchy> objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(username,"dataset:Organization");

			System.out.println("______________________________________________________________________");
			
			for(DatabaseObjectHierarchy hierarchy : objects) {
				System.out.println(hierarchy.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
