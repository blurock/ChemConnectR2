package info.esblurock.reaction.core.server.yaml;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructureObject;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.initialization.yaml.ReadYamlDataset;

public class ReadAndWriteFromDatabase {
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
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterInitializationData.register();
		RegisterRDFData.register();
		RegistrerDataset.register();
		ObjectifyService.register(DatabaseObject.class);

		String fileS = "resources/contact/OrganizationInitialization.yaml";
		String sourceID = "1";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
			Object object;
			try {
				object = reader.read();
				Map<String, Object> map = (Map<String, Object>) object;
				ArrayList<ChemConnectDataStructureObject> lst = ReadYamlDataset.ExtractListOfObjects(map,sourceID);
				for(ChemConnectDataStructureObject structure: lst) {
					WriteReadDatabaseObjects.writeChemConnectDataStructureObject(structure);
					ChemConnectDataStructure datastructure = structure.getChemconnect();
					ClassificationInformation classification = datastructure.getClassification();
					//DatabaseObject obj = structure.getObjecthierarchy().getObject();
					System.out.println(classification.toString());
					/*
					WriteReadDatabaseObjects.readChemConnectDataStructureObject(classification.getIdName(),
							obj.getIdentifier());
						*/
				}
			} catch (YamlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
