package info.esblurock.reaction.ontology;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;


public class OntologyBase {

	//static String fileS = "/Users/edwardblurock/Google\\ Drive/Ontologies/ChemConnectOntologies/Dataset.ttl";
	static String fileS = "/Users/edwardblurock/Dataset.ttl";
	
	public static class Util {
		static OntModel unitsmodel = null;
		static OntModel datasetmodel = null;
		
		public static OntModel getUnitsOntology() {
			if(unitsmodel == null) {
				unitsmodel = ModelFactory.createOntologyModel();
				AlternativeEntryWithAppFiles alt = new AlternativeEntryWithAppFiles();

				unitsmodel.getDocumentManager().addAltEntry(alt.getQUDTQudt(), alt.getQUDTQudtLocal());
				unitsmodel.getDocumentManager().addAltEntry(alt.getQUDTDimension(), alt.getQUDTQudtLocal());
				unitsmodel.getDocumentManager().addAltEntry(alt.getQUDTQuantity(), alt.getQUDTQuantityLocal());
				unitsmodel.getDocumentManager().addAltEntry(alt.getQUDTUnit(), alt.getQUDTUnitLocal());
				// model.read("http://qudt.org/2.0/vocab/VOCAB_QUDT-UNITS-PHYSICAL-CHEMISTRY-AND-MOLECULAR-PHYSICS-v2.0.ttl");
				unitsmodel.read("http://data.nasa.gov/qudt/owl/quantity");
				unitsmodel.read("http://data.nasa.gov/qudt/owl/unit");				
			}
			return unitsmodel;
		}
		
		public static OntModel getDatabaseOntology() {
			if(datasetmodel == null) {
				
				InputStream in = FileManager.get().open(fileS);
				
				System.out.println("OntModel getDatabaseOntology()");
				datasetmodel = ModelFactory.createOntologyModel();
				//datasetmodel = ModelFactory.createDefaultModel();
				datasetmodel.read(fileS);
				System.out.println("OntModel getDatabaseOntology() done");
				
				
			}
			return datasetmodel;
			
		}
	}
	public static String getStandardPrefixUnits() {
		String queryPrefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + "\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + "\n"
				+ "PREFIX unit: <http://data.nasa.gov/qudt/owl/unit#>" + "\n"
				+ "PREFIX quant: <http://data.nasa.gov/qudt/owl/quantity#>" + "\n"
				+ "PREFIX qudt: <http://data.nasa.gov/qudt/owl/qudt#>" + "\n";
		return queryPrefix;
	}

}
