package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;

@RemoteServiceRelativePath("contactservice")
public interface ContactDatabaseAccess extends RemoteService {
	   public static class Util
	   {
	       private static ContactDatabaseAccessAsync instance;

	       public static ContactDatabaseAccessAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(ContactDatabaseAccess.class);
	           }
	           return instance;
	       }
	   }
	ArrayList<String> getListOfUsers() throws IOException;
	ArrayList<String> getListOfOrganizations() throws IOException;
	HierarchyNode getCatalogHierarchy();
	DatasetInformationFromOntology extractCatalogInformation(String identifier, String dataElementName) throws IOException;
	SingleQueryResult standardQuery(QuerySetupBase query) throws IOException;
	SingleQueryResult getMainObjects(ClassificationInformation clsinfo) throws IOException;
	ChemConnectCompoundDataStructure getChemConnectCompoundDataStructure(String dataElementName);
	RecordInformation extractRecordElementsFromStructure(ClassificationInformation clsinfo,
			ChemConnectCompoundDataStructure subelements, 
			DatabaseObject object) throws IOException;
	SetOfKeywordRDF subsystemInterconnections(String topnode, String access, String owner, String sourceID);
	HierarchyNode hierarchyOfConcepts(String topnode);
	HierarchyNode hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel);
}
