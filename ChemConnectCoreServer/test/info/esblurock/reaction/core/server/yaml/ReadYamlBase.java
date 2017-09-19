package info.esblurock.reaction.core.server.yaml;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.dataset.RegistrerDataset;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.rdf.StoreObject;

public class ReadYamlBase {
	public static void test(InputStream in) {		
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
		RegisterRDFData.register();
		RegistrerDataset.register();
		ObjectifyService.register(DatabaseObject.class);
		
		String sourceID = "1";
		
		ArrayList<ListOfElementInformation> results;
		System.out.println("---------------");
		try {
			results = ReadYamlDataset.readYaml(in,sourceID);
			System.out.println("=============================================: " + results.size());
			for (ListOfElementInformation info : results) {
				for (YamlDatasetInformation yaml : info) {
					System.out.println(yaml);
					DatabaseObject obj = yaml.getObject();
					StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), "1");
					WriteDatabaseObjectRDF.writeRDF(obj, store);
					System.out.println(store.toString());
					store.finish();
				}
			}

			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		    assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
		    ds.put(new Entity("yam"));
		    ds.put(new Entity("yam"));
		    assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
			
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
