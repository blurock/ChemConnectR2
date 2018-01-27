package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.ontology.OntologyBase;

public class DatasetOntologyParsing {

	/**
	 * This returns all available UI/database main structures.
	 * 
	 * The name has the standard namespace abbreviation attached to it. This list is
	 * essentially the subClasses of 'dataset:ChemConnectDataStructure'
	 * 
	 * For example: [dataset:UserInformation, dataset:Methodology,
	 * dataset:Instrument, dataset:DatasetInformation]
	 * 
	 * 
	 * @return The list of main data UI/database structures
	 */
	public static List<String> getMainDataStructures() {
		return getSubClasses("dataset:ChemConnectDataStructure");
	}

	/*
getChemConnectDataStructureHierarchy()
dataset:ChemConnectDataStructure:
	dataset:UserInformation:
		dataset:UserAccount:
		dataset:Organization:
		dataset:DatabaseUser:
		dataset:Consortium:
	dataset:Methodology:
	dataset:DeviceDescriptions:
		dataset:SubSystemDescription:
		dataset:Instrument:
		dataset:DeviceSensor:
	dataset:DatasetInformation:
		dataset:DataSetHierarchy:
		dataset:DataSetCatalog:
		 */
	public static HierarchyNode getChemConnectDataStructureHierarchy() {
		return findClassHierarchy("dataset:ChemConnectDataStructure");
	}

	/*
findClassHierarchy("dataset:ChemConnectPrimitiveDataStructure")
dataset:ChemConnectPrimitiveDataStructure:
	dataset:DateElement:
		dataset:DateIssued:
		dataset:DateCreated:
	dataset:OneLine:
		dataset:ReferenceTitle:
		dataset:ReferenceString:
		dataset:DescriptionTitle:
	dataset:ShortStringLabel:
		dataset:familyName:
		dataset:givenName:
		dataset:OrganizationName:
		.
		.
		.
		.
	 */
	public static HierarchyNode findClassHierarchy(String topNode) {
		ClassificationInformation info = DatasetOntologyParsing.getIdentificationInformation(topNode);		
		HierarchyNode top = new HierarchyNode(topNode,info);
		List<String> structurelst = getSubClasses(topNode);
		for (String subclass : structurelst) {
			HierarchyNode node = findClassHierarchy(subclass);
			top.addSubNode(node);
		}

		return top;
	}

	/*
	 * Finds all subclasses: based on that rdfs:subClassOf returns all subclasses
	 * 
		getAllSubClasses("dataset:Classification");
			[dataset:UnitClass, dataset:LocationCountry, dataset:LocationCity, 
			dataset:UnitsOfValue, dataset:DataCatalog, dataset:DataAccess, 
			dataset:UserAccountRole, dataset:DataOwner, dataset:OrganizationClassification, 
			dataset:UserClassification, dataset:SourceKey, dataset:UserTitle, dataset:DataCreator]

	 */
	public static List<String> getAllSubClasses(String topclassS) {
		String query = "SELECT ?directSub\n" + " WHERE { ?directSub rdfs:subClassOf " + topclassS + " .\n" + " }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> structurelst = OntologyBase.isolateProperty("directSub", stringlst);
		structurelst.remove(topclassS);
		return structurelst;
	}

	/*
	 * This returns the direct set of subclasses. First all subclasses are found,
	 * then for each element all their subclasses are eliminated based on the fact
	 * that rdfs:subClassOf returns all subclasses
	 * 
	 * getSubClasses("dataset:ChemConnectPrimitiveDataStructure");
			[dataset:DateElement, dataset:OneLine, dataset:ShortStringLabel, 
			dataset:Email, dataset:Classification, dataset:HTTPAddress, 
			dataset:GPSData, dataset:Paragraph, dataset:DatabaseID, 
			dataset:Keyword, dataset:PasswordElement]

	 */
	public static List<String> getSubClasses(String topclassS) {
		List<String> structurelst = getAllSubClasses(topclassS);
		structurelst.remove(topclassS);
		ArrayList<String> toplist = new ArrayList<String>(structurelst);
		for (String name : structurelst) {
			List<String> subs = getAllSubClasses(name);
			toplist.removeAll(subs);
		}
		return toplist;
	}
/*
 * DataType: dataset:ChemConnectDataStructure
	Records
		dataset:DescriptionDataData: dcat:record	 (singlet)
		dataset:DataSetReference: dcat:record	 (some)
	LinkedTo
		dataset:Consortium: org:linkedTo	 (some)
	Other

	MapToChemConnectCompoundDataStructure
		ChemConnectCompoundDataStructure: dataset:DataSetReference
			dataset:ReferenceTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:NameOfPerson(dcterms:hasPart):  foaf:name  (NameOfPerson):  multiple
			dataset:ReferenceString(dcterms:hasPart):  dcterms:description  (OneLine):  single
			dataset:DOI(dcterms:hasPart):  datacite:PrimaryResourceIdentifier  (ShortStringLabel):  single
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:DescriptionDataData
			dataset:DateCreated(dcterms:hasPart):  dcterms:created  (DateObject):  single
			dataset:SourceKey(dcterms:hasPart):  dcat:dataset  (Classification):  single
			dataset:DataSpecification(dcterms:hasPart):  dataset:DataSpecification  (DataSpecification):  single
			dataset:DescriptionTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DescriptionAbstract(dcterms:hasPart):  dcterms:description  (Paragraph):  single
			dataset:DescriptionKeyword(dcterms:hasPart):  dcat:keyword  (Keyword):  multiple
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:NameOfPerson
			dataset:givenName(dcterms:hasPart):  foaf:givenName  (ShortStringLabel):  single
			dataset:familyName(dcterms:hasPart):  foaf:familyName  (ShortStringLabel):  single
			dataset:UserTitle(dcterms:hasPart):  foaf:title  (Classification):  single
		-------------------------------------------------------

 * 
 */
	public static ChemConnectDataStructure getChemConnectDataStructure(String elementStructure) {
		ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation(elementStructure);
		ChemConnectDataStructure set = new ChemConnectDataStructure(classification);
		getSetOfChemConnectDataStructureElements(elementStructure, set);
		return set;
	}

	/*
	 * SELECT ?record ?property ?cardinality
 		WHERE { dataset:ChemConnectDataStructure  rdfs:subClassOf  ?sub .
				?sub owl:onProperty ?property .
					{?sub owl:someValuesFrom ?record   }
						UNION
					{?sub owl:onClass ?record . ?sub owl:qualifiedCardinality ?cardinality}
  				}
 		[{record=dataset:DescriptionDataData, property=dcat:record, cardinality=1}, 
 			{record=dataset:Consortium, property=org:linkedTo}, 
 			{record=dataset:DataSetReference, property=dcat:record}]
 
	 */
	public static void getSetOfChemConnectDataStructureElements(String element, ChemConnectDataStructure set) {
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX dataset: <http://www.esblurock.info/dataset#>\n" 
				+ "PREFIX core: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "SELECT ?record ?property ?cardinality ?identifier ?datatype ?altl\n"
				+ " WHERE { " + element + "  rdfs:subClassOf  ?sub .\n" 
				+ "	?sub owl:onProperty ?property .\n"
				+ "		{?sub owl:someValuesFrom ?record   }\n" 
				+ "	UNION\n"
				+ "		{?sub owl:onClass ?record . ?sub owl:qualifiedCardinality ?cardinality}\n" 
				+ " ?record <http://purl.org/dc/terms/identifier> ?identifier .\n" + 
				"   ?record <http://purl.org/dc/elements/1.1/type>  ?datatype .\n"
				+ "   ?record core:altLabel ?altl"
				+ "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		for (Map<String, String> map : stringlst) {
			String linktype = map.get("property");
			String elementType = map.get("record");
			String cardinalityS = map.get("cardinality");
			String identifierS = map.get("identifier");
			String datatypeS = map.get("datatype");
			String altlabelS = map.get("altl");
			boolean singlet = true;
			int cardinality = 0;
			if (cardinalityS != null) {
				cardinality = Integer.parseInt(cardinalityS);
				if(cardinality > 1) {
					singlet = false;
				}
			} else {
				singlet = false;
			}
			DataElementInformation e = new DataElementInformation(elementType, linktype,
					singlet,cardinality,datatypeS,identifierS,altlabelS);
			ChemConnectCompoundDataStructure record = null;
			if (linktype.compareTo("dcat:record") == 0) {
				record = DatasetOntologyParsing.subElementsOfStructure(e.getDataElementName());
				subElements(record,set);
			}
			set.addElement(e, record);

		}
	}

	public static void subElements(ChemConnectCompoundDataStructure record, ChemConnectDataStructure set) {
		for (DataElementInformation recordelement : record) {
			if (isChemConnectPrimitiveDataStructure(recordelement.getDataElementName()) == null) {
				if(recordelement.getLink().compareTo("dcterms:hasPart") == 0) {
					ChemConnectCompoundDataStructure subrecord = DatasetOntologyParsing
						.subElementsOfStructure(recordelement.getDataElementName());
					set.addToMapping(subrecord);
					subElements(subrecord,set);
				}
			} else {
			}
		}
	}
/*
 * The key to the search is that the element is a compound element (ChemConnectPrimitiveCompound)
 * if not a compound element, then the search returns no values.
 * The class name returned should return just the direct super class.
 * 
 * SELECT DISTINCT ?super
	WHERE {
     dataset:NameOfPerson  rdfs:subClassOf* dataset:ChemConnectPrimitiveDataStructure .
     dataset:NameOfPerson  rdfs:subClassOf ?super .
     ?super rdf:type owl:Class
	}
		[{super=dataset:Name}, {super=dataset:NameOfPerson}, 
			{super=dataset:ChemConnectPrimitiveCompound}, {super=dataset:Component}]
			
	The string return is the first in this array
	(note that in Protege´this returns just the first element)

 */
	public static String isChemConnectPrimitiveDataStructure(String element) {
		String query = "SELECT DISTINCT ?super\n" + "	WHERE {\n" 
				+ element + "  rdfs:subClassOf* dataset:ChemConnectPrimitiveDataStructure .\n" + element
				+ "  rdfs:subClassOf ?super .\n" + "     ?super rdf:type owl:Class\n" + "	}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String superclass = null;
		if (lst.size() > 0) {
			superclass = stringlst.get(1).get("super");
		}
		return superclass;
	}

	/*
	 * public static HierarchyOfElements getHierarchyOfElements() {
	 * 
	 * ArrayList<ChemConnectDataStructure> elements = new
	 * ArrayList<ChemConnectDataStructure>(); HierarchyNode topnode =
	 * getChemConnectDataStructureHierarchy();
	 * getElementsFromHierarchy(elements,topnode); HierarchyOfElements hierarchy =
	 * new HierarchyOfElements(elements, topnode); return hierarchy; }
	 * 
	 * public static void
	 * getElementsFromHierarchy(ArrayList<ChemConnectDataStructure> elements,
	 * HierarchyNode topnode) { if (topnode.getSubNodes().size() > 0) { for
	 * (HierarchyNode node : topnode.getSubNodes()) {
	 * getElementsFromHierarchy(elements,node); } } else { ChemConnectDataStructure
	 * element = getSetOfChemConnectDataStructureElements(topnode.getIdentifier());
	 * elements.add(element); } }
	 */
	/*
	 * For each of the main structures, return the ClassificationInformation with
	 * the following: idName, identifier and dataType link and DatabaseObject are
	 * null.
	 * 
	 * for example: ID: dataset:UserAccount: dataset:UserAccount (UserAccount) ID:
	 * dataset:Organization: dataset:Organization (Organization) ID:
	 * dataset:DatabaseUser: vcard:Individual (IndividualInformation) ID:
	 * dataset:DataSetCatalog: dcat:Catalog (DatasetCatalog) ID: dataset:Consortium:
	 * dataset:Consortium (Consortium)
	 */
	public static ArrayList<ClassificationInformation> getCatalogClassificationInformationList() {
		List<String> lst = getMainDataStructures();
		ArrayList<ClassificationInformation> clslst = new ArrayList<ClassificationInformation>();
		for (String dataElementName : lst) {
			ClassificationInformation clsinfo = DatasetOntologyParsing.getIdentificationInformation(dataElementName);
			clslst.add(clsinfo);
		}
		return clslst;
	}

	public static ClassificationInformation getIdentificationInformation(String dataElementName) {
		DataElementInformation dataelement = new DataElementInformation(dataElementName, null, true, 0, null, null,null);
		return getIdentificationInformation(null, dataelement);
	}

	/**
	 * @param id
	 * @return
	 */
	public static ClassificationInformation getIdentificationInformation(DatabaseObject top,
			DataElementInformation element) {

		String id = element.getDataElementName();
		String query = "SELECT ?identifier ?datatype\n" + "	WHERE {\n" + id
				+ " <http://purl.org/dc/terms/identifier> ?identifier .\n" + id
				+ " <http://purl.org/dc/elements/1.1/type>  ?datatype .\n" + "	{  " + id
				+ " <http://purl.org/dc/elements/1.1/type>  ?datatype } \n" + "UNION\n" + "	{ " + id
				+ " rdfs:subClassOf ?subclass .\n"
				+ "	   ?subclass <http://purl.org/dc/elements/1.1/type>  ?datatype\n" + "	}" + "  }";
		ClassificationInformation classification = null;

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		if (lst.size() > 0) {
			List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
			Map<String, String> map = stringlst.get(0);
			String identifier = map.get("identifier");
			String datatype = map.get("datatype");
			classification = new ClassificationInformation(top, element.getLink(), id, identifier, datatype);
		}
		return classification;
	}

	/**
	 * @param structure
	 *            The ID structure object (subclass of ID)
	 * @return The object the ID refers to
	 * 
	 *         ?type: The actual object type ?id: The identifier of the object
	 *         ?super: The superclass of the object
	 * 
	 *         SELECT ?id ?type ?super WHERE { dataset:OrganizationID
	 *         <http://purl.org/dc/terms/references> ?type . ?type
	 *         <http://purl.org/dc/terms/identifier> ?id . ?type rdfs:subClassOf
	 *         ?super . ?super rdfs:subClassOf dcat:Dataset }
	 */
	static public DataElementInformation getSubElementStructureFromIDObject(String structure) {
		String query = "SELECT ?id ?type ?super ?altl\n" 
				+ "	WHERE {\n" 
				+ "   " + structure + " <http://purl.org/dc/terms/references> ?type .\n"
				+ "	?type <http://purl.org/dc/terms/identifier> ?id .\n" 
				+ "	?type rdfs:subClassOf ?super .\n"
				+ " ?type <http://www.w3.org/2004/02/skos/core#altLabel> ?altl .\n"
				+ "	?super rdfs:subClassOf dcat:Dataset\n" 
				+ "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		DataElementInformation info = null;
		if (stringlst.size() > 0) {
			String idS = stringlst.get(0).get("id");
			String typeS = stringlst.get(0).get("type");
			String superS = stringlst.get(0).get("super");
			String altlabelS = stringlst.get(0).get("altl");
			info = new DataElementInformation(typeS, null, true, 1, superS, idS,altlabelS);
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
	 *         ?sub: The general type of the object ?card The number of times the
	 *         object can be repeated ?substructure The sub element type ?id The
	 *         identifier key for the subelement ?superclass
	 * 
	 *         SELECT ?sub ?pred ?card ?superclass ?id ?substructure WHERE {
	 *         dataset:ContactInfoData rdfs:subClassOf ?sub . { { ?sub owl:onClass
	 *         ?substructure . ?sub owl:qualifiedCardinality ?card } UNION { ?sub
	 *         owl:someValuesFrom|owl:allValuesFrom ?substructure} } . ?sub ?pred
	 *         ?substructure . ?substructure <http://purl.org/dc/terms/identifier>
	 *         ?id . }
	 * 
	 *         :ContactLocationInformation rdfs:isDefinedBy
	 *         <http://www.w3.org/2006/vcard/ns#Location> ;
	 *         <http://purl.org/dc/elements/1.1/type>
	 *         "ContactLocationInformation"^^xsd:string ; rdfs:label "Contact
	 *         Location Information"^^xsd:string .
	 * 
	 * 
	 *         :ContactLocationInformation rdf:type owl:Class ; rdfs:subClassOf
	 *         :ChemConnectCompoundDataStructure , :ChemConnectDataUnit , [ rdf:type
	 *         owl:Restriction ; owl:onProperty <http://purl.org/dc/terms/hasPart> ;
	 *         owl:qualifiedCardinality "1"^^xsd:nonNegativeInteger ; owl:onClass
	 *         :LocationAddress ] ,
	 * 
	 * 
	 *         ### http://www.esblurock.info/dataset#LocationAddress
	 *         :LocationAddress rdf:type owl:NamedIndividual ;
	 *         <http://purl.org/dc/terms/identifier>
	 *         "vcard:street-address"^^xsd:string .
	 * 
	 */
	public static ChemConnectCompoundDataStructure subElementsOfStructure(String structure) {
		String query = "SELECT ?sub  ?pred ?card ?link ?id ?substructure ?subtype ?subsubtype ?altl\n" + "	WHERE {\n"
				+ "      " + structure + " rdfs:subClassOf ?sub .\n" + "		{\n"
				+ "        {  ?sub owl:onClass ?substructure  . \n"
				+ "           ?sub owl:qualifiedCardinality ?card }\n" + "		      UNION\n"
				+ "		   {   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}" + "      } .\n"
				+ "		   ?sub ?pred ?substructure .\n" + "        ?sub owl:onProperty ?link .\n" 
				+ "        ?substructure" + " <http://purl.org/dc/terms/identifier> ?id .\n" 
			    + "        ?substructure <http://www.w3.org/2004/02/skos/core#altLabel> ?altl \n"
			    	   				+ "       }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		ChemConnectCompoundDataStructure info = new ChemConnectCompoundDataStructure(structure);
		for (Map<String, String> map : stringlst) {
			String substructure = map.get("substructure");
			String pred = map.get("pred");
			String link = map.get("link");
			String altl = map.get("altl");
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
			DataElementInformation element = new DataElementInformation(substructure, link, singlet, numberOfElements,
					chemconnect, identifier,altl);
			info.add(element);
		}
		return info;
	}

	/*
	 * From the primitive class type, get the type SELECT ?subtype WHERE {
	 * dataset:ContactEmail rdfs:subClassOf ?subtype } returns: dataset:Email
	 * 
	 */
	public static String getPrimitiveStructureType(String primitivestructure) {
		String query = "SELECT ?subtype\n" + "	WHERE {\n" + "     " + primitivestructure
				+ "  rdfs:subClassOf ?subtype \n" + "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String subtype = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			subtype = map.get("subtype");
		}
		return subtype;
	}

	/*
	 * SELECT ?subsubtype WHERE { dataset:Email rdfs:subClassOf ?subsubtype }
	 * returns: ChemConnectPrimitiveDataStructure
	 * 
	 */
	public static String getPrimitiveStructureClass(String primitivetype) {
		String query = "SELECT ?subsubtype\n" + "	WHERE {\n" + "     " + primitivetype
				+ "  rdfs:subClassOf ?subsubtype\n" + "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String subtype = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			subtype = map.get("subsubtype");
		}
		return subtype;
	}

	/**
	 * Return the ID structure using the type identifier.
	 * 
	 * @param objid
	 *            The object identifer.
	 * @return The ID object represented by the object with the identifier given
	 * 
	 *         ?ref is the object and from the object the ID is found.
	 * 
	 *         SELECT ?obj ?ref ?id WHERE { ?ref
	 *         <http://purl.org/dc/terms/identifier> "dc:description"^^xsd:string .
	 *         ?id <http://purl.org/dc/terms/references> ?ref }
	 *         id=dataset:DescriptionID
	 * 
	 *         If this object does not have an ID structure, the null is returned:
	 *         SELECT ?obj ?ref ?id WHERE { ?ref
	 *         <http://purl.org/dc/terms/identifier>
	 *         "dataset:GPSLocation"^^xsd:string . ?id
	 *         <http://purl.org/dc/terms/references> ?ref } id=null
	 * 
	 */
	public static String id(String objid) {
		String query = "SELECT ?obj ?ref ?id\n" + "	WHERE {\n" + "	?ref <http://purl.org/dc/terms/identifier> \""
				+ objid + "\"^^xsd:string .\n" + "	?id <http://purl.org/dc/terms/references> ?ref\n" + "  }";
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
				+ "      ?sub owl:someValuesFrom|owl:onClass ?substructure }";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		for (Map<String, String> map : stringlst) {
			String substructure = map.get("substructure");
			subelements.add(substructure);
		}
		return subelements;
	}

	/**
	 * @param name
	 *            The element name
	 * @return The ChemConnect type
	 * 
	 *         dataset:ContactSiteOf ---> HTTPAddress
	 * 
	 */
	public static String getChemConnectDirectTypeHierarchy(String name) {
		String direct = "SELECT ?type\n" + "	WHERE {\n" + name + " <http://purl.org/dc/elements/1.1/type> ?type .\n"
				+ "	}";
		String objname = getHierarchString(direct);
		if (objname == null) {

			String sub = "SELECT ?object ?type\n" + "	WHERE {\n" + "     " + name + "  rdfs:subClassOf ?object .\n"
					+ "		?object <http://purl.org/dc/elements/1.1/type> ?type\n" + "	}";
			objname = getHierarchString(sub);
		}
		return objname;
	}

	/**
	 * @param query
	 *            query with the object
	 * @return The corresponding ChemConnect structure of the general type.
	 * 
	 *         ?object: The general type of object ?type: The corresponding
	 *         ChemConnect structure of the general type
	 * 
	 *         SELECT ?object ?type WHERE { dataset:ContactHasSite rdfs:subClassOf
	 *         ?object . ?object <http://purl.org/dc/elements/1.1/type> ?type }
	 * 
	 *         ### http://www.esblurock.info/dataset#ContactHasSite :ContactHasSite
	 *         rdf:type owl:Class ; rdfs:subClassOf :HTTPAddress .
	 * 
	 *         ### http://www.esblurock.info/dataset#HTTPAddress :HTTPAddress
	 *         rdf:type owl:Class ; rdfs:subClassOf
	 *         :ChemConnectPrimitiveDataStructure ;
	 *         <http://purl.org/dc/elements/1.1/type> "HTTPAddress"^^xsd:string ;
	 *         rdfs:isDefinedBy <http://purl.org/spar/fabio/hasURL> ; rdfs:label
	 *         "HTTP address data"^^xsd:string .
	 * 
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

	public static Set<String> SubObjectsOfDataStructure(String structurename) {
		Set<String> subelements = new HashSet<String>();
		SubObjectsOfDataStructure(structurename, subelements);
		Set<String> set = new HashSet<String>();
		for (String name : subelements) {
			String obj = getStructureFromDataStructure(name);
			if (obj == null)
				System.out.println("Empty:" + name);
			set.add(obj);
		}

		return set;
	}

	/**
	 * This returns the subobjects used by the input object.
	 * 
	 * @param structurename
	 *            The main object to search
	 * @param subelements
	 *            the subelements found are added to this list
	 * @return
	 * 
	 * 		This returns the subobjects used by the input object. From
	 *         dcat:record or dcterms:hasPart sub-elements, those which are
	 *         subclasses of ID (dataset:Identifier) are isolated and the referenced
	 *         type (dcterms:references) is given.
	 * 
	 *         What it does not return are links to object (LinkedTo) The routine
	 *         checks for dcat:record (from Catalog objects) and dcterms:hasPart
	 *         (from Dataset objects).
	 * 
	 *         SELECT ?sub ?type ?data WHERE { dataset:Organization rdfs:subClassOf
	 *         ?sub . { ?sub owl:onProperty <http://purl.org/dc/terms/hasPart> }
	 *         UNION { ?sub owl:onProperty dcat:record } ?sub
	 *         owl:someValuesFrom|owl:onClass ?object . ?object rdfs:subClassOf
	 *         dataset:Identifier . ?object <http://purl.org/dc/terms/references>
	 *         ?data }
	 * 
	 */
	public static void SubObjectsOfDataStructure(String structurename, Set<String> subelements) {
		if (!subelements.contains(structurename)) {
			subelements.add(structurename);
		}
		String query = "SELECT ?substructure\n" + "	WHERE {\n" + structurename + " rdfs:subClassOf ?sub .\n" + "{\n"
				+ "	?sub owl:onProperty <http://purl.org/dc/terms/hasPart> \n" + "	}\n" + "	UNION\n" + "	{\n"
				+ "	?sub owl:onProperty dcat:record\n" + "	}" + "      ?sub owl:someValuesFrom|owl:onClass ?object .\n"
				+ "      ?object rdfs:subClassOf dataset:Identifier .\n"
				+ "      ?object <http://purl.org/dc/terms/references> ?substructure .\n" + "}";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		HashSet<String> mylst = new HashSet<String>();
		for (Map<String, String> map : stringlst) {
			String substructure = map.get("substructure");
			if (!subelements.contains(substructure)) {
				mylst.add(substructure);
				subelements.add(substructure);
			}
		}
		for (String substructure : mylst) {
			SubObjectsOfDataStructure(substructure, subelements);
		}
	}

	public static List<DataElementInformation> elementsFromSimpleType(String type) {
		String query = " SELECT ?type\n" + "			WHERE {\n"
				+ "				?type <http://purl.org/dc/elements/1.1/type>  \"" + type + "\"^^xsd:string }\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		List<DataElementInformation> subelements = null;
		for (Map<String, String> map : stringlst) {
			String typeS = map.get("type");
			subelements = DatasetOntologyParsing.subElementsOfStructure(typeS);
		}
		return subelements;

	}

	/**
	 * @param objid
	 * @return public static String objectFromIdentifier(String objid) { String
	 *         query = "SELECT ?ref\n" + " WHERE {\n" + " ?ref
	 *         <http://purl.org/dc/terms/identifier> " + objid + " }";
	 * 
	 *         List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
	 *         List<Map<String, String>> stringlst =
	 *         OntologyBase.resultmapToStrings(lst); String id = null; if
	 *         (stringlst.size() > 0) { Map<String, String> map = stringlst.get(0);
	 *         id = (String) map.get("ref"); } return id;
	 * 
	 *         }
	 * 
	 */

	public static String getStructureFromDataStructure(String object) {
		String query = "SELECT ?ref\n" + "	WHERE {\n" 
				+ "	 " + object + " <http://purl.org/dc/elements/1.1/type> ?ref\n" 
				+ "  }";
		String structure = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if (stringlst.size() > 0) {
			structure = stringlst.get(0).get("ref");
		}
		System.out.println(structure);
		return structure;
	}

}
