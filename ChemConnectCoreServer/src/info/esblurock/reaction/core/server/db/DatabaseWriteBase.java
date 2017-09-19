package info.esblurock.reaction.core.server.db;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class DatabaseWriteBase {
	public static void writeDatabaseObject(DatabaseObject object) {
		ObjectifyService.ofy().save().entity(object).now();
	}
	public static void writeListOfDatabaseObjects(List<DatabaseObject> lst) {
		ObjectifyService.ofy().save().entities(lst);
	}
	public static void writeEntity(Object entity) {
		ObjectifyService.ofy().save().entity(entity).now();
	}
	/** This writes the transaction object and the TransactionInfo.
	 * @param id The keyword id of the transaction
	 * @param access access of the objects in the transaction
	 * @param owner the owner of the transaction
	 * @param sourceID the sourceID of the transaction
	 * @param object The associated information object associated with the transaction.
	 * 
	 * This writes the transaction object and the TransactionInfo.
	 * The TransactionInfo uses the base parameters (id,access,owner and sourceID) of the object.
	 * 
	 */
	static public void writeObjectWithTransaction(DatabaseObject object) {
		String classname = object.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(
				object.getIdentifier(), object.getAccess(), object.getOwner(),object.getSourceID(),classname);
		writeDatabaseObject(object);
		transaction.setStoredObjectKey(object.getKey());
		writeDatabaseObject(transaction);
	}
	static public void initializeIndividualInformation(String username, String password, String email, String userrole) {

		
		String sourceID = QueryBase.getDataSourceIdentification(username);
		UserAccountInformation account = new UserAccountInformation(username,username,username,sourceID,
				null,password,userrole,email);
		DatabaseWriteBase.writeDatabaseObject(account);

		GPSLocation gps = new GPSLocation(username,sourceID);
		ContactLocationInformation location = new ContactLocationInformation(username,sourceID,gps.getIdentifier());

		ContactInfoData contactinfo = new ContactInfoData(username,sourceID);
		DescriptionDataData data = new DescriptionDataData(username,sourceID);

		NameOfPerson name = new NameOfPerson(username,sourceID);
		PersonalDescription personal = new PersonalDescription(username,username,username,sourceID,userrole,name);

		IndividualInformation individual = new IndividualInformation(username,username,username,sourceID,
				data.getIdentifier(),
				contactinfo.getIdentifier(), location.getIdentifier(),
				personal.getIdentifier(), "");
		
		DatabaseWriteBase.writeDatabaseObject(gps);
		DatabaseWriteBase.writeDatabaseObject(name);
		DatabaseWriteBase.writeDatabaseObject(contactinfo);
		DatabaseWriteBase.writeDatabaseObject(location);
		DatabaseWriteBase.writeDatabaseObject(data);
		DatabaseWriteBase.writeDatabaseObject(personal);
		DatabaseWriteBase.writeDatabaseObject(individual);
	}
}
