package info.esblurock.reaction.ontology.concepts;

import java.util.Set;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.concepts.AttributesOfObject;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ParseAttributes {

	@Test
	public void test() {
				
		AttributesOfObject attributes = ConceptParsing.parseAttributes("dataset:HeatFluxBurner");
		System.out.println("Attributes            -------------------------------------------------------------");
		System.out.println(attributes.toString());
		
		Set<String> immed = ConceptParsing.immediateSubSystems("dataset:HeatFluxBurner");
		System.out.println("Immediate Subsystems  -------------------------------------------------------------");
		System.out.println("dataset:HeatFluxBurner\n" + immed);

		Set<String> immedcomp = ConceptParsing.immediateComponents("dataset:GasMassFlowControllerComponent");
		System.out.println("Immediate Components  -------------------------------------------------------------");
		System.out.println("dataset:GasMassFlowControllerComponent\n" + immedcomp);

		Set<String> lst = ConceptParsing.subSystemsAndComponents("dataset:HeatFluxBurner");
		System.out.println("Subsystem             -------------------------------------------------------------");
		System.out.println("dataset:HeatFluxBurner\n" + lst);
		
		
		System.out.println("Concept Hierarchy RDFS-------------------------------------------------------------");
		String subsystem = "dataset:InstrumentConcept";
		String access = "Public";
		String owner = "Administration";
		String sourceID = "1";
		SetOfKeywordRDF connections = ConceptParsing.conceptHierarchyRDFs(subsystem,access,owner,sourceID);
		System.out.println(connections.toString());
		
		subsystem = "dataset:ChemConnectDomainConcept";
		System.out.println("Concepts Hierarchy  all  -------------------------------------------------------------");
		HierarchyNode node = ConceptParsing.conceptHierarchy(subsystem);
		System.out.println(node.toString());
		System.out.println("Concepts Hierarchy   1   -------------------------------------------------------------");
		HierarchyNode node1 = ConceptParsing.conceptHierarchy(subsystem,1);
		System.out.println(node1.toString());
		System.out.println("Concepts Hierarchy    2  -------------------------------------------------------------");
		HierarchyNode node2 = ConceptParsing.conceptHierarchy(subsystem,2);
		System.out.println(node2.toString());
		
		System.out.println("Attributes            -------------------------------------------------------------");
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept("dataset:HeatFluxBurner");
		for(AttributeDescription descr : attrs) {
			System.out.println(descr);
		}

		System.out.println("TotalAttributes       -------------------------------------------------------------");
		Set<AttributeDescription> totalattr = ConceptParsing.totalSetOfAttributesInConcept("dataset:HeatFluxBurner");
		for(AttributeDescription descr : totalattr) {
			System.out.println(descr);
		}

	
	}

}
