package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;

public class HierarchyTest {

	@Test
	public void test() {
		HierarchyNode hierarchy1 = DatasetOntologyParsing.getChemConnectDataStructureHierarchy();
		System.out.println("getChemConnectDataStructureHierarchy()");
		System.out.println(hierarchy1);
		
		HierarchyNode hierarchy2 = DatasetOntologyParsing.findClassHierarchy("dataset:ChemConnectPrimitiveDataStructure");
		System.out.println("findClassHierarchy(\"dataset:ChemConnectPrimitiveDataStructure\")");
		System.out.println(hierarchy2);
		
		List<String> subclasses1 = DatasetOntologyParsing.getAllSubClasses("dataset:Classification");
		System.out.println("getAllSubClasses(\"dataset:Classification\");");
		System.out.println(subclasses1);
		
		List<String> subclasses2 = DatasetOntologyParsing.getSubClasses("dataset:ChemConnectPrimitiveDataStructure");
		System.out.println("getSubClasses(\"dataset:ChemConnectPrimitiveDataStructure\");");
		System.out.println(subclasses2);
		
	}

}
