package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.dataset.ClassificationInformation;

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
	public static ClassificationInformation getIdentificationInformation(DatabaseObject top, DataElementInformation element) {
		
		String id = element.getDataElementName();
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
	 * @param structure The ID structure object (subclass of ID)
	 * @return The object the ID refers to
	 * 
	 * ?type: The actual object type
	 * ?id: The identifier of the object
	 * ?super: The superclass of the object
	 * 
	 * SELECT ?id ?type ?super
		WHERE {
   			dataset:OrganizationID <http://purl.org/dc/terms/references> ?type .
			?type <http://purl.org/dc/terms/identifier> ?id .
			?type rdfs:subClassOf ?super .
			?super rdfs:subClassOf dcat:Dataset
  }
	 */
	static public DataElementInformation getSubElementStructureFromIDObject(String structure) {
		String query = "SELECT ?id ?type ?super\n" + 
				"	WHERE {\n" + 
				"   " + structure + " <http://purl.org/dc/terms/references> ?type .\n" + 
				"	?type <http://purl.org/dc/terms/identifier> ?id .\n" + 
				"	?type rdfs:subClassOf ?super .\n" + 
				"	?super rdfs:subClassOf dcat:Dataset\n" + 
				"  }";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		
		DataElementInformation info = null;
		if(stringlst.size() > 0) {
			String idS = stringlst.get(0).get("id");
			String typeS = stringlst.get(0).get("type");
			String superS = stringlst.get(0).get("super");
			info = new DataElementInformation(typeS, true, 1, superS, idS);
		}
		return info;
	}
	
	
	/**
	 * From the structure/substructure name, return the list of
	 * DataElementInformation
	 * 
	 * @param structure
	 *            This is the data structure or substructure.
	 * @return A list of DataElementInformation, one for each sub-item within the
	 *         structure.
	 *         
	 *  ?sub:  The general type of the object
	 *  ?card  The number of times the object can be repeated
	 *  ?substructure The sub element type
	 *  ?id The identifier key for the subelement
	 *  ?superclass 
	 *         
	 *  SELECT ?sub  ?pred ?card ?superclass ?id ?substructure 
			WHERE {
				dataset:ContactInfoData rdfs:subClassOf ?sub .
				{
     				{  ?sub owl:onClass ?substructure  .  ?sub owl:qualifiedCardinality ?card }
				UNION
					{   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}
      			} .
				?sub ?pred ?substructure .
     			?substructure <http://purl.org/dc/terms/identifier> ?id .
			}
			
			:ContactLocationInformation rdfs:isDefinedBy <http://www.w3.org/2006/vcard/ns#Location> ;
                            <http://purl.org/dc/terms/type> "ContactLocationInformation"^^xsd:string ;
                            rdfs:label "Contact Location Information"^^xsd:string .
                 
                 
			:ContactLocationInformation rdf:type owl:Class ;
                            rdfs:subClassOf :ChemConnectCompoundDataStructure ,
                                            :ChemConnectDataUnit ,
                                            [ rdf:type owl:Restriction ;
                                              owl:onProperty <http://purl.org/dc/terms/hasPart> ;
                                              owl:qualifiedCardinality "1"^^xsd:nonNegativeInteger ;
                                              owl:onClass :LocationAddress
                                            ] ,

			
			###  http://www.esblurock.info/dataset#LocationAddress
			:LocationAddress rdf:type owl:NamedIndividual ;
                 <http://purl.org/dc/terms/identifier> "vcard:street-address"^^xsd:string .
	
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



	/** Return the ID structure using the type identifier.
	 * @param objid The object identifer.
	 * @return The ID object represented by the object with the identifier given 
	 * 
	 * ?ref is the object and from the object the ID is found.
	 * 
	 * SELECT ?obj ?ref ?id
			WHERE {
				?ref <http://purl.org/dc/terms/identifier> "dc:description"^^xsd:string .
				?id <http://purl.org/dc/terms/references> ?ref
  				}
		id=dataset:DescriptionID
		
		If this object does not have an ID structure, the null is returned:
		SELECT ?obj ?ref ?id
			WHERE {
				?ref <http://purl.org/dc/terms/identifier> "dataset:GPSLocation"^^xsd:string .
				?id <http://purl.org/dc/terms/references> ?ref
  			}
		id=null	
	 * 
	 */
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

	/**
	 * @param name The element name
	 * @return The ChemConnect type 
	 * 
	 *   dataset:ContactSiteOf --->   HTTPAddress
	 *   
	 */
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

	/**
	 * @param query query with the object
	 * @return The corresponding ChemConnect structure of the general type.
	 * 
	 * ?object:  The general type of object
	 * ?type:    The corresponding ChemConnect structure of the general type
	 * 
	 * SELECT ?object ?type
			WHERE {
     			dataset:ContactHasSite  rdfs:subClassOf ?object .
				?object <http://purl.org/dc/terms/type> ?type
			}
	 * 
	 * ###  http://www.esblurock.info/dataset#ContactHasSite
		:ContactHasSite rdf:type owl:Class ;
                			rdfs:subClassOf :HTTPAddress .
                
       ###  http://www.esblurock.info/dataset#HTTPAddress
		:HTTPAddress rdf:type owl:Class ;
             rdfs:subClassOf :ChemConnectPrimitiveDataStructure ;
             <http://purl.org/dc/terms/type> "HTTPAddress"^^xsd:string ;
             rdfs:isDefinedBy <http://purl.org/spar/fabio/hasURL> ;
             rdfs:label "HTTP address data"^^xsd:string .

	 */
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
	
	
	
	public static List<DataElementInformation> elementsFromSimpleType(String type) {
		String query = " SELECT ?type\n" + 
				"			WHERE {\n" + 
				"				?type <http://purl.org/dc/terms/type>  \"" + type + "\"^^xsd:string }\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		List<DataElementInformation> subelements = null;
		for (Map<String, String> map : stringlst) {
			String typeS = map.get("type");
			subelements = DatasetOntologyParsing.getSubElementsOfStructure(typeS);
		}
		return subelements;
		
	}
	
	/**
	 * @param objid
	 * @return
	public static String objectFromIdentifier(String objid) {
		String query = "SELECT ?ref\n" 
				+ "	WHERE {\n" 
				+ "	?ref <http://purl.org/dc/terms/identifier> " + objid
				+ "  }";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String id = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			id = (String) map.get("ref");
		}
		return id;

	}
	
	
	public static String getStructureFromID(String id) {
		String query = "SELECT ?ref\n" + 
				"	WHERE {\n" + 
				"	 " + id + " <http://purl.org/dc/terms/references> ?ref\n" + 
				"  }";
		String structure = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if(stringlst.size() > 0) {
			structure = stringlst.get(0).get("ref");
		}
		return structure;
	}

	 */

}
