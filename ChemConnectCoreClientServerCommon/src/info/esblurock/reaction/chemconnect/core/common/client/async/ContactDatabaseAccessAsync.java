package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
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


public interface ContactDatabaseAccessAsync {

	void getListOfUsers(AsyncCallback<ArrayList<String>> callback);

	void getListOfOrganizations(AsyncCallback<ArrayList<String>> callback);

	void extractCatalogInformation(String identifier, String dataElementName,
			AsyncCallback<DatasetInformationFromOntology> callback);

	void getCatalogHierarchy(AsyncCallback<HierarchyNode> callback);

	void standardQuery(QuerySetupBase query, AsyncCallback<SingleQueryResult> callback);

	void getMainObjects(ClassificationInformation clsinfo, AsyncCallback<SingleQueryResult> callback);

	void getChemConnectCompoundDataStructure(String dataElementName, AsyncCallback<ChemConnectCompoundDataStructure> callback);

	void extractRecordElementsFromStructure(ClassificationInformation clsinfo, 
			ChemConnectCompoundDataStructure subelements,
			DatabaseObject object, 
			AsyncCallback<RecordInformation> callback);

	void hierarchyOfConcepts(String topnode, AsyncCallback<HierarchyNode> callback);

	void subsystemInterconnections(String topnode, String access, String owner, String sourceID,
			AsyncCallback<SetOfKeywordRDF> callback);

	void hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel, AsyncCallback<HierarchyNode> callback);

	void buildSubSystem(String concept, AsyncCallback<TotalSubsystemInformation> callback);

	void unitProperties(String topunit, AsyncCallback<SetOfUnitProperties> callback);

	void getParameterInfo(ArrayList<String> parameternames,
			AsyncCallback<ArrayList<PrimitiveParameterValueInformation>> callback);

	void hierarchyFromPrimitiveStructure(String structure, AsyncCallback<HierarchyNode> callback);

	void getSetOfObservationsInformation(String observations, AsyncCallback<SetOfObservationsTransfer> callback);

	void interpretSpreadSheet(SpreadSheetInputInformation input, AsyncCallback<ObservationsFromSpreadSheet> callback);

}
