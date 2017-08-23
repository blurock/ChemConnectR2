package info.esblurock.reaction.ontology.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;

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
	
	public static List<DataElementInformation> getSubElementsOfStructure(String structure) {
		String query = "SELECT ?sub  ?pred ?substructure ?card\n" + 
				"	WHERE {\n"
				+ structure + " rdfs:subClassOf ?sub .\n" + 
				"		{{  ?sub owl:onClass ?substructure  . "
				+ "         ?sub owl:qualifiedCardinality ?card }\n" + 
				"		UNION\n" + 
				"		{   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}} .\n" + 
				"		?sub ?pred ?substructure"
				+ "}";
		
		//System.out.println(query);
		
		
		List< Map<String,RDFNode> > lst = OntologyBase.resultSetToMap(query);
		List< Map<String,String> > stringlst = OntologyBase.resultmapToStrings(lst);
		List<DataElementInformation> info = new ArrayList<DataElementInformation>();
		for(Map<String,String> map : stringlst) {
			String substructure = map.get("substructure");
			String pred = map.get("pred");
			boolean singlet = true;
			int numberOfElements = 1;
			String numS = map.get("card");
			if(numS != null)
				numberOfElements = Integer.parseInt(numS);
			else 
				numberOfElements = 0;
			if(pred.compareTo("owl:someValuesFrom") == 0) {
				singlet = false;
			} else if(pred.compareTo("owl:onClass") == 0) {
				singlet = false;
				if(numberOfElements == 1)
					singlet = true;
			}
			DataElementInformation element = new DataElementInformation(substructure, singlet,numberOfElements);
			info.add(element);
		}
		return info;
	}
}
