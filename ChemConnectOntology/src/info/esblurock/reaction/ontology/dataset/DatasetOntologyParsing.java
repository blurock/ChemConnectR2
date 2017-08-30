package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.test.dataset.ClassificationInformation;

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
		String query = "SELECT ?object \n" + "	WHERE {" + "?object rdfs:subClassOf dataset:ChemConnectDataStructure "
				+ "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> structurelst = OntologyBase.isolateProperty("object", stringlst);
		//
		structurelst.remove("dataset:ChemConnectDataStructure");
		return structurelst;
	}

	/**
	 * @param id
	 * @return
	 */
	public static ClassificationInformation getIdentificationInformation(DatabaseObject top, String id) {
		String query = "SELECT ?identifier ?datatype\n" 
				+ "	WHERE {\n" 
				+ id + " <http://purl.org/dc/terms/identifier> ?identifier .\n" + id
				+ " <http://purl.org/dc/terms/type>  ?datatype\n" 
				+ "  }";

		ClassificationInformation classification = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		if (lst.size() > 0) {
			List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
			Map<String, String> map = stringlst.get(0);
			String identifier = map.get("identifier");
			String datatype = map.get("datatype");
			classification = new ClassificationInformation(top, id, identifier, datatype);
		}
		return classification;
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
		String query = "SELECT ?sub  ?pred ?card ?superclass ?id ?substructure \n" + "	WHERE {\n" + structure
				+ " rdfs:subClassOf ?sub .\n" + "		{\n" + "     {  ?sub owl:onClass ?substructure  . \n"
				+ "         ?sub owl:qualifiedCardinality ?card }\n" + "		UNION\n"
				+ "		{   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}\n" + "      } .\n"
				+ "		?sub ?pred ?substructure .\n"
				+ "     ?substructure <http://purl.org/dc/terms/identifier> ?id .\n" + "}";
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
			String identifier = (String) map.get("id");
			String chemconnect = getChemConnectDirectTypeHierarchy(substructure);
			DataElementInformation element = new DataElementInformation(substructure, singlet, numberOfElements,
					chemconnect, identifier);
			info.add(element);
		}
		return info;
	}

	public static String objectFromIdentifier(String objid) {
		String query = "SELECT ?ref\n" 
				+ "	WHERE {\n" 
				+ "	?ref <http://purl.org/dc/terms/identifier> " + objid
				+ "  }";
		System.out.println(query);
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String id = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			id = (String) map.get("ref");
		}
		System.out.println("Answer: " + id);
		return id;

	}

	
	public static String id(String objid) {
		String query = "SELECT ?obj ?ref ?id\n" 
				+ "	WHERE {\n" 
				+ "	?ref <http://purl.org/dc/terms/identifier> \""
				+ objid + "\"^^xsd:string .\n" 
				+ "	?id <http://purl.org/dc/terms/references> ?ref\n" + "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String id = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			id = (String) map.get("id");
		}
		return id;

	}

	public static String identifierFromID(String id) {
		String query = "SELECT ?obj ?ref ?id\n" + "	WHERE {\n" + id + "  rdfs:subClassOf dataset:Identifier .\n" + id
				+ " <http://purl.org/dc/terms/references> ?ref .\n"
				+ "		?ref <http://purl.org/dc/terms/identifier> ?id\n" + "  }";
		String classid = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			classid = map.get("id");
		}
		return classid;
	}

	public static String idFromObject(String object) {
		String query = "SELECT ?obj ?ref ?id\n" + "	WHERE {\n" + "		?obj  rdfs:subClassOf dataset:Identifier .\n"
				+ "		?obj <http://purl.org/dc/terms/references> " + object + " .\n" + "		" + object
				+ " rdfs:subClassOf dataset:ChemConnectCompoundDataStructure .\n" + "		" + object
				+ " <http://purl.org/dc/terms/identifier> ?id\n" + "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String obj = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			obj = map.get("obj");
		}
		return obj;
	}

	public static List<String> getSubElements(String name) {
		ArrayList<String> subelements = new ArrayList<String>();
		String query = "SELECT ?sub ?substructure\n" + "	WHERE {" + name + " rdfs:subClassOf ?sub .\n"
				+ "      ?sub owl:onClass ?substructure }";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		for (Map<String, String> map : stringlst) {
			String substructure = map.get("substructure");
			subelements.add(substructure);
		}
		return subelements;
	}

	public static String getChemConnectDirectTypeHierarchy(String name) {

		String direct = "SELECT ?type\n" + "	WHERE {\n" + name + " <http://purl.org/dc/terms/type> ?type .\n"
				+ "	}";

		String objname = getHierarchString(direct);
		if (objname == null) {

			String sub = "SELECT ?object ?type\n" + "	WHERE {\n" + "     " + name + "  rdfs:subClassOf ?object .\n"
					+ "		?object <http://purl.org/dc/terms/type> ?type\n" + "	}";
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
