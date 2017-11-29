package info.esblurock.reaction.core.server.yaml;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ListOfElementInformation;
import info.esblurock.reaction.core.server.db.rdf.WriteDatabaseObjectRDF;
import info.esblurock.reaction.io.rdf.StoreObject;
import info.esblurock.reaction.ontology.initialization.ReadYamlDataset;

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
				System.out.println("=============================================: " + info.getMainstructure());
				for (DatasetInformationFromOntology yaml : info) {
					System.out.println(yaml);
					DatabaseObject obj = yaml.getObject();
					StoreObject store = new StoreObject(obj.getIdentifier(), obj.getOwner(), obj.getIdentifier(), "1");
					WriteDatabaseObjectRDF.writeRDF(obj, store);
					System.out.println(store.toString());
					try {
						store.finish();
					} catch(Exception ex) {
						System.out.println("Did not store: " 
								+ obj.getIdentifier() + "("+ yaml.getDataelement() + ")\n" + ex.toString());
					}
				}
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
