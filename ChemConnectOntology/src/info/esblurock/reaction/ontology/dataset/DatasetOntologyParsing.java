package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import info.esblurock.reaction.ontology.OntologyBase;

public class DatasetOntologyParsing {

	public static List<String> getMainDataStructures() {
		String query = "SELECT ?object \n" + 
				"	WHERE {"
				+ "?object rdfs:subClassOf dataset:ChemConnectDataStructure "
				+ "}";
		
		List< Map<String,RDFNode> > lst = OntologyBase.resultSetToMap(query);
		List< Map<String,String> > stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> structurelst = OntologyBase.isolateProperty("object", stringlst);
		// 
		structurelst.remove("dataset:ChemConnectDataStructure");
		return structurelst;
	}
	
	public static List<String> getSubElementsOfStructure(String structure) {
		String query = "SELECT ?sub  ?pred ?substructure ?card\n" + 
				"	WHERE {\n"
				+ structure + " rdfs:subClassOf ?sub .\n" + 
				"		?sub owl:onProperty dcat:record .\n" + 
				"		?sub owl:someValuesFrom|owl:allValuesFrom ?substructure .\n" + 
				"		?sub ?pred ?substructure"
				+ "}";
		
		System.out.println(query);
		
		
		List< Map<String,RDFNode> > lst = OntologyBase.resultSetToMap(query);
		System.out.println(lst);
		List< Map<String,String> > stringlst = OntologyBase.resultmapToStrings(lst);
		System.out.println(stringlst);
		List<String> structurelst = OntologyBase.isolateProperty("value", stringlst);
		List<String> cardlst = OntologyBase.isolateProperty("card", stringlst);
		
		return structurelst;
	}
}
