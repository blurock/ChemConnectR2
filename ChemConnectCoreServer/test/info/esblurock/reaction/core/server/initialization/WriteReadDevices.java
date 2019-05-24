package info.esblurock.reaction.core.server.initialization;

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
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
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
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;

public class WriteReadDevices {
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
		String user = "Administration";
		//String classname = SubSystemDescription.class.getCanonicalName();
		String basecatalog = "Catalog-Base";
		String catalog = "Catalog";
		
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog-HeatFluxBurner",
				user,user,"1" );
		
		String devicename = "dataset:HeatFluxBurner";
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		ArrayList<String> path = new ArrayList<String>();
		path.add("First");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple",path);
		
		DatabaseObjectHierarchy devicehier = CreateDefaultObjectsFactory.fillSubSystemDescription(obj,
				devicename,name);

		System.out.println("fillSubSystemDescription\n" + devicehier.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(devicehier);	
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject("AdministrationCatalog-HeatFluxBurner", "dataset:SubSystemDescription");
		System.out.println("fillSubSystemDescription\n" + readhierarchy.toString());
		
		
		System.out.println("WriteReadDatabaseObjects.getIDsFromDatabaseObjectHierarchy:");
		try {
			ArrayList<DatabaseObjectHierarchy> hiers = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(user, 
					"dataset:DataCatalogID");
			for(DatabaseObjectHierarchy hier : hiers) {
				System.out.println(hier.toString());
			}
			
			HierarchyNode node = WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogID(user,basecatalog,catalog);
			System.out.println("WriteReadDatabaseObjects.getIDsFromDatabaseObjectHierarchy:\n " + node.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
