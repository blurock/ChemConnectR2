package info.esblurock.reaction.ontology.utilities;

import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;


public class IsolateResultList {

	public static String subjectVariableS = "subject";
	
	/** Extracts list of string results from query, with name 'subject'
	 * 
	 * @param set results from query
	 * @return list of names as a result of the query
	 */
	public ArrayList<String> getListFromSubject(ResultSet set) {
		return getListFromSubjectInResultSet(subjectVariableS, set);
	}
	
/** Extracts list of string results from query, with given query name
 * 	
 * @param variableName result name used in query
 * @param set results from query
 * @return list of names as a result of the query
 */
	public ArrayList<String> getListFromSubjectInResultSet(String variableName, ResultSet set) {
		ArrayList<Resource> lst = getResourceListFromResultSet(variableName, set);
		ArrayList<String> results = new ArrayList<String>();
		for(Resource resource : lst) {
			results.add(resource.getLocalName());
        }
		return results;
	}
	/** Extracts list of resource results from query, with subject name
	 * 	
	 * @param set results from query
	 * @return list of resources as a result of the query
	 */
	public ArrayList<Resource> getResourceListFromResultSet(ResultSet set) {
		return getResourceListFromResultSet(subjectVariableS, set);
	}
	/** Extracts list of resource results from query, with given query name
	 * 	
	 * @param variableName result name used in query
	 * @param set results from query
	 * @return list of resources as a result of the query
	 */
		public ArrayList<Resource> getResourceListFromResultSet(String variableName, ResultSet set) {
			ArrayList<Resource> results = new ArrayList<Resource>();
	        while(set.hasNext()) {
	        	QuerySolution result = set.next();
	        	Iterator<String> iter = result.varNames();
	        	while(iter.hasNext()) {
	        		String name = iter.next();
	        		if(name.compareTo(variableName) == 0) {
	            		RDFNode node = result.get(name);
	            		if(node.isResource()) {
	            			Resource resource = node.asResource();
	            			results.add(resource);  
	            		} else if(node.isLiteral()) {
	            			System.out.println("Literal: " + node.asLiteral());
	            		}
	            		      			
	        		}
	        	}
	        }
			return results;
		}
}
