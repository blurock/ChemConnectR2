package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;


public interface ContactDatabaseAccessAsync {

	void getListOfUsers(AsyncCallback<ArrayList<String>> callback);

	void getListOfOrganizations(AsyncCallback<ArrayList<String>> callback);

	void extractCatalogInformation(String identifier, String dataElementName,
			AsyncCallback<DatasetInformationFromOntology> callback);

	void getCatalogClassificationInformation(AsyncCallback<ArrayList<ClassificationInformation>> callback);

	void standardQuery(QuerySetupBase query, AsyncCallback<SingleQueryResult> callback);

}
