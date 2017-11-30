package info.esblurock.reaction.ontology.units;

import java.util.ArrayList;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import info.esblurock.reaction.ontology.AlternativeEntryWithAppFiles;
import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.utilities.IsolateResultList;

public class OntologyUnits extends OntologyBase {

	public static ArrayList<Resource> getUnitSet(String unittype) {
		OntModel m = OntologyBase.Util.getUnitsOntology();
		String queryPrefix = OntologyBase.getStandardPrefixUnits();

		String queryString = queryPrefix + "SELECT  ?subject" + "\n"
				+ " WHERE {?subject ?p quant:ThermodynamicTemperature}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, m);
		ResultSet results = qe.execSelect();
		IsolateResultList isolate = new IsolateResultList();
		ArrayList<Resource> lst = isolate.getResourceListFromResultSet(results);
		qe.close();
		return lst;
	}

	public static UnitProperties UnitInformation(String unitname) {
		UnitProperties props = new UnitProperties();
		AlternativeEntryWithAppFiles alt = new AlternativeEntryWithAppFiles();
		OntModel m = OntologyBase.Util.getUnitsOntology();
		String queryPrefix = OntologyBase.getStandardPrefixUnits();
		String queryMass = queryPrefix + "SELECT ?predicate ?object" + "\n" + "	WHERE { " + alt.getQUDTUnitPrefix()
				+ unitname + " ?predicate ?object}";
		Query querymass = QueryFactory.create(queryMass);
		QueryExecution massqe = QueryExecutionFactory.create(querymass, m);
		ResultSet massresults = massqe.execSelect();
		while (massresults.hasNext()) {
			QuerySolution solution = massresults.next();
			RDFNode node = solution.get("object");
			RDFNode predicate = solution.get("predicate");
			if (node.isLiteral()) {

				Resource presource = predicate.asResource();
				System.out.println("\t" + presource.getLocalName() + "\t: " + node.asLiteral().getString());
				props.addProperty(presource.getLocalName(), node.asLiteral().getString());
			} else if (node.isResource()) {
				Resource oresource = node.asResource();
				if (predicate.isResource()) {
					Resource presource = predicate.asResource();
					if (oresource.getLocalName() != null) {
						String p = presource.getLocalName();
						String v = oresource.getLocalName();
						System.out.println("\t" + p + "\t: " + v);
						if(p.compareTo("type") == 0) {
							props.addType(v);
						} else {
							props.addProperty(p, v);
						}
					}
				}

			}
		}
		return props;
	}

}
