package info.esblurock.reaction.core.server.authentification;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.OntologyKeys;

public class LoginAuthorization {

	public static DatabaseObjectHierarchy findOrCreateIndividual(String username, String organization, 
			String shortorganization, String userrole) {
		DatabaseObjectHierarchy hierarchy = null;
		try {
			IndividualInformation person = (IndividualInformation) QueryBase
					.getFirstDatabaseObjectsFromSingleProperty(IndividualInformation.class.getCanonicalName(),
							"owner", username);
			ArrayList<DatabaseObjectHierarchy> lst 
				= WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(username, 
						OntologyKeys.useraccount);
			if(lst.size() > 0) {
				hierarchy = lst.get(0);
			}
			
		} catch (IOException ex) {
			String sourceID = QueryBase.getDataSourceIdentification(username);
			String access   = username;
			String owner    = username;
			String orgname  = shortorganization;
			String title    = organization;
			CreateDefaultObjectsFactory.createAndWriteDefaultUserOrgAndCatagories(username, userrole, 
					access, owner, orgname, title, sourceID);
			
			System.out.println("Write UserAccountInformation");
			DatabaseObject obj = new DatabaseObject(username,access,owner,sourceID);
			hierarchy = InterpretData.UserAccountInformation.createEmptyObject(obj);
			UserAccountInformation account = (UserAccountInformation) hierarchy.getObject();
			account.setUserrole(userrole);
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);
		}
		return hierarchy;
	}
}
