package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.chemconnect.core.data.transfer.ListOfDataElementInformation;

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
	ArrayList<ClassificationInformation> getCatalogClassificationInformation();
	DatasetInformationFromOntology extractCatalogInformation(String identifier, String dataElementName) throws IOException;
	SingleQueryResult standardQuery(QuerySetupBase query) throws IOException;
	SingleQueryResult getMainObjects(ClassificationInformation clsinfo) throws IOException;
	ListOfDataElementInformation getSubElementsOfStructure(String dataElementName);
}
