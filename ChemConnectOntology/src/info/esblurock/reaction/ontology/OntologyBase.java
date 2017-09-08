package info.esblurock.reaction.ontology;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class OntologyBase {

	/**
	 * A static subclass to generate/return static items.
	 *
	 */
	public static class Util {
		static OntModel unitsmodel = null;
		static OntModel datasetmodel = null;
		static Map<String, String> namespaceMap = null;

		/**
		 * Static routine to generate/return the ontology of units
		 * 
		 * @return The ontology for scientific units
		 */
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

		/**
		 * This routine generates/returns the ontology for UI structures and data
		 * structure relationships
		 * 
		 * @return The ontology model for data structures and data relations
		 */
		public static OntModel getDatabaseOntology() {
			if (datasetmodel == null) {

				String filename = "/info/esblurock/reaction/ontology/resources/Dataset.ttl";
				InputStream str = OntologyBase.class.getResourceAsStream(filename);
				datasetmodel = ModelFactory.createOntologyModel();
				try {
					datasetmodel.read(str, "http://esblurock.info", "TURTLE");
				} catch (Exception ex) {
					System.out.println("Error in reading Ontology:   " + filename + "\n" + ex.toString());
				}
				System.out.println("OntModel getDatabaseOntology() done");

			}
			return datasetmodel;
		}

		/**
		 * This routine generates/returns the namespace mappings
		 * 
		 * @return The namespace to abbreviation mapping
		 */
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
				namespaceMap.put("http://purl.org/dc/terms/", "dcterms");
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
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + "PREFIX dcat: <http://www.w3.org/ns/dcat#>\n"
				+ "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>\n"
				+ "PREFIX dataset: <http://www.esblurock.info/dataset#>\n";
		return databasePrefix;
	}

	/**
	 * The prefix data (standard namespaces) is appended to the SELECT part of the
	 * query
	 * 
	 * @param queryS:
	 *            The query beginning with SELECT
	 * @return The raw ResultSet to the query
	 */
	public static ResultSet datasetQueryBase(String queryS) {
		OntModel model = OntologyBase.Util.getDatabaseOntology();
		String fullquery = getStandardPrefixDatabase() + queryS;
		Query query = QueryFactory.create(fullquery);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		return results;
	}

	/**
	 * This converts the ResultSet to a mapping of keyword to RDF nodes
	 * 
	 * Each member of the List is a map from keywords to RDF nodes. Each member
	 * represents a result.
	 * 
	 * @param results:
	 *            The Result set from a query
	 * @return A mapping from keywords to RDFNodes
	 */
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

	/**
	 * From the query a list of mappings from keywords to RDFNodes
	 * 
	 * @param queryS
	 *            The query string
	 * @return A list of mappings from keywords to RDFNodes
	 */
	public static List<Map<String, RDFNode>> resultSetToMap(String queryS) {
		ResultSet set = datasetQueryBase(queryS);
		List<Map<String, RDFNode>> lst = resultSetToMap(set);
		return lst;
	}

	/**
	 * The simplifies the mapping to RDFNodes to a mapping to a String
	 * 
	 * From a Resource the namespace and the local string are separated out. The
	 * full namespace name is simplified to the standard abbreviation. From a
	 * Literal the value is isolated and converted to a string. Anything else is
	 * just converted to a String.
	 * 
	 * @param results
	 *            A list of mappings from keywords to RDFNodes
	 * @return A list of mappings from a keyword to a String result.
	 */
	public static ArrayList<Map<String, String>> resultmapToStrings(List<Map<String, RDFNode>> results) {
		ArrayList<Map<String, String>> resultmaplst = new ArrayList<Map<String, String>>();
		for (Map<String, RDFNode> map : results) {
			Map<String, String> namemap = new HashMap<String, String>();
			Set<String> names = map.keySet();
			for (String name : names) {
				RDFNode node = map.get(name);
				if (node.isResource()) {
					Resource resource = node.asResource();
					String namespace = resource.getNameSpace();
					String local = resource.getLocalName();
					String simplenamespace = convertNameSpace(namespace);
					String full = simplenamespace + ":" + local;
					if (namespace == null) {
						full = node.toString();
					}
					namemap.put(name, full);
				} else if (node.isLiteral()) {
					Literal literal = node.asLiteral();
					// System.out.println("Literal: " + name + "(" + literal.getDatatypeURI() + "):
					// " + literal.getValue().toString());
					String value = literal.getValue().toString();
					namemap.put(name, value);
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

	/**
	 * A help routine to isolate a single property from the list of mappings.
	 * 
	 * @param property
	 *            The property to be isolated
	 * @param stringlst
	 *            A list of mappings from a keyword to a String result.
	 * @return A list of the String of the property
	 */
	public static List<String> isolateProperty(String property, List<Map<String, String>> stringlst) {
		List<String> lst = new ArrayList<String>();
		for (Map<String, String> map : stringlst) {
			String value = map.get(property);
			lst.add(value);
		}

		return lst;
	}

	/**
	 * @param namespace
	 *            The full namespace name (from Resource.getNamespace())
	 * @return The standard abbreviated namespace name
	 */
	public static String convertNameSpace(String namespace) {
		String converted = Util.namespaceMap().get(namespace);
		if (converted == null) {
			converted = namespace;
		}
		return converted;
	}
}
