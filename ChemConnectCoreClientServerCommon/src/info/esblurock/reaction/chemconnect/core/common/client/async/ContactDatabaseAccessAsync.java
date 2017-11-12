package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;


public interface ContactDatabaseAccessAsync {

	void getListOfUsers(AsyncCallback<ArrayList<String>> callback);

	void getListOfOrganizations(AsyncCallback<ArrayList<String>> callback);

	void extractCatalogInformation(String identifier, String dataElementName,
			AsyncCallback<DatasetInformationFromOntology> callback);

	void getCatalogClassificationInformation(AsyncCallback<ArrayList<ClassificationInformation>> callback);

	void standardQuery(QuerySetupBase query, AsyncCallback<SingleQueryResult> callback);

	void getMainObjects(ClassificationInformation clsinfo, AsyncCallback<SingleQueryResult> callback);

	void getSubElementsOfStructure(String dataElementName, AsyncCallback<ChemConnectCompoundDataStructure> callback);

	void extractRecordElementsFromStructure(ClassificationInformation clsinfo, 
			ChemConnectCompoundDataStructure subelements,
			DatabaseObject object, 
			AsyncCallback<RecordInformation> callback);

}
