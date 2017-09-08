package info.esblurock.reaction.core.server.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.core.server.initialization.Food;
import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.rdf.StoreObject;

public class ReadAndWriteRDF {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected Closeable session;

	@BeforeClass
	public static void setUpBeforeClass() {
		// Reset the Factory so that all translators work properly.
		ObjectifyService.setFactory(new ObjectifyFactory());
	}

	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
		helper.setUp();
	}

	@After
	public void tearDown() {
		AsyncCacheFilter.complete();
		this.session.close();
		this.helper.tearDown();
	}

	@Test
	public void test() {
		//RegisterContactData.register();
		//RegisterDescriptionData.register();
		//RegisterInitializationData.register();
		//RegisterRDFData.register();
		ObjectifyService.register(DatabaseObject.class);
		
		String fileS = "resources/contact/OrganizationInitialization.yaml";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		ArrayList<ListOfElementInformation> results;
		System.out.println("---------------");
		try {
			results = ReadYamlDataset.readYaml(in);
			System.out.println("=============================================: " + results.size());
			for (ListOfElementInformation info : results) {
				for (YamlDatasetInformation yaml : info) {
					System.out.println(yaml);
					DatabaseObject obj = yaml.getObject();
					StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), "1");
					WriteDatabaseObjectRDF.writeRDF(obj, store);
					System.out.println(store.toString());
					//store.finish();
				}
			}

			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		    assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
		    ds.put(new Entity("yam"));
		    ds.put(new Entity("yam"));
		    assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
			
		    ObjectifyService.register(Food.class);
		    
			System.out.println("-------------------");
			//DatabaseObject example = new DatabaseObject("");
			Food food = new Food("apples");
			System.out.println("-------------------");
			
			try {
			ObjectifyService.ofy().save().entity(food).now();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
			//DatabaseWriteBase.writeDatabaseObject(example);
			System.out.println("-------------------");
			List<KeywordRDF> lst = ObjectifyService.ofy().load().type(KeywordRDF.class).list();
			System.out.println("-------------------");
			System.out.println(lst.size());
			System.out.println("-------------------");
			for (KeywordRDF rdf : lst) {
				System.out.println(rdf.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

