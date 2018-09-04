package info.esblurock.reaction.core.server.initialization.catobj;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.methodology.RegisterMethodology;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.RegisterObservationMatrixData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;

public class InitialSetOfObservations {
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
		RegisterObservationMatrixData.register();
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
		String setobs = "dataset:BurnerPlateObservations";
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		ArrayList<String> path = new ArrayList<String>();
		path.add("First");
		DataCatalogID datid = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple",path);
		String oneline = "A set of Heat Flux Burner Observations";
		DatabaseObjectHierarchy hierarchy1 = CreateDefaultObjectsFactory.fillSetOfObservations(obj, setobs, oneline, datid);
		String descr = "The Heat Flux method is one of the most recent "
				+ "experimental methods, which allow measuring laminar burning "
				+ "velocity. In order to improve the accuracy of the measurements "
				+ "and to determine possible systematic uncertainties, "
				+ "several sets of experiments have been carried out and their "
				+ "results have been compared.\n In this study the Heat Flux "
				+ "method has been applied to measure laminar burning velocities "
				+ "of methane, methanol and ethanol mixtures with air. "
				+ "The measurements have been performed by four different "
				+ "laboratories from Eindhoven University of Technology, "
				+ "Lund University, OWI Oel-Waerme Institut GmbH and "
				+ "TU Bergakademie Freiberg.";
		SetOfObservationValues m = (SetOfObservationValues) hierarchy1.getObject();
		DatabaseObjectHierarchy dhier = hierarchy1.getSubObject(m.getDescriptionDataData());
		DescriptionDataData dstruct = (DescriptionDataData) dhier.getObject();
		dstruct.setDescriptionAbstract(descr);
		
		try {
			System.out.println(hierarchy1.toString());
			
			Map<String,Object> map1 = ReadWriteYamlDatabaseObjectHierarchy.yamlDatabaseObjectHierarchy(hierarchy1);
			StringWriter wS = new StringWriter(1000000);
			YamlWriter writer = new YamlWriter(wS);
			writer.write(map1);
			writer.close();
			System.out.println("--------------------------------------------");
			System.out.println(wS.toString());
			System.out.println("--------------------------------------------");
			DatabaseObjectHierarchy hierarchy2 = ReadWriteYamlDatabaseObjectHierarchy.readYamlDatabaseObjectHierarchy(obj, map1, "100");
			System.out.println("--------------------------------------------");
			System.out.println(hierarchy2.toString());
			System.out.println("--------------------------------------------");
			
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy1);	
			System.out.println("Hierarchy written");
			Set<String> ids = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects("Administration",
					"dataset:SetOfObservationValues");
			System.out.println("IDs: " + ids);
			Set<String> ids1 = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects("Administration",
					"dataset:ObservationValueRowTitle");
			System.out.println("IDs: " + ids1);
			
			System.out.println("----------------------------------------------------------------");
			//String uid = DatasetCatalogHierarchy.createFullCatalogName("Catalog", username);
			String uid = "AdministrationCatalog-setofvalues";
			DatabaseObjectHierarchy methhier = ExtractCatalogInformation.getCatalogObject(uid, "dataset:SetOfObservationValues");
			System.out.println("----------------------------------------------------------------");
			System.out.println(methhier.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
