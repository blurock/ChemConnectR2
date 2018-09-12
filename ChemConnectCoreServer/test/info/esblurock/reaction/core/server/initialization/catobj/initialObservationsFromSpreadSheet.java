package info.esblurock.reaction.core.server.initialization.catobj;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

//import static org.junit.Assert.*;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlWriter;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultipleInfo;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.methodology.RegisterMethodology;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;

public class initialObservationsFromSpreadSheet {
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
		RegisterMethodology.register();
		ObjectifyService.register(BlobKeyCorrespondence.class);
		ObjectifyService.register(DatabaseObject.class);
		ObjectifyService.register(ChemConnectCompoundMultiple.class);
		ObjectifyService.register(ChemConnectDataStructure.class);
		ObjectifyService.register(ChemConnectCompoundDataStructure.class);
		ObjectifyService.register(ChemConnectCompoundMultiple.class);
		ObjectifyService.register(ChemConnectCompoundMultipleInfo.class);
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
		DatabaseObject obj = new DatabaseObject("Base-First-Second-catalogname-simple", "Public", "Administration",
				"0");
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj, "Spreadsheet");
		String catalogBaseName = "Base-First-Second";
		String dataCatalog = "catalogname";
		String simpleCatalogName = "simple";
		ArrayList<String> path = new ArrayList<String>();
		path.add("Base");
		path.add("First");
		path.add("Second");
		DataCatalogID catid = new DataCatalogID(structure, catalogBaseName, dataCatalog, simpleCatalogName, path);
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillObservationsFromSpreadSheet(obj, catid, 2,
				4);
		System.out.println(hierarchy);
		try {
			Map<String, Object> map1 = ReadWriteYamlDatabaseObjectHierarchy.yamlDatabaseObjectHierarchy(hierarchy);
			StringWriter wS = new StringWriter(1000000);
			YamlWriter writer = new YamlWriter(wS);
			writer.write(map1);
			writer.close();
			System.out.println("--------------------------------------------");
			System.out.println(wS.toString());
			System.out.println("--------------------------------------------");
			DatabaseObjectHierarchy hierarchy2 = ReadWriteYamlDatabaseObjectHierarchy
					.readYamlDatabaseObjectHierarchy(obj, map1, "100");
			System.out.println("--------------------------------------------");
			System.out.println(hierarchy2.toString());
			System.out.println("--------------------------------------------");

			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);
			System.out.println("Hierarchy written");

			System.out.println("----------------------------------------------------------------");
			// String uid = DatasetCatalogHierarchy.createFullCatalogName("Catalog",
			// username);
			String uid = "Base-First-Second-catalogname-simple-spreadsheetobs";
			DatabaseObjectHierarchy methhier = ExtractCatalogInformation.getCatalogObject(uid,
					"dataset:ObservationsFromSpreadSheet");
			System.out.println("----------------------------------------------------------------");
			System.out.println(methhier.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
