package info.esblurock.reaction.core.server.db;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.delete.DeleteDataStructures;
import info.esblurock.reaction.ontology.DatasetOntologyParsing;
import info.esblurock.reaction.ontology.QueryBase;

import java.io.IOException;
import java.util.ArrayList;
/*
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

*/
import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class DatabaseWriteBase {
	public static void writeDatabaseObject(DatabaseObject object) {
				ObjectifyService.ofy().save().entity(object).now();
	}
	public static void writeListOfDatabaseObjects(ArrayList<UploadedImage> images) {
		ObjectifyService.ofy().save().entities(images);
	}
	public static void writeListOfDatabaseObjects(List<DatabaseObject> lst) {
		ObjectifyService.ofy().save().entities(lst).now();
	}
	public static void writeEntity(Object entity) {
		ObjectifyService.ofy().save().entity(entity).now();
	}

	public static void deleteTransactionInfo(TransactionInfo info, String datatype) throws IOException {
		String ID = info.getIdentifier();
		DeleteDataStructures.deleteObject(datatype,ID);
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(info.getIdentifier(), 
					datatype);
		WriteReadDatabaseObjects.deleteHierarchy(hierarchy);
		ObjectifyService.ofy().delete().entity(info);
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
	static public void writeTransactionWithoutObjectWrite(DatabaseObject object) {
		String classname = object.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(
				object.getIdentifier(), object.getAccess(), object.getOwner(),object.getSourceID(),classname);
		transaction.setStoredObjectKey(object.getKey());
		writeDatabaseObject(transaction);
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
	 * @throws IOException 
	 * 
	 */
	static public void writeObjectWithTransaction(DatabaseObject object) {
		String classname = object.getClass().getName();
		
		String transclass = TransactionInfo.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("identifier",object.getIdentifier());
		values.add(value1);
		QueryPropertyValue value2 = new QueryPropertyValue("transactionObjectType",classname);
		values.add(value2);
		QuerySetupBase query = new QuerySetupBase(object.getOwner(),transclass, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(query);
			List<DatabaseObject> objs = result.getResults();
			for(DatabaseObject obj : objs) {
				TransactionInfo info = (TransactionInfo) obj;
				String datatype = info.getTransactionObjectType();
				int pos = datatype.lastIndexOf('.');
				if(pos > 0) {
					String type = DatasetOntologyParsing.getTypeFromCanonicalDataType(datatype);
					deleteTransactionInfo(info,type);
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("writeObjectWithTransaction: ClassNotFoundException ");
		} catch (IOException ex) {
			System.out.println("writeObjectWithTransaction: IOException ");
		}
		TransactionInfo transaction = new TransactionInfo(
				object.getIdentifier(), object.getAccess(), object.getOwner(),object.getSourceID(),classname);
		writeDatabaseObject(object);
		transaction.setStoredObjectKey(object.getKey());
		writeDatabaseObject(transaction);
	}
	
	
	
	
	static public void initializeIndividualInformation(String username, String email, String userrole) {
/*
		String sourceID = QueryBase.getDataSourceIdentification(username);
		String id = username;

		ChemConnectDataStructure datastructure = new ChemConnectDataStructure(username,sourceID);

		// Individual
		DataElementInformation contactelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.databaseUserS);
		String individualid = id + "-" + contactelement.getSuffix();
		// ContactUserInfo
		DataElementInformation contactuserelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.contactKeyS);
		String contactid = individualid + "-" + contactelement.getSuffix();
		// ContactLocationInformation
		DataElementInformation locationelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.locationKeyS);
		String locationid = individualid + "-" + locationelement.getSuffix();
		// GPSLocation
		DataElementInformation gpselement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.gpsCoordinatesID);
		String gpsid = locationid + "-" + gpselement.getSuffix();
		// PersonalDescription
		DataElementInformation personalelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.databaseUserS);
		String personalid = individualid + "-" + personalelement.getSuffix();
		// NameOfPerson
		DataElementInformation nameelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.databaseUserS);
		String nameid = personalid + "-" + nameelement.getSuffix();
		// UserAccountinformation 
		DataElementInformation useraccountelement = 
				DatasetOntologyParsing.getSubElementStructureFromIDObject(StandardDatasetMetaData.userAccountS);
		String useraccountid = id + "-" + useraccountelement.getSuffix();
		
		
		NameOfPerson name = new NameOfPerson(nameid,sourceID);
		PersonalDescription personal = new PersonalDescription(personalid,username,username,sourceID,userrole,name);
				
		GPSLocation gps = new GPSLocation(gpsid,sourceID);
		ContactLocationInformation location = new ContactLocationInformation(locationid,username,sourceID,gps.getIdentifier());

		ContactInfoData contactinfo = new ContactInfoData(contactid,sourceID);
		contactinfo.setEmail(email);

		IndividualInformation individual = new IndividualInformation(datastructure,contactinfo.getIdentifier(),
				location.getIdentifier(),personal.getIdentifier(),new HashSet<String>());
		
		UserAccountInformation account = new UserAccountInformation(
				useraccountid,username,username,sourceID,
				password,userrole,email);

		
		UserAccount useraccount = new UserAccount(datastructure, account.getIdentifier(), individual.getIdentifier());
		
		DescriptionDataData data = new DescriptionDataData(username,sourceID);

		
		DatabaseWriteBase.writeDatabaseObject(account);
		DatabaseWriteBase.writeDatabaseObject(gps);
		DatabaseWriteBase.writeDatabaseObject(name);
		DatabaseWriteBase.writeDatabaseObject(contactinfo);
		DatabaseWriteBase.writeDatabaseObject(location);
		DatabaseWriteBase.writeDatabaseObject(data);
		DatabaseWriteBase.writeDatabaseObject(personal);
		DatabaseWriteBase.writeDatabaseObject(individual);
		*/
	}
}
