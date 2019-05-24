package info.esblurock.reaction.ontology.units;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.concepts.UnitProperties;
import info.esblurock.reaction.ontology.AlternativeEntryWithAppFiles;
import info.esblurock.reaction.ontology.dataset.OntologyBase;
import info.esblurock.reaction.ontology.utilities.IsolateResultList;

public class OntologyUnits extends OntologyBase {

	public static ArrayList<Resource> getUnitSet(String unittype) {
		String queryPrefix = OntologyBase.getStandardPrefixUnits();

		String queryString = queryPrefix + "SELECT  ?subject" + "\n" + " WHERE {?subject ?p " + unittype + " }";
		ResultSet results = datasetQueryBase(queryString);
		IsolateResultList isolate = new IsolateResultList();
		ArrayList<Resource> lst = isolate.getResourceListFromResultSet(results);
		/*
		 * Query query = QueryFactory.create(queryString); QueryExecution qe = null;
		 * ArrayList<Resource> lst = null; try { qe =
		 * QueryExecutionFactory.create(query, m); ResultSet results = qe.execSelect();
		 * // results = ResultSetFactory.copyResults(results) ; } catch
		 * (ConcurrentModificationException ex) {
		 * System.out.println("Concurrent: getUnitSet  " + unittype); } finally {
		 * qe.close(); }
		 */
		return lst;
	}

	public static UnitProperties UnitInformation(String unitname) {
		UnitProperties props = new UnitProperties(unitname);
		AlternativeEntryWithAppFiles alt = new AlternativeEntryWithAppFiles();
		String queryPrefix = OntologyBase.getStandardPrefixUnits();
		String query = queryPrefix + "SELECT ?predicate ?object" + "\n" + "	WHERE { " + alt.getQUDTUnitPrefix()
				+ unitname + " ?predicate ?object}";
		List<Map<String, RDFNode>> results = resultSetToMap(query);
		ArrayList<Map<String, String>> lststring = resultmapToStrings(results);
		String conversionOffset = "0";
		String conversionMultiplier = "1";
		String symbol = "";
		String abbreviation = unitname;
		String code = "0";
		for (Map<String, String> map : lststring) {
			String pred = map.get("predicate");
			String object = map.get("object");
			if (pred.compareTo("qudt:conversionOffset") == 0) {
				conversionOffset = object;
			} else if (pred.compareTo("qudt:conversionMultiplier") == 0) {
				conversionMultiplier = object;
			} else if (pred.compareTo("qudt:symbol") == 0) {
				symbol = object;
			} else if (pred.compareTo("qudt:abbreviation") == 0) {
				abbreviation = object;
			} else if (pred.compareTo("qudt:code") == 0) {
				code = object;
			}
		}
		props.fillAsValue(conversionOffset, conversionMultiplier, symbol, abbreviation, code);
		/*
		 * Query querymass = QueryFactory.create(queryMass); QueryExecution massqe =
		 * null; try { massqe = QueryExecutionFactory.create(querymass, m); ResultSet
		 * massresults = massqe.execSelect(); while (massresults.hasNext()) {
		 * QuerySolution solution = massresults.next(); RDFNode node =
		 * solution.get("object"); RDFNode predicate = solution.get("predicate"); if
		 * (node.isLiteral()) {
		 * 
		 * Resource presource = predicate.asResource();
		 * props.addProperty(presource.getLocalName(), node.asLiteral().getString()); }
		 * else if (node.isResource()) { Resource oresource = node.asResource(); if
		 * (predicate.isResource()) { Resource presource = predicate.asResource(); if
		 * (oresource.getLocalName() != null) { String p = presource.getLocalName();
		 * String v = oresource.getLocalName(); if (p.compareTo("type") == 0) {
		 * props.addType(v); } else { props.addProperty(p, v); } } }
		 * 
		 * } } } catch (ConcurrentModificationException ex) {
		 * System.out.println("Concurrent: UnitInformation   " + unitname); } finally {
		 * massqe.close(); }
		 */
		return props;
	}

	public static SetOfUnitProperties getSetOfUnitProperties(String topunit) {
		SetOfUnitProperties propset = new SetOfUnitProperties(topunit);
		String simpletopunit = removeNamespace(topunit);
		boolean keywordunit = OntologyUnits.isAKeyWordUnit(topunit);
		if (keywordunit) {
			propset.setKeyword(keywordunit);
		} else {
			Set<String> classifications = classifications(topunit);
			if (classifications == null) {
				ArrayList<Resource> lst = OntologyUnits.getUnitSet(topunit);
				if (lst != null) {
					for (Resource resource : lst) {

						UnitProperties unit = OntologyUnits.UnitInformation(resource.getLocalName());
						if (unit.getUnitName() != null) {
							if (unit.getUnitName().compareTo(simpletopunit) != 0) {
								propset.addUnitProperties(unit);
							}
						}
					}
				} else {
					System.out.println("Classification null: " + topunit);
				}
			} else {
				propset.setClassification(true);
				for (String classification : classifications) {
					if (classification != null) {
						if (classification.compareTo(topunit) != 0) {
							UnitProperties unit = new UnitProperties();
							unit.fillAsClassification(classification);
							propset.addUnitProperties(unit);
						}
					}
				}
			}
		}
		return propset;
	}

	public static Set<String> classifications(String classification) {
		String query = "SELECT ?propertyname ?keyword\n" + " WHERE {" + classification + " rdfs:subClassOf  ?obj .\n"
				+ "              ?obj  owl:onProperty <http://purl.org/linked-data/cube#concept> .\n"
				+ "              ?obj owl:onClass ?propertyname .\n"
				+ "              ?keyword rdfs:subClassOf ?propertyname\n" + "         }";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		Set<String> set = null;
		if (stringlst.size() > 0) {
			set = new HashSet<String>();
			for (Map<String, String> map : stringlst) {
				String propS = map.get("keyword");
				set.add(propS);
			}
		}
		return set;
	}

	public static boolean isAKeyWordUnit(String unit) {
		String query = "ASK {" + "	" + unit + " rdfs:subClassOf dataset:KeywordUnit }";
		boolean result = OntologyBase.datasetASK(query);
		return result;
	}

	public static String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String ans = name;
		if (pos >= 0) {
			ans = name.substring(pos + 1);
		}
		return ans;
	}

}
