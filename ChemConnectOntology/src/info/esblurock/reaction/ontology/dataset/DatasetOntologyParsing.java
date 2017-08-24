package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

import info.esblurock.reaction.ontology.OntologyBase;

public class DatasetOntologyParsing {

	/**
	 * This returns all available UI/database main structures.
	 * 
	 * The name has the standard namespace abbreviation attached to it. This list is
	 * essentially the subClasses of 'dataset:ChemConnectDataStructure'
	 * 
	 * @return The list of main data UI/database structures
	 */
	public static List<String> getMainDataStructures() {
		String query = "SELECT ?object \n" 
				+ "	WHERE {" 
				+ "?object rdfs:subClassOf dataset:ChemConnectDataStructure "
				+ "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> structurelst = OntologyBase.isolateProperty("object", stringlst);
		//
		structurelst.remove("dataset:ChemConnectDataStructure");
		return structurelst;
	}

	/**
	 * From the structure/substructure name, return the list of
	 * DataElementInformation
	 * 
	 * @param structure
	 *            This is the data structure or substructure.
	 * @return A list of DataElementInformation, one for each sub-item within the
	 *         structure.
	 */
	public static List<DataElementInformation> getSubElementsOfStructure(String structure) {
		String query = "SELECT ?sub  ?pred ?substructure ?card ?superclass\n" + "	WHERE {\n" + structure
				+ " rdfs:subClassOf ?sub .\n" + "		{{  ?sub owl:onClass ?substructure  . "
				+ "         ?sub owl:qualifiedCardinality ?card }\n" + "		UNION\n"
				+ "		{   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}} .\n"
				+ "		?sub ?pred ?substructure .\n" + "        ?sub rdfs:subClassOf ?superclass" + "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		ListOfDataElementInformation info = new ListOfDataElementInformation();
		for (Map<String, String> map : stringlst) {

			String substructure = map.get("substructure");
			String pred = map.get("pred");
			boolean singlet = true;
			int numberOfElements = 1;
			String numS = map.get("card");
			if (numS != null)
				numberOfElements = Integer.parseInt(numS);
			else
				numberOfElements = 0;
			if (pred.compareTo("owl:someValuesFrom") == 0) {
				singlet = false;
			} else if (pred.compareTo("owl:onClass") == 0) {
				singlet = false;
				if (numberOfElements == 1)
					singlet = true;
			}
			String chemconnect = getChemConnectDirectTypeHierarchy(substructure);
			DataElementInformation element = new DataElementInformation(substructure, singlet, numberOfElements,chemconnect);
			info.add(element);
		}
		return info;
	}

	public static String getChemConnectDirectTypeHierarchy(String name) {

		String direct = "SELECT ?type\n" + "	WHERE {\n" + name + " <http://purl.org/dc/terms/type> ?type .\n"
				+ "	}";

		String objname = getHierarchString(direct);
		if (objname == null) {

			String sub = "SELECT ?object ?type\n" 
					+ "	WHERE {\n" 
					+ "     " + name + "  rdfs:subClassOf ?object .\n"
					+ "		?object <http://purl.org/dc/terms/type> ?type\n" 
					+ "	}";
			objname = getHierarchString(sub);
		}
		return objname;
	}

	public static String getHierarchString(String query) {
		List<Map<String, RDFNode>> maplst = OntologyBase.resultSetToMap(query);
		String str = null;
		if (maplst.size() > 0) {
			Map<String, RDFNode> nodemap = maplst.get(0);
			RDFNode node = nodemap.get("type");
			Literal literal = node.asLiteral();
			str = (String) literal.getValue();
		}
		return str;
	}
}
