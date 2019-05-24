package info.esblurock.reaction.core.server.initialization.catobj;

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
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterObservationMatrixData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterProtocol;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.register.RegisterDataset;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;

public class FindTopObjectTest {
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
		RegisterProtocol.register();
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
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		ArrayList<String> path = new ArrayList<String>();
		path.add("First");
		DataCatalogID datid = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple",path);
		String userClassification = MetaDataKeywords.accessTypeStandardUser;
		String title = "Dr.";
		String givenName = "Homer";
		String familyName = "Simpson";
		String username = "simpson";
		ChemConnectCompoundDataStructure pstructure = new ChemConnectCompoundDataStructure(obj,null);
		NameOfPerson person = new NameOfPerson(pstructure, title, givenName, familyName);
		DatabaseObjectHierarchy hierarchy = 
				CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj, username,userClassification, person, datid);
		
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);
		
		IndividualInformation individual = (IndividualInformation) hierarchy.getObject();
		System.out.println(individual.toString("IndividualInformation: "));
		DatabaseObjectHierarchy indhier = hierarchy.getSubObject(individual.getPersonalDescriptionID());
		PersonalDescription personal = (PersonalDescription) indhier.getObject();
		System.out.println(personal.toString("PersonalDescription: "));
		DatabaseObjectHierarchy p = indhier.getSubObject(personal.getNameOfPersonIdentifier());
		NameOfPerson name = (NameOfPerson) p.getObject();
		System.out.println(name.toString("NameOfPerson: "));
		try {
			DatabaseObjectHierarchy hier = ExtractCatalogInformation.getTopCatalogObject(name.getIdentifier(), "dataset:NameOfPerson");
			System.out.println(hier.toString("FromName: "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
