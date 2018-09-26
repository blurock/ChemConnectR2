package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;

public class ReadInitialization {
	protected Closeable session;
	//private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		ObjectifyService.setFactory(new ObjectifyFactory());
	}
	
	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		RegisterInitializationData.register();
		
		RegisterContactData.register();
		RegisterDescriptionData.register();
		
		RegisterRDFData.register();
		RegisterTransactionData.register();
		RegistrerDataset.register();
		RegisterUserLoginData.register();

		//helper.setUp();
	}
	@After
	public void tearDown() {
		AsyncCacheFilter.complete();
		this.session.close();
		//this.helper.tearDown();
	}

	@Test
	public void test() {
		//String userInitialization = "resources/contact/UserInitialization.yaml";
		//String userInitialization = "resources/contact/OrganizationInitialization.yaml";
		String userInitialization = "resources/contact/AccountInitialization.yaml";
		DatabaseInterpretBase base = new DatabaseInterpretBase();
		System.out.println("Initializing users: " + userInitialization);
		try {
			base.readInitializationFile(userInitialization, "yaml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
