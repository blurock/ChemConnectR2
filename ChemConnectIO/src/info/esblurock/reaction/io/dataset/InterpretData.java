package info.esblurock.reaction.io.dataset;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataSpecification;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.dataset.ChemConnectSetOfValues;
import info.esblurock.reaction.chemconnect.core.data.dataset.ChemConnectObjectLink;

public enum InterpretData {

	DatabaseObject {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			String identifierS = (String) yaml.get(StandardDatasetMetaData.identifierKeyS);
			String ownerS = (String) yaml.get(StandardDatasetMetaData.ownerKeyS);
			String accessS = (String) yaml.get(StandardDatasetMetaData.accessKeyS);

			if (top != null) {
				if (identifierS == null) {
					identifierS = top.getIdentifier();
				}
				if (ownerS == null) {
					ownerS = top.getOwner();
				}
				if (accessS == null) {
					accessS = top.getAccess();
				}
			}
			DatabaseObject obj = new DatabaseObject(identifierS, ownerS, accessS, sourceID);

			return obj;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			Map<String, Object> map = new HashMap<String, Object>();

			//HashSet<String> hasSite = interpretMultipleYaml(StandardDatasetMetaData.hassiteKeyS,yaml);
			map.put(StandardDatasetMetaData.identifierKeyS, object.getIdentifier());
			map.put(StandardDatasetMetaData.ownerKeyS, object.getOwner());
			map.put(StandardDatasetMetaData.accessKeyS, object.getAccess());
			map.put(StandardDatasetMetaData.sourceIDS, object.getAccess());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatabaseObject.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatabaseObject.class.getCanonicalName();
		}

	},
	ChemConnectDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top,
				Map<String, Object> yaml, String sourceID) throws IOException {
			ChemConnectDataStructure datastructure = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			System.out.println("ChemConnectDataStructure: " + yaml);
			Object yamlobj = yaml.get(StandardDatasetMetaData.dataSetReferenceS);
			System.out.println("ChemConnectDataStructure: " + yamlobj);
					
			
			HashSet<String> dataSetReferenceS = interpretMultipleYaml(StandardDatasetMetaData.dataSetReferenceS,yaml);
			System.out.println("ChemConnectDataStructure: " + dataSetReferenceS);
			
			String descriptionDataDataS = (String) yaml.get(StandardDatasetMetaData.descriptionDataDataS);
			HashSet<String> consortiumS = interpretMultipleYaml(StandardDatasetMetaData.consortiumS,yaml);

			datastructure = new ChemConnectDataStructure(objdata, dataSetReferenceS, descriptionDataDataS, consortiumS);
			
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			putMultipleInYaml(StandardDatasetMetaData.dataSetReferenceS, map,datastructure.getDataSetReferenceID());
			map.put(StandardDatasetMetaData.descriptionDataDataS, datastructure.getDescriptionDataDataID());
			putMultipleInYaml(StandardDatasetMetaData.consortiumS, map,datastructure.getConsortiumID());

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectDataStructure.class.getCanonicalName();
		}

	},
	ChemConnectCompoundDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure datastructure = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String parentCatalogS = (String) yaml.get(StandardDatasetMetaData.parentCatalogS);
			datastructure = new ChemConnectCompoundDataStructure(objdata, parentCatalogS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			ChemConnectCompoundDataStructure datastructure = (ChemConnectCompoundDataStructure) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.descriptionDataDataS, datastructure.getParentLink());
			
			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectCompoundDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectCompoundDataStructure.class.getCanonicalName();
		}
	},
	DescriptionDataData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			DescriptionDataData descdata = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String titleS = (String) yaml.get(StandardDatasetMetaData.titleKeyS);
			String descriptionS = (String) yaml.get(StandardDatasetMetaData.descriptionKeyS);
			String datasetS = (String) yaml.get(StandardDatasetMetaData.datasetKeyS);
			String datatypeS = (String) yaml.get(StandardDatasetMetaData.dataTypeKeyS);
			String sourceDateS = (String) yaml.get(StandardDatasetMetaData.sourceDateKeyS);
			HashSet<String> keywords = interpretMultipleYaml(StandardDatasetMetaData.keywordKeyS,yaml);

			Date dateD = null;
			if (sourceDateS != null) {
				dateD = parseDate(sourceDateS);
			} else {
				dateD = new Date();
			}

			descdata = new DescriptionDataData(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, titleS, descriptionS, datasetS, dateD, datatypeS, keywords);

			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DescriptionDataData description = (DescriptionDataData) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String sourceDateS = dateToString(description.getSourceDate());
			putMultipleInYaml(StandardDatasetMetaData.keywordKeyS,map,description.getKeywords());

			map.put(StandardDatasetMetaData.titleKeyS, description.getOnlinedescription());
			map.put(StandardDatasetMetaData.descriptionKeyS, description.getFulldescription());
			map.put(StandardDatasetMetaData.datasetKeyS, description.getSourcekey());
			map.put(StandardDatasetMetaData.dataTypeKeyS, description.getDataType());
			map.put(StandardDatasetMetaData.sourceDateKeyS, sourceDateS);

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DescriptionDataData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DescriptionDataData.class.getCanonicalName();
		}

	},
	DataSpecification {

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject fillFromYamlString(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject obj = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String deviceSensorS = (String) yaml.get(StandardDatasetMetaData.deviceSensorS);
			String instrumentS = (String) yaml.get(StandardDatasetMetaData.instrumentS);
			String chemConnectActivityS = (String) yaml.get(StandardDatasetMetaData.chemConnectActivityS);
			String dataPointConceptS = (String) yaml.get(StandardDatasetMetaData.dataPointConceptS);

			DataSpecification spec = new DataSpecification(obj,
					deviceSensorS, instrumentS, chemConnectActivityS, dataPointConceptS);
			return spec;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject object) throws IOException {
			DataSpecification spec = (DataSpecification) object;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.deviceSensorS, spec.getDeviceSensor());
			map.put(StandardDatasetMetaData.instrumentS, spec.getInstrument());
			map.put(StandardDatasetMetaData.chemConnectActivityS, spec.getChemConnectActivity());
			map.put(StandardDatasetMetaData.dataPointConceptS, spec.getDataPointConcept());

			return map;
		}

		@Override
		public info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String canonicalClassName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	ContactInfoData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String email = (String) yaml.get(StandardDatasetMetaData.emailKeyS);
			HashSet<String> hasSite = interpretMultipleYaml(StandardDatasetMetaData.hassiteKeyS,yaml);
			HashSet<String> siteOf = interpretMultipleYaml(StandardDatasetMetaData.siteofKeyS,yaml);

			ContactInfoData contact = new ContactInfoData(objdata.getIdentifier(), objdata.getAccess(),
					objdata.getOwner(), sourceID, email, hasSite, siteOf);
			return contact;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactInfoData contact = (ContactInfoData) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.emailKeyS, contact.getEmail());
			putMultipleInYaml(StandardDatasetMetaData.hassiteKeyS, map,contact.getHasSite());
			putMultipleInYaml(StandardDatasetMetaData.siteofKeyS, map,contact.getTopSite());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactInfoData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactInfoData.class.getCanonicalName();
		}

	},
	ContactLocationInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String streetaddress = (String) yaml.get(StandardDatasetMetaData.streetaddressKeyS);
			String locality = (String) yaml.get(StandardDatasetMetaData.localityKeyS);
			String postalcode = (String) yaml.get(StandardDatasetMetaData.postalcodeKeyS);
			String country = (String) yaml.get(StandardDatasetMetaData.countryKeyS);
			//Object gps = yaml.get(StandardDatasetMetaData.gpsCoordinatesID);
			String gspLocationID = (String) yaml.get(StandardDatasetMetaData.gpsCoordinatesID);

			ContactLocationInformation location = new ContactLocationInformation(objdata.getIdentifier(),
					objdata.getAccess(), objdata.getOwner(), sourceID, streetaddress, locality, country, postalcode,
					gspLocationID);
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
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactLocationInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactLocationInformation.class.getCanonicalName();
		}

	},
	GPSLocation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String GPSLongitude = (String) yaml.get(StandardDatasetMetaData.longitudeKeyS);
			String GPSLatitude = (String) yaml.get(StandardDatasetMetaData.latitudeKeyS);

			GPSLocation location = new GPSLocation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, GPSLongitude, GPSLatitude);
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
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(GPSLocation.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return GPSLocation.class.getCanonicalName();
		}

	},
	OrganizationDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			OrganizationDescription descdata = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String organizationalUnitS = (String) yaml.get(StandardDatasetMetaData.organizationUnit);
			String organizationClassificationS = (String) yaml.get(StandardDatasetMetaData.organizationClassification);
			String organizationNameS = (String) yaml.get(StandardDatasetMetaData.organizationName);
			String subOrganizationOfS = (String) yaml.get(StandardDatasetMetaData.subOrganizationOf);

			descdata = new OrganizationDescription(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, organizationalUnitS, organizationClassificationS, organizationNameS, subOrganizationOfS);
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
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(OrganizationDescription.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return OrganizationDescription.class.getCanonicalName();
		}

	},
	DataSetReference {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String referenceDOIS = (String) yaml.get(StandardDatasetMetaData.referenceDOI);
			String referenceTitleS = (String) yaml.get(StandardDatasetMetaData.referenceTitle);
			String referenceBibliographicStringS = (String) yaml
					.get(StandardDatasetMetaData.referenceBibliographicString);

			System.out.println("DataSetReference:  " + yaml.get(StandardDatasetMetaData.referenceAuthors));
			
			HashSet<String> authors = interpretMultipleYaml(StandardDatasetMetaData.referenceAuthors,yaml);

			DataSetReference refset = new DataSetReference(objdata.getIdentifier(), objdata.getAccess(),
					objdata.getOwner(), sourceID, referenceDOIS, referenceTitleS, referenceBibliographicStringS,
					authors);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataSetReference ref = (DataSetReference) object;

			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.referenceDOI, ref.getDOI());
			map.put(StandardDatasetMetaData.referenceTitle, ref.getTitle());
			map.put(StandardDatasetMetaData.referenceBibliographicString, ref.getReferenceString());
			putMultipleInYaml(StandardDatasetMetaData.referenceAuthors, map,ref.getAuthors());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataSetReference.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataSetReference.class.getCanonicalName();
		}

	}, ChemConnectSetOfValues {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			HashSet<String> parameterDescription = interpretMultipleYaml(StandardDatasetMetaData.parameterDescriptionS,yaml);
			ChemConnectSetOfValues refset = new ChemConnectSetOfValues(compound, parameterDescription);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectSetOfValues ref = (ChemConnectSetOfValues) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			putMultipleInYaml(StandardDatasetMetaData.parameterDescriptionS, map,ref.getValues());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectSetOfValues.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectSetOfValues.class.getCanonicalName();
		}

	}, ChemConnectObjectLink {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);


			String dataStructureIdentifierS = (String) yaml.get(StandardDatasetMetaData.dataStructureIdentifierS);
			String linkConceptTypeS = (String) yaml.get(StandardDatasetMetaData.linkConceptTypeS);
			String dataConceptTypeS = (String) yaml.get(StandardDatasetMetaData.dataConceptTypeS);
			ChemConnectObjectLink refset = new ChemConnectObjectLink(compound, 
					dataStructureIdentifierS,linkConceptTypeS,dataConceptTypeS);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectObjectLink ref = (ChemConnectObjectLink) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.dataStructureIdentifierS, ref.getDataStructure());
			map.put(StandardDatasetMetaData.linkConceptTypeS, ref.getLinkConceptType());
			map.put(StandardDatasetMetaData.dataConceptTypeS, ref.getDataConceptType());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectObjectLink.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectObjectLink.class.getCanonicalName();
		}

	},
	PersonalDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String userClassification = (String) yaml.get(StandardDatasetMetaData.userClassification);
			String userNameID = (String) yaml.get(StandardDatasetMetaData.userNameID);

			PersonalDescription person = new PersonalDescription(compound, userClassification, userNameID);

			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			PersonalDescription person = (PersonalDescription) object;

			InterpretData interpret = InterpretData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDatasetMetaData.userClassification, person.getUserClassification());
			map.put(StandardDatasetMetaData.userNameID, person.getNameOfPersonIdentifier());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PersonalDescription.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return PersonalDescription.class.getCanonicalName();
		}

	},
	NameOfPerson {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			NameOfPerson person = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String nameTitleS = (String) yaml.get(StandardDatasetMetaData.titleName);
			String givenNameS = (String) yaml.get(StandardDatasetMetaData.givenName);
			String familyNameS = (String) yaml.get(StandardDatasetMetaData.familyName);

			person = new NameOfPerson(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(), sourceID,
					nameTitleS, givenNameS, familyNameS);
			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			NameOfPerson name = (NameOfPerson) object;

			map.put(StandardDatasetMetaData.titleName, name.getTitle());
			map.put(StandardDatasetMetaData.givenName, name.getGivenName());
			map.put(StandardDatasetMetaData.familyName, name.getFamilyName());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(NameOfPerson.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return NameOfPerson.class.getCanonicalName();
		}

	},
	IndividualInformation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			IndividualInformation org = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String contactInfoDataID = (String) yaml.get(StandardDatasetMetaData.contactKeyS);
			String contactLocationInformationID = (String) yaml
					.get(StandardDatasetMetaData.locationKeyS);
			String personalDescriptionID = (String) yaml.get(StandardDatasetMetaData.personS);
			HashSet<String> organization = interpretMultipleYaml(StandardDatasetMetaData.orginfoKeyS,yaml);

			org = new IndividualInformation(datastructure,
					contactInfoDataID, contactLocationInformationID, 
					personalDescriptionID, organization);
			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			IndividualInformation individual = (IndividualInformation) object;

			map.put(StandardDatasetMetaData.contactKeyS, individual.getContactInfoDataID());
			map.put(StandardDatasetMetaData.locationKeyS, individual.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.personS, individual.getPersonalDescriptionID());
			putMultipleInYaml(StandardDatasetMetaData.orginfoKeyS, map,individual.getOrganizationID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(IndividualInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return IndividualInformation.class.getCanonicalName();
		}

	},
	Organization {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			Organization org = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			HashSet<String> accounts = interpretMultipleYaml(StandardDatasetMetaData.userAccountS, yaml);
			String contactInfoDataID = (String) yaml.get(StandardDatasetMetaData.contactKeyS);
			String contactLocationInformationID = (String) yaml
					.get(StandardDatasetMetaData.locationKeyS);
			String organizationDescriptionID = (String) yaml.get(StandardDatasetMetaData.orginfoKeyS);
		
			org = new Organization(datastructure, contactInfoDataID,
					contactLocationInformationID, organizationDescriptionID,
					accounts);

			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Organization org = (Organization) object;
			map.put(StandardDatasetMetaData.contactKeyS, org.getContactInfoDataID());
			map.put(StandardDatasetMetaData.locationKeyS, org.getContactLocationInformationID());
			map.put(StandardDatasetMetaData.orginfoKeyS, org.getOrganizationDescriptionID());
			putMultipleInYaml(StandardDatasetMetaData.userAccountS, map, org.getUserAccounts());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Organization.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Organization.class.getCanonicalName();
		}

	},
	UserAccountInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccountInformation account = null;
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String password = (String) yaml.get(StandardDatasetMetaData.password);
			String emailS = (String) yaml.get(StandardDatasetMetaData.emailKeyS);
			String userrole = (String) yaml.get(StandardDatasetMetaData.userrole);

			account = new UserAccountInformation(objdata.getIdentifier(), objdata.getAccess(), 
					objdata.getOwner(), sourceID, 
					emailS, password, userrole);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccountInformation account = (UserAccountInformation) object;

			map.put(StandardDatasetMetaData.password, account.getPassword());
			map.put(StandardDatasetMetaData.emailKeyS, account.getEmail());
			map.put(StandardDatasetMetaData.userrole, account.getUserrole());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			DatabaseObject obj = QueryBase
					.getDatabaseObjectFromIdentifier(UserAccountInformation.class.getCanonicalName(), identifier);
			return obj;
		}

		@Override
		public String canonicalClassName() {
			return UserAccountInformation.class.getCanonicalName();
		}

	},
	UserAccount {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccount account = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String DatabaseUserID = (String) yaml.get(StandardDatasetMetaData.databaseUserS);
			String UserAccountInformationID = (String) yaml.get(StandardDatasetMetaData.UserAccountInformation);

			account = new UserAccount(datastructure,
					DatabaseUserID, UserAccountInformationID);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccount account = (UserAccount) object;

			map.put(StandardDatasetMetaData.databaseUserS, account.getDatabaseUser());
			map.put(StandardDatasetMetaData.UserAccountInformation, account.getUserAccountInformation());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(UserAccount.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return UserAccount.class.getCanonicalName();
		}

	},
	Consortium {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			Consortium consortium = null;
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			HashSet<String> DatabaseUserIDReadAccess 
				= interpretMultipleYaml(StandardDatasetMetaData.DatabaseUserIDReadAccess,yaml);
			HashSet<String> DatabaseUserIDWriteAccess
				= interpretMultipleYaml(StandardDatasetMetaData.DatabaseUserIDWriteAccess,yaml);
			HashSet<String> DataSetCatalogID
				= interpretMultipleYaml(StandardDatasetMetaData.parameterSetDescriptionsS,yaml);
			HashSet<String> OrganizationID
			= interpretMultipleYaml(StandardDatasetMetaData.orginfoKeyS,yaml);

			consortium = new Consortium(datastructure, 
					DatabaseUserIDReadAccess, DatabaseUserIDWriteAccess,
					DataSetCatalogID, OrganizationID);

			return consortium;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretData interpret = InterpretData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Consortium consortium = (Consortium) object;

			putMultipleInYaml(StandardDatasetMetaData.DatabaseUserIDReadAccess, 
					map,consortium.getDatabaseUserIDReadAccess());
			putMultipleInYaml(StandardDatasetMetaData.DatabaseUserIDWriteAccess, 
					map,consortium.getDatabaseUserIDWriteAccess());
			putMultipleInYaml(StandardDatasetMetaData.parameterSetDescriptionsS, 
					map,consortium.getDataSetCatalogID());
			putMultipleInYaml(StandardDatasetMetaData.orginfoKeyS, 
					map,consortium.getOrganizationID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Consortium.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Consortium.class.getCanonicalName();
		}

	};

	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
			throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException;

	public abstract String canonicalClassName();

	public HashSet<String> parseKeywords(String keywordset) {
		HashSet<String> keywords = new HashSet<String>();
		if (keywordset != null) {
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
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyy-MM-dd");
		}
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyyMMdd");
		}
		if (dateD == null) {
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
		for (String key : keywords) {
			if (notfirstKey) {
				build.append(", ");
			} else {
				notfirstKey = true;
			}
			build.append(key);
		}
		return build.toString();
	}

	public void putMultipleInYaml(String key, Map<String, Object> yaml, HashSet<String> set) {
		ArrayList<String> arr = new ArrayList<String>();
		for(String name : set) {
			arr.add(name);
		}
		yaml.put(key, arr);
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<String> interpretMultipleYaml(String key, Map<String, Object> yaml) {
		HashSet<String> answers = new HashSet<String>();
		System.out.println("interpretMultipleYaml: key=" + key);
		Object yamlobj = yaml.get(key);
	if (yamlobj != null) {
			System.out.println("interpretMultipleYaml: " 
					+ key + ",  "
					+ yamlobj.getClass().getCanonicalName());
			if (yamlobj.getClass().getCanonicalName().compareTo("java.util.ArrayList") == 0) {
				List<String> lst = (List<String>) yamlobj;
				for (String answer : lst) {
					answers.add(answer);
				}
			} else if (yamlobj.getClass().getCanonicalName().compareTo("java.util.HashMap") == 0) {
				HashMap<String, Object> map = (HashMap<String, Object>) yamlobj;
				System.out.println(yamlobj);
				Set<String> keyset = map.keySet();
				for(String mapkey : keyset) {
					HashMap<String, Object> submap = (HashMap<String, Object>) map.get(mapkey);
					String identifier = (String) submap.get(StandardDatasetMetaData.identifierKeyS);
					answers.add(identifier);
				}
			} else {
				String answer = (String) yamlobj;
				answers.add(answer);
			}
		}
		return answers;
	}
}
