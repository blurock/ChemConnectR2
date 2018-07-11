package info.esblurock.reaction.core.server.initialization;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

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
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.observations.RegisterObservationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ReadFromLinkConcept {

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
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog-HeatFluxBurner",
				"Public","Administration","1" );
		
		PurposeConceptPair pair = new PurposeConceptPair();
		String devicename = "dataset:HeatFluxBurner";
		ConceptParsing.fillInPurposeConceptPair(devicename, pair);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple");

		DatabaseObjectHierarchy devicehier = CreateDefaultObjectsFactory.fillSubSystemDescription(obj,
				devicename,pair.getPurpose(),pair.getConcept(),name);

		System.out.println("fillSubSystemDescription\n" + devicehier.toString());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(devicehier);	
	
		String concept1 = "dataset:ConceptLinkSubsystems";
		String classType = ConceptParsing.findObjectTypeFromLinkConcept(concept1);
		System.out.println(classType);
		
		try {
			Set<String> ids = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects("Administration",classType);
			System.out.println("The IDs with concept: ");
			HierarchyNode topnode = new HierarchyNode(concept1);
			for(String id : ids) {
				System.out.println(id);
				StringTokenizer tok = new StringTokenizer(id,"-");
				ArrayList<String> path = new ArrayList<String>();
				String last = null;
				while(tok.hasMoreTokens()) {
					last = tok.nextToken();
					path.add(last);
				}
				ParseUtilities.fillInHierarchy(topnode, path, id);
			}
			System.out.println(topnode.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	
	
	}

}
