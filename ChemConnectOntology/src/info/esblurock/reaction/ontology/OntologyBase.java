package info.esblurock.reaction.ontology;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class OntologyBase {

	// static String fileS = "/Users/edwardblurock/Google\\
	// Drive/Ontologies/ChemConnectOntologies/Dataset.ttl";
	static String fileS = "file:///Users/edwardblurock/Dataset.ttl";

	// static String filename = "/info/esblurock/reaction/ontology/Dataset.ttl";
	public static class Util {
		static OntModel unitsmodel = null;
		static OntModel datasetmodel = null;
		static Map<String, String> namespaceMap = null;

		public static OntModel getUnitsOntology() {
			if (unitsmodel == null) {
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
			if (datasetmodel == null) {

				String filename = "/info/esblurock/reaction/ontology/resources/Dataset.ttl";
				InputStream str = ClassLoader.class.getResourceAsStream(filename);
				System.out.println("OntModel getDatabaseOntology()");
				datasetmodel = ModelFactory.createOntologyModel();
				datasetmodel.read(str, "http://esblurock.info", "TURTLE");
				System.out.println("OntModel getDatabaseOntology() done");

			}
			return datasetmodel;
		}

		public static Map<String, String> namespaceMap() {
			if (namespaceMap == null) {
				namespaceMap = new HashMap<String, String>();

				namespaceMap.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
				namespaceMap.put("http://www.w3.org/2002/07/owl#", "owl");
				namespaceMap.put("http://www.w3.org/2000/01/rdf-schema#", "rdfs");
				namespaceMap.put("http://www.w3.org/2001/XMLSchema#", "xsd");
				namespaceMap.put("http://data.nasa.gov/qudt/owl/unit#", "unit");
				namespaceMap.put("http://data.nasa.gov/qudt/owl/quantity#", "quant");
				namespaceMap.put("http://data.nasa.gov/qudt/owl/qudt#", "qudt");
				namespaceMap.put("http://www.w3.org/ns/dcat#", "dcat");
				namespaceMap.put("http://www.esblurock.info/dataset#", "dataset");
			}
			return namespaceMap;
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

	public static String getStandardPrefixDatabase() {
		String databasePrefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX dcat: <http://www.w3.org/ns/dcat#>\n"
				+ "PREFIX dataset: <http://www.esblurock.info/dataset#>\n";
		return databasePrefix;
	}

	public static ResultSet datasetQueryBase(String queryS) {
		OntModel model = OntologyBase.Util.getDatabaseOntology();
		String fullquery = getStandardPrefixDatabase() + queryS;
		Query query = QueryFactory.create(fullquery);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		return results;
	}

	public static List<Map<String, RDFNode>> resultSetToMap(ResultSet results) {
		ArrayList<Map<String, RDFNode>> lst = new ArrayList<Map<String, RDFNode>>();
		while (results.hasNext()) {
			Object solution = results.next();
			ResultBinding result = (ResultBinding) solution;
			Iterator<String> names = result.varNames();
			HashMap<String, RDFNode> map = new HashMap<String, RDFNode>();
			while (names.hasNext()) {
				String name = names.next();
				RDFNode value = result.get(name);
				map.put(name, value);
			}
			lst.add(map);
		}
		return lst;
	}

	public static List<Map<String, RDFNode>> resultSetToMap(String queryS) {
		ResultSet set = datasetQueryBase(queryS);
		List<Map<String, RDFNode>> lst = resultSetToMap(set);
		return lst;
	}

	public static ArrayList<Map<String, String>> resultmapToStrings(List<Map<String, RDFNode>> results) {
		ArrayList<Map<String, String>> resultmaplst = new ArrayList<Map<String, String>>();
		for (Map<String, RDFNode> map : results) {
			Map<String,String> namemap = new HashMap<String,String>();
			Set<String> names = map.keySet();
			for (String name : names) {
				RDFNode node = map.get(name);
				if (node.isResource()) {
					Resource resource = node.asResource();
					String namespace = resource.getNameSpace();
					String local = resource.getLocalName();
					String simplenamespace = convertNameSpace(namespace);
					String full = simplenamespace + ":" + local;
					if(namespace == null) {
						full = node.toString();
					}
					namemap.put(name, full);
				} else if(node.isLiteral()) {
					Literal literal = node.asLiteral();
					System.out.println("Literal: " +  name + "(" + literal.getDatatypeURI() + "):  " + literal.getValue().toString());
					String value = literal.getValue().toString();
					namemap.put(name,value);
				} else {
					System.out.println("Other: " + node.isAnon());
					System.out.println("Type: " + node.toString());
					System.out.println("" + node.asNode());
					namemap.put(name, node.toString());
				}
			}
			resultmaplst.add(namemap);
		}
		return resultmaplst;
	}
	public static List<String> isolateProperty(String property, List<Map<String,String>> stringlst) {
		List<String> lst = new ArrayList<String>();
		for(Map<String, String> map : stringlst) {
			String value = map.get(property);
			lst.add(value);
		}
		
		return lst;
	}
	public static String convertNameSpace(String namespace) {
		String converted = Util.namespaceMap().get(namespace);
		if(converted == null) {
			converted = namespace;
		}
		return converted;
	}
}
