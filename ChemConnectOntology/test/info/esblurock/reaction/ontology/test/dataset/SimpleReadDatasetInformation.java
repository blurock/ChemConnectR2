package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.dataset.DataElementInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;


public class SimpleReadDatasetInformation {

	@Test
	public void test() {
		System.out.println("Database Test 1");
		OntModel model = OntologyBase.Util.getDatabaseOntology();
		System.out.println("Database Test 2");
		String queryS1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
				"PREFIX dataset: <http://www.esblurock.info/dataset#>\n" + 
				"SELECT ?object ?subobject ?card \n" + 
				"	WHERE {?object rdfs:subClassOf dataset:ChemConnectDataStructure . " 
				+ "          ?sub owl:onClass ?subobject . "
				+ "		?sub owl:maxQualifiedCardinality ?card"
				+ "}";
		/*
				+ " .\n" + 
				"		?object rdfs:subClassOf ?sub .\n" + 
				"		?sub owl:allValuesFrom ?subobject .\n" + 
				"		?subobject rdfs:subClassOf ?xxx .\n" + 
				"		?xxx owl:onProperty ?vvv .\n" + 
				"		?xxx ?pred dataset:ContactID .\n" + 
				"		?xxx owl:qualifiedCardinality ?uuu" +
				*/
	
		Query query = QueryFactory.create(queryS1);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		
		List<String> lst = DatasetOntologyParsing.getMainDataStructures();
		for(String structure : lst) {
			System.out.println("Data Structure: '" + structure + "'");
			List<DataElementInformation> subs = DatasetOntologyParsing.getSubElementsOfStructure(structure);
			for(DataElementInformation element : subs) {
				System.out.println("\tSubstructure: '" + element + "'");
				List<DataElementInformation> subelements = DatasetOntologyParsing.getSubElementsOfStructure(element.getDataElementName());
				for(DataElementInformation info : subelements) {
					System.out.println("\t\tElement: " + info.toString());
					ClassificationInformation classid = DatasetOntologyParsing.getIdentificationInformation(null,info.getDataElementName());
					if(classid != null) {
						System.out.println("\t\t\tClassificationID: " + classid.toString());
					}
				}
			}
		}
	}

}
