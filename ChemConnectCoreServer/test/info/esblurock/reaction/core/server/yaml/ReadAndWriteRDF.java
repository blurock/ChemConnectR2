package info.esblurock.reaction.core.server.yaml;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.core.server.initialization.yaml.ListOfElementInformation;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;
import info.esblurock.reaction.core.server.initialization.yaml.YamlDatasetInformation;
import info.esblurock.reaction.io.rdf.StoreObject;

public class ReadAndWriteRDF {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void test() {
		String fileS = "resources/contact/OrganizationInitialization.yaml";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		ArrayList<ListOfElementInformation> results;
		System.out.println("---------------");
		try {
			results = ReadYamlDataset.readYaml(in);
			System.out.println("=============================================: " + results.size());
			for (ListOfElementInformation info : results) {
				for (YamlDatasetInformation yaml : info) {
					System.out.println("Class: " + yaml.getDataelement());
					DatabaseObject obj = yaml.getObject();
					StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), "1");
					WriteDatabaseObjectRDF.writeRDF(obj, store);
					System.out.println(store.toString());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
