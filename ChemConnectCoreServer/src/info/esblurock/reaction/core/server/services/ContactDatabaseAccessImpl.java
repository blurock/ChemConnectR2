package info.esblurock.reaction.core.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.services.util.DatabaseObjectUtilities;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.BuildSubsystemInformation;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.ontology.units.OntologyUnits;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
public class ContactDatabaseAccessImpl  extends ServerBase implements ContactDatabaseAccess {
	
	public ArrayList<String> getListOfUsers() throws IOException {
		System.out.println("Users: ");
		List<DatabaseObject> users = QueryBase.getDatabaseObjects(UserAccount.class.getCanonicalName());
		System.out.println("Users: " + users.size());
		return DatabaseObjectUtilities.getListOfIdentifiers(users);
	}
	public ArrayList<String> getListOfOrganizations() throws IOException {
		List<DatabaseObject> orgs = QueryBase.getDatabaseObjects(Organization.class.getCanonicalName());
		System.out.println("Organizations: " + orgs.size());
		return DatabaseObjectUtilities.getListOfIdentifiers(orgs);
	}
	public HierarchyNode getCatalogHierarchy() {
		return DatasetOntologyParsing.getChemConnectDataStructureHierarchy();
	}
	public DatasetInformationFromOntology extractCatalogInformation(String identifier, String dataElementName) throws IOException {
		DatasetInformationFromOntology info = ExtractCatalogInformation.extract(identifier, dataElementName);
		System.out.println(info.toString());
		return info;
	}
	public SingleQueryResult getMainObjects(ClassificationInformation clsinfo) throws IOException {
		InterpretData interpret = InterpretData.valueOf(clsinfo.getDataType());
		String classname = interpret.canonicalClassName();
		System.out.println("getMainObjects: " + classname);
		QuerySetupBase query = new QuerySetupBase(classname);
		return standardQuery(query);
	}
	
	public ChemConnectCompoundDataStructure getChemConnectCompoundDataStructure(String dataElementName) {
		ChemConnectCompoundDataStructure substructures = null;
		substructures = DatasetOntologyParsing.subElementsOfStructure(dataElementName);
		return substructures;
	}

	public SingleQueryResult standardQuery(QuerySetupBase query) throws IOException {
		SingleQueryResult ans = null;
		try {
			ans = QueryBase.StandardQueryResult(query);
		} catch (ClassNotFoundException e) {
			throw new IOException("Query class not found: " + query.getQueryClass());
		}
		return ans;
	}
	public RecordInformation extractRecordElementsFromStructure(ClassificationInformation clsinfo,
			ChemConnectCompoundDataStructure subelements, DatabaseObject object) throws IOException {
		
		RecordInformation record = ExtractCatalogInformation.extractRecordElementsFromChemStructure(clsinfo,subelements,object);
		System.out.println(record.toString());
		
		return record;
	}
	
	public SetOfKeywordRDF subsystemInterconnections(String topnode, String access, String owner, String sourceID) {
		SetOfKeywordRDF rdfs = ConceptParsing.conceptHierarchyRDFs(topnode,access,owner,sourceID);
		System.out.println(rdfs);
		return rdfs;
	}
	
	public HierarchyNode hierarchyFromPrimitiveStructure(String structure) {
		String topconcept = ConceptParsing.definitionFromStructure(structure);
		return hierarchyOfConcepts(topconcept);
	}
	
	@Override
	public HierarchyNode hierarchyOfConcepts(String topnode) {
		HierarchyNode hierarchy = ConceptParsing.conceptHierarchy(topnode);
		return hierarchy;
	}	
	
	@Override
	public HierarchyNode hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel) {
		System.out.println("hierarchyOfConceptsWithLevelLimit" + topnode + ": " + maxlevel);
		HierarchyNode hierarchy = ConceptParsing.conceptHierarchy(topnode,maxlevel);
		System.out.println("hierarchyOfConceptsWithLevelLimit" + hierarchy);
		return hierarchy;
	}	

	public TotalSubsystemInformation buildSubSystem(String concept) {
		BuildSubsystemInformation build = new BuildSubsystemInformation(concept);
		return build.SubsystemInformation();
	}
	
	public SetOfUnitProperties unitProperties(String topunit) {
		return OntologyUnits.getSetOfUnitProperties(topunit);
	}
	
	public ArrayList<PrimitiveParameterValueInformation> getParameterInfo(ArrayList<String> parameternames) {
		System.out.println(parameternames);
		ArrayList<PrimitiveParameterValueInformation> parameters  = new ArrayList<PrimitiveParameterValueInformation>();
		for(String name : parameternames) {
			PrimitiveParameterValueInformation info = ConceptParsing.fillParameterInfo(name);
			parameters.add(info);
		}
		System.out.println(parameters);
		return parameters;
 	}
}
