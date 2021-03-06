package info.esblurock.reaction.core.server.db.extract;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubSystemConceptLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubSystemParameters;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class BuildSubsystemInformation {
	
	public static String hasOutput = "ssn:hasOutput";
	TotalSubsystemInformation total;

	public BuildSubsystemInformation(String topid, String structureS) {
		total = new TotalSubsystemInformation(structureS);
		HierarchyNode info = buildHierarchy(structureS,total.getSubsystemsandcomponents(), total.getAttributesubsystemMap());
		total.setSubsystemtree(info);
		ChemConnectDataStructure struct = BuildFromCatalogInformation.getChemConnectDataStructure(topid,
				MetaDataKeywords.subSystemDescription);
		System.out.println("BuildSubsystemInformation:  " + struct.toString());
		total.setInfoStructure(struct);
		System.out.println(total.toString());
	}

	private HierarchyNode buildHierarchy(String concept, 
			Map<String,SubsystemInformation> set,
			Map<String,String> attributeset) {
		HierarchyNode node = new HierarchyNode(concept);
		Set<String> subsystems = ConceptParsing.immediateSubSystems(concept);
		Set<String> components = ConceptParsing.immediateComponents(concept);
		SubSystemParameters parameters = buildAttributes(concept);
		Set<SubSystemConceptLink> links = ConceptParsing.immediateLinks(concept);
		Set<SetOfObservationsInformation> observations = getObservations(links);
		SubsystemInformation info = new SubsystemInformation(concept,components, subsystems, links, parameters,null,observations);
		for(String attribute : parameters.getDirect()) {
			attributeset.put(attribute, concept);
		}
		set.put(concept,info);
		for(String sub : subsystems) {
			HierarchyNode subnode = buildHierarchy(sub, set, attributeset);
			node.addSubNode(subnode);
		}
		for(String sub : components) {
			HierarchyNode subnode = buildHierarchy(sub, set, attributeset);
			node.addSubNode(subnode);			
		}
		return node;
	}

	
	
	private Set<SetOfObservationsInformation> getObservations(Set<SubSystemConceptLink> links) {
		Set<SetOfObservationsInformation> observations = new HashSet<SetOfObservationsInformation>();
		for(SubSystemConceptLink link : links) {
			if(link.getLinkConcept().compareTo(hasOutput) == 0) {
				SetOfObservationsInformation obs = ConceptParsing.fillSetOfObservations(link.getTargetConcept());
				observations.add(obs);
			}
		}
		return observations;
	}

	private SubSystemParameters buildAttributes(String concept) {
		SubSystemParameters parameters = new SubSystemParameters();
		Set<AttributeDescription> set = ConceptParsing.totalSetOfAttributesInConcept(concept);
		String subsystem = concept;
		for (AttributeDescription description : set) {
			Set<String> subsystems = description.getSubsystems();
			String attribute = description.getAttributeName();
			boolean inherited = true;
			for (String sub : subsystems) {
				if (subsystem.matches(sub)) {
					inherited = false;
				}
			}
			if (inherited) {
				parameters.addRestInherited(attribute);
			} else {
				if (subsystems.size() == 1) {
					parameters.addDirect(attribute);
				} else {
					parameters.addListedInherited(attribute);
				}
			}
		}
		return parameters;
	}

	public TotalSubsystemInformation SubsystemInformation() {
		return total;
	}

	public void setUser(String name) {
		total.setUserName(name);
	}
	public void setSourceID(String sourceID) {
		total.setSourceID(sourceID);
	}
	
	
}
