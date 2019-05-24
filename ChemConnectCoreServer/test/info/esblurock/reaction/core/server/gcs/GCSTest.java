package info.esblurock.reaction.core.server.gcs;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlWriter;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.image.GCSServiceRoutines;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class GCSTest {
	
	protected Closeable session;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setUp() {
		this.session = ObjectifyService.begin();
	}
	
	@After
	public void tearDown() {
		AsyncCacheFilter.complete();
		this.session.close();
		this.helper.tearDown();
	}


	@Test
	public void test() {
		String username = "Administration";
		String owner = username;
		String sourceID = "0";
		String access = "Public";
		String userrole = MetaDataKeywords.access;
		String catname = "Catalog";
		
		DatabaseObject usrcatobj = new DatabaseObject(catname,access,username,sourceID);
		ChemConnectCompoundDataStructure structure1 = new ChemConnectCompoundDataStructure(usrcatobj,"");
		ArrayList<String> userPath = new ArrayList<String>();
		userPath.add(catname);
		DataCatalogID namecatid = new DataCatalogID(structure1,catname,"dataset:UserDataCatagory",username,userPath);

		DatabaseObject obj = new DatabaseObject(username, access, owner, sourceID);
		ChemConnectCompoundDataStructure pstructure = new ChemConnectCompoundDataStructure(obj,null);
		NameOfPerson person = new NameOfPerson(pstructure, "", "", username);
		
		
		DatabaseObjectHierarchy user = CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj, username, userrole, person,namecatid);

		try {
			Map<String,Object> map1 = ReadWriteYamlDatabaseObjectHierarchy.yamlDatabaseObjectHierarchy(user);
			StringWriter wS = new StringWriter(1000000);
			YamlWriter writer = new YamlWriter(wS);
			writer.write(map1);
			writer.close();

			ChemConnectDataStructure structure = (ChemConnectDataStructure) user.getObject();
			String idS = structure.getCatalogDataID();
			DatabaseObjectHierarchy catalogHier = user.getSubObject(idS);
			DataCatalogID catalogID = (DataCatalogID) catalogHier.getObject();
			String filename = catalogID.blobFilenameFromCatalogID(".yaml");
			String contentType = ConceptParsing.getContentType(StandardDatasetMetaData.yamlFileType);
			String title = structure.getClass().getSimpleName() + ": " + structure.getIdentifier();
			String ip = "localhost:8080";
			String path = GCSServiceRoutines.createUploadPath(username);
			GCSServiceRoutines.uploadFileBlob(user.getObject().getIdentifier(),
					GCSServiceRoutines.getGCSStorageBucket(), 
					ip,username,
					path, filename,contentType,title,wS.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}

}
