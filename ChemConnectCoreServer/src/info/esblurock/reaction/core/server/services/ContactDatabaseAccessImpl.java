package info.esblurock.reaction.core.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.services.util.DatabaseObjectUtilities;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

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
	public ArrayList<ClassificationInformation> getCatalogClassificationInformation() {
		return DatasetOntologyParsing.getCatalogClassificationInformation();
	}
	public DatasetInformationFromOntology extractCatalogInformation(String identifier, String dataElementName) throws IOException {
		DatasetInformationFromOntology info = ExtractCatalogInformation.extract(identifier, dataElementName);
		System.out.println(info.toString());
		return info;
	}
}
