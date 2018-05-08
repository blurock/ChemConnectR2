package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

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
	TotalSubsystemInformation buildSubSystem(String concept);
	SetOfUnitProperties unitProperties(String topunit);
	ArrayList<PrimitiveParameterValueInformation> getParameterInfo(ArrayList<String> parameternames);
	HierarchyNode hierarchyFromPrimitiveStructure(String structure);
	SetOfObservationsTransfer getSetOfObservationsInformation(String observations);
	ChemConnectDataStructure getSetOfObservationsStructructure();
	DatabaseObject getBaseUserDatabaseObject();
	GPSLocation getGPSLocation(DatabaseObject obj, String city, String country) throws IOException;
	ChemConnectDataStructure getChemConnectDataStructure(String identifier, String structureS);
	Map<String,DatabaseObject> getElementsOfCatalogObject(String identifier, String dataElementName);
}
