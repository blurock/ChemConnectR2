package info.esblurock.reaction.core.server.rdf;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.io.rdf.DatabaseWriteBase;

public class WriteRDFTest {
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
		/*
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
		RegisterRDFData.register();
		RegisterTransactionData.register();
		RegistrerDataset.register();
		RegisterUserLoginData.register();
*/
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
		ObjectifyService.register(KeywordRDF.class);
		ObjectifyService.register(DatabaseObject.class);
		
		String sourceID = "1";
		String keyword = "key";
		String access = "Administration";
		String owner = "blurock";
		String predicate = "predicate";
		String object = "object";
		String typeS = "type";
		//DatabaseObject obj = new DatabaseObject(keyword,access,owner,sourceID);
		KeywordRDF objectrdf = new KeywordRDF(keyword,access,owner,sourceID,predicate,object,typeS);
		//ObjectifyService.ofy().save().entity(objectrdf).now();
		DatabaseWriteBase.writeDatabaseObject(objectrdf);
		
		System.out.println("-------------------");
		List<KeywordRDF> lst = ObjectifyService.ofy().load().type(KeywordRDF.class).list();
		System.out.println("-------------------");
		System.out.println(lst.size());
		System.out.println("-------------------");
		for (KeywordRDF rdf : lst) {
			System.out.println(rdf.toString());
		}
		
	}

}
