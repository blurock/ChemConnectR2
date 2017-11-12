package info.esblurock.reaction.io.dataset;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DataSetReference;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.dataset.Consortium;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
public enum InterpretData {

	DatabaseObject {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {

			String identifierS = (String) yaml.get(StandardDatasetMetaData.identifierKeyS);
			String ownerS = (String) yaml.get(StandardDatasetMetaData.ownerKeyS);
			String accessS = (String) yaml.get(StandardDatasetMetaData.accessKeyS);

			if(top != null) {
				if(identifierS == null) {
					identifierS = top.getIdentifier();
				}
				if(ownerS == null) {
					ownerS = top.getOwner();
				}
				if(accessS == null) {
					accessS = top.getAccess();
				}
			}
			DatabaseObject obj = new DatabaseObject(identifierS, ownerS, accessS, sourceID);

			return obj;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put(StandardDatasetMetaData.identifierKeyS, object.getIdentifier());
			map.put(StandardDatasetMetaData.ownerKeyS, object.getOwner());
			map.put(StandardDatasetMetaData.accessKeyS, object.getAccess());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatabaseObject.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatabaseObject.class.getCanonicalName();
		}
		
	},

	DescriptionDataData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {

			DescriptionDataData descdata = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String titleS = (String) yaml.get(StandardDatasetMetaData.titleKeyS);
			String descriptionS = (String) yaml.get(StandardDatasetMetaData.descriptionKeyS);
			String datasetS = (String) yaml.get(StandardDatasetMetaData.datasetKeyS);
			String datatypeS = (String) yaml.get(StandardDatasetMetaData.dataTypeKeyS);
			String keywordsS = (String) yaml.get(StandardDatasetMetaData.keywordKeyS);
			String sourceDateS = (String) yaml.get(StandardDatasetMetaData.sourceDateKeyS);

			HashSet<String> keywords = parseKeywords(keywordsS);
			Date dateD = null;
			if(sourceDateS != null) {
				dateD = parseDate(sourceDateS);
			} else {
				dateD = new Date();
			}

			descdata = new DescriptionDataData(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					titleS, descriptionS, datasetS, 
					dateD, datatypeS, keywords);

			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DescriptionDataData description = (DescriptionDataData) object;
			Map<String, Object> map = new HashMap<String, Object>();

			String sourceDateS = dateToString(description.getSourceDate());
			String keywordsS = keywordsToString(description.getKeywords());
			
			map.put(StandardDatasetMetaData.titleKeyS, description.getOnlinedescription());
			map.put(StandardDatasetMetaData.descriptionKeyS, description.getFulldescription());
			map.put(StandardDatasetMetaData.datasetKeyS, description.getSourcekey());
			map.put(StandardDatasetMetaData.keywordKeyS, keywordsS);
			map.put(StandardDatasetMetaData.dataTypeKeyS, description.getDataType());
			map.put(StandardDatasetMetaData.sourceDateKeyS, sourceDateS);
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DescriptionDataData.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return DescriptionDataData.class.getCanonicalName();
		}

	},
	ContactInfoData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String email = (String) yaml.get(StandardDatasetMetaData.emailKeyS);
			String hasSiteS = (String) yaml.get(StandardDatasetMetaData.hassiteKeyS);
			String siteOfS = (String) yaml.get(StandardDatasetMetaData.siteofKeyS);

			HashSet<String> hassites = parseKeywords(hasSiteS);
			HashSet<String> siteofs = parseKeywords(siteOfS);
			
			
			ContactInfoData contact = new ContactInfoData(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					 email, hassites, siteofs);
			return contact;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactInfoData contact = (ContactInfoData) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String hasSite = keywordsToString(contact.getHasSite());
			String siteof = keywordsToString(contact.getTopSite());
			
			map.put(StandardDatasetMetaData.emailKeyS, contact.getEmail());
			map.put(StandardDatasetMetaData.hassiteKeyS, hasSite);
			map.put(StandardDatasetMetaData.siteofKeyS, siteof);

			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactInfoData.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return ContactInfoData.class.getCanonicalName();
		}

	},
	ContactLocationInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top,yaml, sourceID);

			String streetaddress = (String) yaml.get(StandardDatasetMetaData.streetaddressKeyS);
			String locality = (String) yaml.get(StandardDatasetMetaData.localityKeyS);
			String postalcode = (String) yaml.get(StandardDatasetMetaData.postalcodeKeyS);
			String country = (String) yaml.get(StandardDatasetMetaData.countryKeyS);
			String gspLocationID = (String) yaml.get(StandardDatasetMetaData.gpsCoordinatesID);

			ContactLocationInformation location = new ContactLocationInformation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					streetaddress, locality,
					country, postalcode, gspLocationID);
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactLocationInformation location = (ContactLocationInformation) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			
			map.put(StandardDatasetMetaData.streetaddressKeyS, location.getAddressAddress());
			map.put(StandardDatasetMetaData.localityKeyS, location.getCity());
			map.put(StandardDatasetMetaData.postalcodeKeyS, location.getPostcode());
			map.put(StandardDatasetMetaData.countryKeyS, location.getCountry());
			map.put(StandardDatasetMetaData.gpsCoordinatesID, location.getGpsLocationID());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactLocationInformation.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return ContactLocationInformation.class.getCanonicalName();
		}

	}, GPSLocation {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top,yaml, sourceID);

			String GPSLongitude = (String) yaml.get(StandardDatasetMetaData.longitudeKeyS);
			String GPSLatitude = (String) yaml.get(StandardDatasetMetaData.latitudeKeyS);

			GPSLocation location = new GPSLocation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					GPSLongitude, GPSLatitude);
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			GPSLocation location = (GPSLocation) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			
			map.put(StandardDatasetMetaData.longitudeKeyS, location.getGPSLatitude());
			map.put(StandardDatasetMetaData.latitudeKeyS, location.getGPSLongitude());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(GPSLocation.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return GPSLocation.class.getCanonicalName();
		}
		
	}, OrganizationDescription {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {
			
			OrganizationDescription descdata = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String organizationalUnitS = (String) yaml.get(StandardDatasetMetaData.organizationUnit);
			String organizationClassificationS = (String) yaml.get(StandardDatasetMetaData.organizationClassification);
			String organizationNameS = (String) yaml.get(StandardDatasetMetaData.organizationName);
			String subOrganizationOfS = (String) yaml.get(StandardDatasetMetaData.subOrganizationOf);

			descdata = new OrganizationDescription(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, 
					organizationalUnitS, organizationClassificationS, organizationNameS, subOrganizationOfS);
			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				
				DatabaseObject object) throws IOException {
			
			OrganizationDescription org = (OrganizationDescription) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.organizationClassification, org.getOrganizationClassification());
			map.put(StandardDatasetMetaData.organizationName, org.getOrganizationName());
			map.put(StandardDatasetMetaData.organizationUnit, org.getOrganizationUnit());
			map.put(StandardDatasetMetaData.subOrganizationOf, org.getSubOrganizationOf());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(OrganizationDescription.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return OrganizationDescription.class.getCanonicalName();
		}
		
	}, DataSetReference {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String referenceDOIS = (String) yaml.get(StandardDatasetMetaData.referenceDOI);
			String referenceTitleS = (String) yaml.get(StandardDatasetMetaData.referenceTitle);
			String referenceBibliographicStringS = (String) yaml.get(StandardDatasetMetaData.referenceBibliographicString);
			String referenceAuthorsS = (String) yaml.get(StandardDatasetMetaData.referenceAuthors);
			
			HashSet<String> authors = parseKeywords(referenceAuthorsS);
			
			DataSetReference refset = new DataSetReference(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					referenceDOIS, referenceTitleS, referenceBibliographicStringS, 
					authors);
			
			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			DataSetReference ref = (DataSetReference) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String authors = keywordsToString(ref.getAuthors());
			map.put(StandardDatasetMetaData.referenceDOI, ref.getDOI());
			map.put(StandardDatasetMetaData.referenceTitle, ref.getTitle());
			map.put(StandardDatasetMetaData.referenceBibliographicString, ref.getReferenceString());
			map.put(StandardDatasetMetaData.referenceAuthors, authors);
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataSetReference.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return DataSetReference.class.getCanonicalName();
		}
		
	}
	, PersonalDescription {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String userClassification = (String) yaml.get(StandardDatasetMetaData.userClassification);
			String userNameID = (String) yaml.get(StandardDatasetMetaData.userNameID);
			
			PersonalDescription person = new PersonalDescription(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					userClassification, userNameID);
			
			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			PersonalDescription person = (PersonalDescription) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.userClassification, person.getUserClassification());
			map.put(StandardDatasetMetaData.userNameID, person.getNameOfPersonIdentifier());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PersonalDescription.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return PersonalDescription.class.getCanonicalName();
		}
		
	}
	, NameOfPerson {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {
			
			NameOfPerson person = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String nameTitleS = (String) yaml.get(StandardDatasetMetaData.titleName);
			String givenNameS = (String) yaml.get(StandardDatasetMetaData.givenName);
			String familyNameS = (String) yaml.get(StandardDatasetMetaData.familyName);
			
			person = new NameOfPerson(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					nameTitleS, givenNameS, familyNameS);
			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			NameOfPerson name = (NameOfPerson) object;

			map.put(StandardDatasetMetaData.titleName,  name.getTitle());
			map.put(StandardDatasetMetaData.givenName,  name.getGivenName());
			map.put(StandardDatasetMetaData.familyName, name.getFamilyName());
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(NameOfPerson.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return NameOfPerson.class.getCanonicalName();
		}
		
	}, IndividualInformation {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			IndividualInformation org = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String contactInfoDataID = (String) yaml.get(StandardDatasetMetaData.contactInfoDataID);
			String contactLocationInformationID = (String) yaml.get(StandardDatasetMetaData.contactLocationInformationID);
			String descriptionDataDataID = (String) yaml.get(StandardDatasetMetaData.descriptionDataDataID);
			String organizationID = (String) yaml.get(StandardDatasetMetaData.organizationID);
			String personalDescriptionID = (String) yaml.get(StandardDatasetMetaData.personalDescriptionID);
			
			org = new IndividualInformation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					descriptionDataDataID, 
					contactInfoDataID, contactLocationInformationID, 
					personalDescriptionID,organizationID);
			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			IndividualInformation individual = (IndividualInformation) object;
			
			map.put(StandardDatasetMetaData.contactInfoDataID,  individual.getContactInfoDataID());
			map.put(StandardDatasetMetaData.contactLocationInformationID,  individual.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.descriptionDataDataID, individual.getDescriptionDataDataID());
			map.put(StandardDatasetMetaData.organizationID, individual.getOrganizationID());
			map.put(StandardDatasetMetaData.personalDescriptionID, individual.getPersonalDescriptionID());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(IndividualInformation.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return IndividualInformation.class.getCanonicalName();
		}
		
	}, Organization {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException {
			Organization org = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String contactInfoDataID = (String) yaml.get(StandardDatasetMetaData.contactInfoDataID);
			String contactLocationInformationID = (String) yaml.get(StandardDatasetMetaData.contactLocationInformationID);
			String descriptionDataDataID = (String) yaml.get(StandardDatasetMetaData.descriptionDataDataID);
			String organizationDescriptionID = (String) yaml.get(StandardDatasetMetaData.organizationDescriptionID);

			org = new Organization(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					descriptionDataDataID, contactInfoDataID, 
					contactLocationInformationID, organizationDescriptionID);
			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Organization org = (Organization) object;
			
			map.put(StandardDatasetMetaData.contactInfoDataID,  org.getContactInfoDataID());
			map.put(StandardDatasetMetaData.contactLocationInformationID,  org.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.descriptionDataDataID, org.getDescriptionDataDataID());
			map.put(StandardDatasetMetaData.organizationDescriptionID, org.getOrganizationDescriptionID());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Organization.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return Organization.class.getCanonicalName();
		}
		
	}, UserAccountInformation {
		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			UserAccountInformation account = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			
			
			String contactInfoDataID = (String) yaml.get(StandardDatasetMetaData.contactInfoDataID);
			String password = (String) yaml.get(StandardDatasetMetaData.password);
			String userrole = (String) yaml.get(StandardDatasetMetaData.userrole);
			String emailS = (String) yaml.get(StandardDatasetMetaData.emailKeyS);

			account = new UserAccountInformation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					contactInfoDataID,
					password,userrole,emailS);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccountInformation account = (UserAccountInformation) object;
			
			map.put(StandardDatasetMetaData.contactInfoDataID,  account.getContactInfoDataID());
			map.put(StandardDatasetMetaData.password, account.getPassword()) ;
			map.put(StandardDatasetMetaData.userrole,  account.getUserrole());
			map.put(StandardDatasetMetaData.emailKeyS, account.getEmail());
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			DatabaseObject obj = QueryBase.getDatabaseObjectFromIdentifier(UserAccountInformation.class.getCanonicalName(), identifier);
			System.out.println("UserAccountInformation: " + obj.getClass().getCanonicalName());
			return obj;
		}
		@Override
		public String canonicalClassName() {
			return UserAccountInformation.class.getCanonicalName();
		}
		
	}, UserAccount {
		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			UserAccount account = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String DatabaseUserID = (String) yaml.get(StandardDatasetMetaData.DatabaseUserID);
			String UserAccountInformationID = (String) yaml.get(StandardDatasetMetaData.UserAccountInformationID);

			account = new UserAccount(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					DatabaseUserID,UserAccountInformationID);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccount account = (UserAccount) object;
			
			map.put(StandardDatasetMetaData.DatabaseUserID,  account.getDatabaseUserID());
			map.put(StandardDatasetMetaData.UserAccountInformationID, account.getUserAccountInformationID()) ;
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(UserAccount.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return UserAccount.class.getCanonicalName();
		}
		
	}, Consortium {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			
			Consortium consortium = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);
			
			String DatabaseUserIDReadAccessS = (String) yaml.get(StandardDatasetMetaData.DatabaseUserIDReadAccess);
			String DatabaseUserIDWriteAccessS = (String) yaml.get(StandardDatasetMetaData.DatabaseUserIDWriteAccess);
			String DataSetCatalogIDS = (String) yaml.get(StandardDatasetMetaData.DataSetCatalogID);
			String OrganizationIDS = (String) yaml.get(StandardDatasetMetaData.OrganizationID);
			String DescriptionIDS = (String) yaml.get(StandardDatasetMetaData.DescriptionID);
			
			HashSet<String> DatabaseUserIDReadAccess = parseKeywords(DatabaseUserIDReadAccessS);
			HashSet<String> DatabaseUserIDWriteAccess = parseKeywords(DatabaseUserIDWriteAccessS);
			HashSet<String> DataSetCatalogID = parseKeywords(DataSetCatalogIDS);
			HashSet<String> OrganizationID = parseKeywords(OrganizationIDS);

			consortium = new Consortium(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID,
					DatabaseUserIDReadAccess, DatabaseUserIDWriteAccess,
					DataSetCatalogID, OrganizationID, DescriptionIDS);

			return consortium;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Consortium consortium = (Consortium) object;
			
			String DatabaseUserIDReadAccess = keywordsToString(consortium.getDatabaseUserIDReadAccess());
			String DatabaseUserIDWriteAccess = keywordsToString(consortium.getDatabaseUserIDWriteAccess());
			String DataSetCatalogID = keywordsToString(consortium.getDataSetCatalogID());
			String OrganizationID = keywordsToString(consortium.getOrganizationID());
			String DescriptionID = consortium.getDescriptionID();
			
			map.put(StandardDatasetMetaData.DatabaseUserIDReadAccess,  DatabaseUserIDReadAccess);
			map.put(StandardDatasetMetaData.DatabaseUserIDWriteAccess, DatabaseUserIDWriteAccess) ;
			map.put(StandardDatasetMetaData.DataSetCatalogID,  DataSetCatalogID);
			map.put(StandardDatasetMetaData.OrganizationID, OrganizationID);
			map.put(StandardDatasetMetaData.DescriptionID, DescriptionID);
			
			return map;
		}
		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Consortium.class.getCanonicalName(), identifier);
		}
		@Override
		public String canonicalClassName() {
			return Consortium.class.getCanonicalName();
		}
		
	};
	
	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID) throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException ;
	public abstract String canonicalClassName();
	
	public HashSet<String> parseKeywords(String keywordset) {
		HashSet<String> keywords = new HashSet<String>();
		if(keywordset != null) {
		StringTokenizer tok = new StringTokenizer(keywordset, ",");
		while (tok.hasMoreTokens()) {
			keywords.add(tok.nextToken().trim());
		}
		}
		return keywords;
	}

	public Date parseDate(String dateS) throws IOException {
		Date dateD = null;
		dateD = parseDateWithFormat(dateS, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		if(dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyy-MM-dd");
		}
		if(dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyyMMdd");
		}
		if(dateD == null) {
			throw new IOException("Date parse exception");
		}
		return dateD;
	}
	
	public Date parseDateWithFormat(String dateS, String formatS) {
		Date dateD = null;
		try {
			DateFormat format = new SimpleDateFormat(formatS);
			dateD = format.parse(dateS);
		} catch (ParseException e) {
		}
		return dateD;
		
	}
	public String dateToString(Date dateD) {
		String formatS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		DateFormat format = new SimpleDateFormat(formatS);
		String dateS = format.format(dateD);
		return dateS;
	}
	public String keywordsToString(HashSet<String> keywords) {
		StringBuilder build = new StringBuilder();
		boolean notfirstKey = false;
		for(String key: keywords) {
			if(notfirstKey) {
				build.append(", ");
			} else {
				notfirstKey = true;
			}
			build.append(key);
		}
		return build.toString();
	}

}
