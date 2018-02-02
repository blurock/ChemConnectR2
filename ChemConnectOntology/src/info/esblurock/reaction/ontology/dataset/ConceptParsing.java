package info.esblurock.reaction.ontology.dataset;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.concepts.AttributesOfObject;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubSystemConceptLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;
import info.esblurock.reaction.ontology.OntologyBase;

public class ConceptParsing {

	
	static class AttributeDescriptionComparator implements Comparator<AttributeDescription> {

		@Override
		public int compare(AttributeDescription o1, AttributeDescription o2) {
			return o1.getAttributeName().compareTo(o2.getAttributeName());
		}
		
	}

	/**
	 * 
	 * This finds the annotation associated with the parameter [ rdf:type owl:Axiom
	 * ; owl:annotatedSource :HeatFluxBurner ; owl:annotatedProperty rdfs:subClassOf
	 * ; owl:annotatedTarget [ rdf:type owl:Restriction ; owl:onProperty
	 * <http://purl.org/linked-data/cube#attribute> ; owl:qualifiedCardinality
	 * "1"^^xsd:nonNegativeInteger ; owl:onClass :BurnerPlateDiameter ] ;
	 * rdfs:isDefinedBy :HeatFluxBurnerBurnerPlate ]
	 * 
	 * @param topclassS
	 *            The class to find the attributes to
	 * @return The attributes organized into relative to their originating
	 *         subsystems
	 */
	public static AttributesOfObject parseAttributes(String topclassS) {
		String query = "SELECT ?subsystem ?sub ?pobj\n" + "	WHERE { \n" + "	" + topclassS
				+ "  rdfs:subClassOf ?pobj .\n"
				+ "	?pobj owl:onProperty <http://purl.org/linked-data/cube#attribute> .\n"
				+ "	?pobj owl:onClass ?sub .\n" + "	?subject owl:annotatedSource ?obj .\n"
				+ "	?subject rdfs:isDefinedBy ?subsystem .\n" + "	?subject owl:annotatedTarget ?obj2 .\n"
				+ "	?obj2 owl:onClass ?sub\n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		AttributesOfObject attributes = new AttributesOfObject(topclassS);
		for (Map<String, String> map : stringlst) {
			String subsystem = map.get("subsystem");
			String attribute = map.get("sub");
			attributes.addAttribute(subsystem, attribute);
		}
		return attributes;
	}

	/**
	 * 
	 * ### http://www.esblurock.info/dataset#HeatFluxBurner :HeatFluxBurner rdf:type
	 * owl:Class ; rdfs:subClassOf :CombustionInstrument , [ rdf:type
	 * owl:Restriction ; owl:onProperty <http://www.w3.org/ns/ssn/hasSubSystem> ;
	 * owl:qualifiedCardinality "1"^^xsd:nonNegativeInteger ; owl:onClass
	 * :HeatFluxBurnerFuelPreparation ]
	 * 
	 * @param topclassS
	 * @return
	 */
	public static Set<String> immediateComponents(String topclassS) {
		Set<String>  components = immediateLinks(topclassS, "dataset:hasComponent");
		return components;
	}
	/**
	 * 
	 * @param topclassS
	 * @return
	 */
	public static Set<String> immediateSubSystems(String topclassS) {
		return immediateLinks(topclassS,"<http://www.w3.org/ns/ssn/hasSubSystem>");
	}
	/**
	 * 
	 * ### http://www.esblurock.info/dataset#HeatFluxBurner :HeatFluxBurner rdf:type
	 * owl:Class ; rdfs:subClassOf :CombustionInstrument , [ rdf:type
	 * owl:Restriction ; owl:onProperty <http://www.w3.org/ns/ssn/hasSubSystem> ;
	 * owl:qualifiedCardinality "1"^^xsd:nonNegativeInteger ; owl:onClass
	 * :HeatFluxBurnerFuelPreparation ]
	 * 
	 * @param topclassS
	 * @return
	 */
	public static Set<String> immediateLinks(String topclassS, String linktype) {
		String query = "SELECT ?identifier ?sub ?target \n" + 
				"	WHERE {\n" + 
				"             ?identifier  owl:annotatedSource " + topclassS + " .\n" + 
				"             ?identifier owl:annotatedTarget ?sub .\n" + 
				"             ?identifier <http://purl.org/dc/elements/1.1/type> " + linktype + " .\n" + 
				"             ?sub owl:onClass  ?target\n" + 
				"          }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		TreeSet<String> subsystems = new TreeSet<String>();
		for (Map<String, String> map : stringlst) {
			String subsystem = map.get("target");
			subsystems.add(subsystem);
		}
		return subsystems;
	}

	public static Set<SubSystemConceptLink> immediateLinks(String topclassS) {
		String query = "SELECT ?target ?type\n" + 
				"	WHERE {\n" + 
				"             ?identifier  owl:annotatedSource " + topclassS + " .\n" + 
				"             ?identifier owl:annotatedTarget ?sub .\n" + 
				"             ?identifier <http://purl.org/dc/elements/1.1/type> ?type .\n" + 
				"             ?sub owl:onClass  ?target\n" + 
				"          }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		HashSet<SubSystemConceptLink> links = new HashSet<SubSystemConceptLink>();
		for (Map<String, String> map : stringlst) {
			String target = map.get("target");
			String type = map.get("type");
			SubSystemConceptLink link = new SubSystemConceptLink(type,target);
			links.add(link);
		}
		return links;
		
	}
	/** Find all the subsystems and components for a concept class
	 * 
	 * Finds the immediate set of subsystems and components and
	 * then finds all their subsystems and components
	 * 
	 * @param topclassS
	 * @return total set of components and subsystems down the hierarchy
	 */
	public static Set<String> subSystemsAndComponents(String topclassS) {
		Set<String> subsystems = immediateSubSystems(topclassS);
		Set<String> components = immediateComponents(topclassS);
		TreeSet<String> total = new TreeSet<String>(subsystems);
		total.addAll(components);
		for (String subsystem : subsystems) {
			Set<String> sub = subSystemsAndComponents(subsystem);
			total.addAll(sub);
		}
		return total;
	}

	public static SetOfKeywordRDF conceptHierarchyRDFs(String topnode, String access, String owner, String sourceID) {
		SetOfKeywordRDF connections = new SetOfKeywordRDF();
		String subclassS = "rdf:subClassOf";
		String conceptS = "concept";
		String query = "SELECT ?subsystem { ?subsystem <" + ReasonerVocabulary.directSubClassOf + "> " + topnode + " .\n"
				+ "FILTER (?subsystem != " + topnode + ") .\n"
				+ "}";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		
		for(Map<String, String> map : stringlst) {
			String subsystem = map.get("subsystem");
				KeywordRDF rdf = new KeywordRDF(subsystem, access, owner, sourceID, subclassS, topnode, conceptS);
				connections.add(rdf);
				SetOfKeywordRDF subset = conceptHierarchyRDFs(subsystem, access, owner, sourceID);
				connections.addAll(subset);
		}
		return connections;
	}
	public static HierarchyNode conceptHierarchy(String topnode) {
		return conceptHierarchy(topnode,-1);
	}

	public static HierarchyNode conceptHierarchy(String topnode, int maxlevel) {
		HierarchyNode node = new HierarchyNode(topnode);
		
		String query = "SELECT ?subsystem { ?subsystem <" + ReasonerVocabulary.directSubClassOf + "> " + topnode + " .\n"
				+ "FILTER (?subsystem != " + topnode + ") .\n"
				+ "}";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if(maxlevel > 0 || maxlevel < 0) {
			--maxlevel;
		for(Map<String, String> map : stringlst) {
			String subsystem = map.get("subsystem");
				HierarchyNode subset = conceptHierarchy(subsystem,maxlevel);
				node.addSubNode(subset);
			}
		}
		return node;
	}
	

	/** This lists the concepts which have the named input property
	 * @param property The property name
	 * @return The set of concepts devices/subsystem/components where property is being used
	 */
	public static Set<String> conceptUseOfProperty(String property) {
		String query = "SELECT ?subsystem\n" + 
				"       WHERE { ?subsystem rdfs:subClassOf ?obj .\n" + 
				"	            ?obj owl:onClass " + property + "\n" + 
				"         }";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		
		TreeSet<String> set = new TreeSet<String>();
		for(Map<String, String> map : stringlst) {
			String subsystem = map.get("subsystem");
			set.add(subsystem);
		}
		return set;
	}
	/** This lists the attributes (and the originating subsystem) of a concept subsystem
	 * 
	 * The default subsystem is the concept itself
	 * If the attribute has a reference to another subsystem, then that subsystem is taken.
	 * 
	 * @param concept The concept with the attributes
	 * @return The set of attributes
	 */
	public static Set<AttributeDescription> attributesInConcept(String concept) {
		String query = "SELECT ?propertyname\n" + 
				" WHERE { " + concept + " rdfs:subClassOf  ?obj .\n" + 
				"              ?obj  owl:onProperty <http://purl.org/linked-data/cube#attribute> .\n" + 
				"              ?obj owl:onClass ?propertyname\n" + 
				"         }";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		
		AttributesOfObject attset = parseAttributes(concept);
		Map<String, Set<String>> subsystemsOfAttributes = attset.getSubsystemsOfAttributes();
		
		TreeSet<AttributeDescription> set = new TreeSet<AttributeDescription>(new AttributeDescriptionComparator());
		for(Map<String, String> map : stringlst) {
			String attribute = map.get("propertyname");
			TreeSet<String> subsystem = new TreeSet<String>();
			subsystem.add(concept);
			Set<String> subs = subsystemsOfAttributes.get(attribute);
			if(subs != null) {
				if(subs.size() > 0) {
					subsystem.addAll(subs);
				}
			}
			AttributeDescription descr = new AttributeDescription(attribute, subsystem);
			set.add(descr);
		}
		return set;
	}
	
	/** total set of attributes
	 * 
	 * All the attributes of the system (concept), including those from its subsystems and components, are listed.
	 * Each attribute is listed with the subystem from which it originates 
	 * (For each attribute, this is stored in AttributeDescription)
	 * 
	 * attributesInConcept is called for each subsystem and the sets are merged.
	 * 
	 * @param concept
	 * @return
	 */
	public static Set<AttributeDescription> totalSetOfAttributesInConcept(String concept) {
		AttributesOfObject total = new AttributesOfObject(concept);
		Set<String> subsystems = subSystemsAndComponents(concept);
		subsystems.add(concept);
		for(String subsystem : subsystems) {
			Set<AttributeDescription> descrset = attributesInConcept(subsystem);
			for(AttributeDescription descr : descrset ) {
				for(String sub : descr.getSubsystems()) {
					total.addAttribute(sub, descr.getAttributeName());
				}
			}
		}
		Set<AttributeDescription> set = new TreeSet<AttributeDescription>(new AttributeDescriptionComparator());
		return total.extractAttributeDescription(set);
	}

	public static SubsystemInformation getSubsystemInformation(String concept) {
		
		
		
		return null;
	}
}
