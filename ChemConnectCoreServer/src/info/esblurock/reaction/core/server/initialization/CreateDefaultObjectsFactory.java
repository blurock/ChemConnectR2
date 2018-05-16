package info.esblurock.reaction.core.server.initialization;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.dataset.AttributeInDataset;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfKeywords;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.DeviceSubsystemElement;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.ontology.OntologyKeys;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class CreateDefaultObjectsFactory {
	
	
	
	static DatabaseObjectHierarchy fillDescriptionDataData(DatabaseObject obj,
			String onelinedescription, String concept, String purpose) {
		DatabaseObjectHierarchy descrhier = createDescriptionDataData(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(onelinedescription);
		
		setOneLineDescription(descrhier, onelinedescription);
		setPurposeConceptPair(descrhier,concept,purpose);

		return descrhier;
	}

	public static DatabaseObjectHierarchy createIndividualInformation(DatabaseObject obj) {
		DatabaseObject indobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.individualInformation);
		String indid = createSuffix(obj,element);
		indobj.setIdentifier(indid);
		
		DatabaseObjectHierarchy structhier = createChemConnectDataStructure(indobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();
		
		DatabaseObjectHierarchy contact = createContactInfo(indobj);
		DatabaseObjectHierarchy location = createContactLocationInformation(indobj);
		DatabaseObjectHierarchy personalhier = createPersonalDescription(indobj);
		IndividualInformation info = new IndividualInformation(structure, 
				contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(),
				personalhier.getObject().getIdentifier());
		info.setIdentifier(indid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(info);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(personalhier);
		top.transferSubObjects(structhier);
		
		return top;
	}

	
	public static DatabaseObjectHierarchy fillMinimalPersonDescription(DatabaseObject obj, 
			String userClassification,
			NameOfPerson person) {
		DatabaseObjectHierarchy infohier = createIndividualInformation(obj);
		IndividualInformation info = (IndividualInformation) infohier.getObject();
		
		DatabaseObjectHierarchy personalhier = infohier.getSubObject(info.getPersonalDescriptionID());
		PersonalDescription personal = (PersonalDescription) personalhier.getObject();
		personal.setUserClassification(userClassification);
		
		 String nameID = personal.getNameOfPersonIdentifier();
		 DatabaseObjectHierarchy namehier = personalhier.getSubObject(nameID);
		 NameOfPerson name = (NameOfPerson) namehier.getObject();
		 name.fill(name, person.getTitle(), person.getGivenName(), person.getFamilyName());
		 
		 String onlinedescription = person.getGivenName() + " " + person.getFamilyName();
		 setOneLineDescription(infohier, onlinedescription);
		 
		 String concept = "dataset:ChemConnectContactConcept";
		 String purpose = "dataset:PurposeOrganization";
		 setPurposeConceptPair(infohier, concept, purpose);
		 
		return infohier;
	}

	public static DatabaseObjectHierarchy createOrganization(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.organization);
		String compid = createSuffix(obj,element);
		compobj.setIdentifier(compid);
		DatabaseObjectHierarchy contact = createContactInfo(compobj);
		DatabaseObjectHierarchy location = createContactLocationInformation(compobj);
		DatabaseObjectHierarchy orgdescr = createOrganizationDescription(compobj);
		
		DatabaseObjectHierarchy structurehier = createChemConnectDataStructure(obj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
		Organization org = new Organization(structure,
				contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(),
				orgdescr.getObject().getIdentifier()
				);
		org.setIdentifier(compid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(org);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(orgdescr);
		top.transferSubObjects(structurehier);
		
		return top;
	}
	public static DatabaseObjectHierarchy fillOrganization(DatabaseObject obj,
			String organizationname) {
		String concept = MetaDataKeywords.conceptContact;
		String purpose = MetaDataKeywords.purposeOrganization;

		DatabaseObjectHierarchy orghier = createOrganization(obj);
		Organization org = (Organization) orghier.getObject();
		
		DatabaseObjectHierarchy orgdescrhier = orghier.getSubObject(org.getOrganizationDescriptionID());
		OrganizationDescription orgdescr = (OrganizationDescription) orgdescrhier.getObject();
		orgdescr.setOrganizationName(organizationname);
				
		setOneLineDescription(orghier, organizationname);
		setPurposeConceptPair(orghier, concept, purpose);

		return orghier;
	}
	
	public static DatabaseObjectHierarchy createSubSystemDescription(DatabaseObject obj) {
		DatabaseObject subobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.subSystemDescription);
		String subid = createSuffix(obj,element);
		subobj.setIdentifier(subid);
		
		DatabaseObjectHierarchy structhier = createChemConnectDataStructure(subobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();
		
		DeviceSubsystemElement device = new DeviceSubsystemElement(structure);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(device);
		hierarchy.transferSubObjects(structhier);
		
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy fillSubSystemDescription(DatabaseObject obj,
			String devicename, String purpose, String concept) {
		DatabaseObjectHierarchy hierarchy = createSubSystemDescription(obj);
		DeviceSubsystemElement device = (DeviceSubsystemElement) hierarchy.getObject();
		
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(device.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(devicename);
		
		setOneLineDescription(hierarchy, devicename);
		setPurposeConceptPair(hierarchy, concept, purpose);
		
		return hierarchy;
	}	
	public static void setOneLineDescription(DatabaseObjectHierarchy hierarchy, String oneline) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		descr.setOnlinedescription(oneline);
	}

	public static void setPurposeConceptPair(DatabaseObjectHierarchy hierarchy, String concept, String purpose) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		
		String pairID = descr.getSourceConceptID();
		DatabaseObjectHierarchy pairhierarchy = descrhierarchy.getSubObject(pairID);
		PurposeConceptPair pair = (PurposeConceptPair) pairhierarchy.getObject();
		pair.setConcept(concept);
		pair.setPurpose(purpose);
	}

	
	public static DatabaseObjectHierarchy fillDatasetCatalogHierarchy(
			DatasetCatalogHierarchy topcatalog,
			DatabaseObject obj, String id, 
			String onelinedescription) {
		DataElementInformation linkelement = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		
		DatabaseObject aobj = new DatabaseObject(obj);
		String aid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(),id);
		aobj.setIdentifier(aid);

		DatabaseObjectHierarchy cathierarchy = createDatasetCatalogHierarchy(aobj);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) cathierarchy.getObject();
		
		setOneLineDescription(cathierarchy,onelinedescription);
		
		int num = topcatalog.getChemConnectObjectLink().size();
		String linknum = Integer.toString(num+1);
		DatabaseObject lobj = new DatabaseObject(obj);
		String oid = createSuffix(lobj,linkelement);
		lobj.setIdentifier(oid+linknum);
		ChemConnectCompoundDataStructure lstructure = new ChemConnectCompoundDataStructure(lobj,topcatalog.getIdentifier());
		DataObjectLink cataloglink = new DataObjectLink(lstructure,MetaDataKeywords.linkSubCatalog, catalog.getIdentifier());
		DatabaseObjectHierarchy subcatalog = new DatabaseObjectHierarchy(cataloglink);

		cathierarchy.addSubobject(subcatalog);
		
		topcatalog.addObjectDataLink(cataloglink);
		
		return cathierarchy;
	}
	/*
	public static DatabaseObjectHierarchy createCataogHierarchyForUser(DatabaseObject obj, 
			String userid, String organizationid) {
		DataElementInformation linkelement = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);

		String uid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), userid);
		DatabaseObject dobj = new DatabaseObject(obj);
		dobj.setIdentifier(uid);
		
		DatabaseObjectHierarchy userhierarchy = createDatasetCatalogHierarchy(dobj);
		
		DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy) userhierarchy.getObject();
		
		String onelinedescription = "User's Catalog";
		setOneLineDescription(userhierarchy, onelinedescription);
		String concept = "dataset:ChemConnectConceptSubCatalog";
		String purpose = "dataset:ChemConnectDefineSubCatagory";
		setPurposeConceptPair(userhierarchy, concept, purpose);
		
		DatabaseObject aobj = new DatabaseObject(dobj);
		String orgsuffix = "orglink";
		String aid = DatasetCatalogHierarchy.createFullCatalogName(dobj.getIdentifier(), orgsuffix);
		aobj.setIdentifier(aid);
		String orgcatdescription = "Institute's Catalog";
		
		return userhierarchy;
	}
	*/
	public static DatabaseObjectHierarchy createChemConnectDataStructure(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.chemConnectDataStructure);
		String compid = createSuffix(obj,element);
		compobj.setIdentifier(compid);
		
		DatabaseObjectHierarchy descrhier = createDescriptionDataData(compobj);
		DescriptionDataData descr = (DescriptionDataData)  descrhier.getObject();
		
		ChemConnectDataStructure compound = new ChemConnectDataStructure(compobj,descr.getIdentifier());
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);
		hierarchy.addSubobject(descrhier);
		
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy createDatasetCatalogHierarchy(DatabaseObject obj) {
		DatabaseObject catobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.datasetCatalogHierarchy);

		String catid = createSuffix(obj,element);
		catobj.setIdentifier(catid);
		
		DatabaseObjectHierarchy comphier = createChemConnectDataStructure(catobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) comphier.getObject();
		
		DatasetCatalogHierarchy catalog = new DatasetCatalogHierarchy(structure);
		catalog.setIdentifier(catid);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(catalog);
		hierarchy.transferSubObjects(comphier);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, 
			String userid, String organizationid) {
		
		String uid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), userid);
		DatabaseObject dobj = new DatabaseObject(obj);
		dobj.setIdentifier(uid);
		
		String onelinedescription = "User's Catalog";
		DatabaseObjectHierarchy userhierarchy = createDatasetCatalogHierarchy(dobj);
		DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy) userhierarchy.getObject();
		usercatalog.setSimpleCatalogName(userid);
		setOneLineDescription(userhierarchy, onelinedescription);

		DatabaseObject aobj = new DatabaseObject(dobj);
		String orgsuffix = "orglink";
		String aid = DatasetCatalogHierarchy.createFullCatalogName(dobj.getIdentifier(), orgsuffix);
		aobj.setIdentifier(aid);
		
		String orgcatdescription = "Institute's Catalog";
		DatabaseObjectHierarchy orghierarchy = createDatasetCatalogHierarchy(aobj);
		DatasetCatalogHierarchy orgcatalog = (DatasetCatalogHierarchy) orghierarchy.getObject();
		orgcatalog.setSimpleCatalogName(organizationid);
		setOneLineDescription(orghierarchy, orgcatdescription);
		
		userhierarchy.addSubobject(orghierarchy);		
		
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		String cid = createSuffix(usercatalog,element);

		DatabaseObject mult1obj = new DatabaseObject(usercatalog);
		mult1obj.setIdentifier(cid);
		ChemConnectCompoundMultiple mult1 = new ChemConnectCompoundMultiple(mult1obj);
		DatabaseObjectHierarchy mhier1 = new DatabaseObjectHierarchy(mult1);
			
		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(mult1obj,
				"1", MetaDataKeywords.linkSubCatalog,
				orgcatalog.getIdentifier());
		
		DatabaseObjectHierarchy userlink =   fillDataObjectLink(mult1obj,
				"2", MetaDataKeywords.linkUser,
				userid);
		mhier1.addSubobject(subcatalog);
		mhier1.addSubobject(userlink);
		userhierarchy.addSubobject(mhier1);
		
		DatabaseObject subobj = new DatabaseObject(orgcatalog);
		subobj.setIdentifier(aid);
		String orglnkid = createSuffix(aobj,element);

		DatabaseObject sublnkobj = new DatabaseObject(subobj);
		sublnkobj.setIdentifier(orglnkid);
		ChemConnectCompoundMultiple mult2 = new ChemConnectCompoundMultiple(sublnkobj);
		DatabaseObjectHierarchy mhier2 = new DatabaseObjectHierarchy(mult2);

		DatabaseObjectHierarchy orglink =    fillDataObjectLink(sublnkobj,
				"1", MetaDataKeywords.linkOrganization,
				organizationid);
		mhier2.addSubobject(orglink);
		orghierarchy.addSubobject(mhier2);

		DataObjectLink subcataloglink = (DataObjectLink) subcatalog.getObject();
		DataObjectLink orglinklink    = (DataObjectLink) orglink.getObject();
		DataObjectLink userlinklink   = (DataObjectLink) userlink.getObject();
		
		usercatalog.addObjectDataLink(subcataloglink);
		usercatalog.addObjectDataLink(userlinklink);
		orgcatalog.addObjectDataLink(orglinklink);
		
		return userhierarchy;
	}

	static DatabaseObjectHierarchy fillOrganizationDescription(DatabaseObject obj, String organizationname) {
		
		DatabaseObjectHierarchy orghier = createOrganizationDescription(obj);
		OrganizationDescription descr = (OrganizationDescription) orghier.getObject();
		descr.setOrganizationName(organizationname);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);
		
		return hierarchy;
	}
	
	static DatabaseObjectHierarchy createOrganizationDescription(DatabaseObject obj) {
		DatabaseObject orgobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.organizationDescription);
		String orgid = createSuffix(obj,element);
		orgobj.setIdentifier(orgid);
		
		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(orgobj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
		
		OrganizationDescription descr = new OrganizationDescription(compound,
				"organization unit","organization class","organization","suborganization");
		descr.setIdentifier(orgid);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);
	
		return hierarchy;
	}
	
	
	static DatabaseObjectHierarchy fillDataObjectLink(DatabaseObject obj, 
			String linknumber,
			String concept, String linkedobj) {
		DatabaseObjectHierarchy linkhier = createDataObjectLink(obj);
		DataObjectLink userlink = (DataObjectLink) linkhier.getObject();
		
		userlink.setLinkConcept(concept);
		userlink.setDataStructure(linkedobj);
		String id = userlink.getIdentifier() + linknumber;
		userlink.setIdentifier(id);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);
		
		return hierarchy;
		
	}

	static DatabaseObjectHierarchy createDataObjectLink(DatabaseObject obj) {
		DatabaseObject linkobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		String linkid = createSuffix(obj,element);
		linkobj.setIdentifier(linkid);
		
		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
		
		DataObjectLink userlink = new DataObjectLink(compound,"link concept", "linked object");
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);
		
		return hierarchy;
	}

	static DatabaseObjectHierarchy createContactInfo(DatabaseObject obj) {
		DatabaseObject contactobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.contactInfoData);
		String contactid = createSuffix(obj,element);
		contactobj.setIdentifier(contactid);
		
		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
		
		ContactInfoData contact = new ContactInfoData(compound);
		contact.setIdentifier(contactid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(contact);
		
		return top;
	}
		
	static DatabaseObjectHierarchy createContactLocationInformation(DatabaseObject obj) {
		DatabaseObject contactobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.contactLocationInformation);
		String contactid = createSuffix(obj,element);
		contactobj.setIdentifier(contactid);
		
		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
		
		DatabaseObjectHierarchy gpshier = createGPSLocation(contactobj);
		GPSLocation gps = (GPSLocation) gpshier.getObject();
		
		ContactLocationInformation location = new ContactLocationInformation(compound,gps.getIdentifier());
		location.setIdentifier(contactid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(location);
		top.addSubobject(gpshier);

		return top;
	}
	
	public static DatabaseObjectHierarchy createGPSLocation(DatabaseObject obj) {
		DatabaseObject gpsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.gPSLocation);
		String gpsid = createSuffix(obj,element);
		gpsobj.setIdentifier(gpsid);
		
		GPSLocation gps = new GPSLocation(gpsobj);
		DatabaseObjectHierarchy gpshier = new DatabaseObjectHierarchy(gps);
		
		return gpshier;
	}
	
	
	static DatabaseObjectHierarchy fillPersonalDescription(DatabaseObject obj,
			NameOfPerson person, String userClassification) {
		
		DatabaseObjectHierarchy descrhier = createPersonalDescription(obj);
		PersonalDescription description = (PersonalDescription) descrhier.getObject();
		
		DatabaseObjectHierarchy personhier = descrhier.getSubObject(description.getNameOfPersonIdentifier());
		NameOfPerson subperson = (NameOfPerson) personhier.getObject();
		subperson.fill(subperson,person.getTitle(),person.getGivenName(),person.getFamilyName());
		
		description.setUserClassification(userClassification);
		return descrhier;
	}
	
	static DatabaseObjectHierarchy createPersonalDescription(DatabaseObject obj) {
		DatabaseObject personobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.personalDescription);
		String personid = createSuffix(obj,element);
		personobj.setIdentifier(personid);

		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
		
		DatabaseObjectHierarchy namehier = createNameOfPerson(personobj);
		NameOfPerson person = (NameOfPerson) namehier.getObject();
		
		PersonalDescription description = new PersonalDescription(compound, "user class",person.getIdentifier());
		description.setIdentifier(personid);
		
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(description);
		top.addSubobject(namehier);
		
		return top;
	}
	
	static DatabaseObjectHierarchy createNameOfPerson(DatabaseObject obj) {
		DatabaseObject personobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.nameOfPerson);
		String personid = createSuffix(obj,element);
		personobj.setIdentifier(personid);
		
		NameOfPerson person = new NameOfPerson(personobj,"title","firstname","lastname");
		DatabaseObjectHierarchy personhier = new DatabaseObjectHierarchy(person);
		
		return personhier;
	}
	
	static DatabaseObjectHierarchy createDescriptionDataData(DatabaseObject obj) {
		DatabaseObject descrobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.descriptionDataData);
		String descrid = createSuffix(obj,element);
		descrobj.setIdentifier(descrid);
		
		DatabaseObjectHierarchy comphier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) comphier.getObject();
		
		DatabaseObjectHierarchy pairhier = createPurposeConceptPair(descrobj);
		PurposeConceptPair pair = (PurposeConceptPair) pairhier.getObject();

		DatabaseObjectHierarchy keyshier = createSetOfKeywords(descrobj);
		SetOfKeywords keywords = (SetOfKeywords) keyshier.getObject();

		DescriptionDataData descr = new DescriptionDataData(compound,
				"one line", "full description", pair.getIdentifier(), 
				new Date(), "datatype", keywords.getIdentifier());
		descr.setIdentifier(descrid);
		
		DatabaseObjectHierarchy descrhier = new DatabaseObjectHierarchy(descr);
		descrhier.addSubobject(pairhier);
		descrhier.addSubobject(keyshier);
		
		return descrhier;
	}
	
	public static DatabaseObjectHierarchy createChemConnectCompoundDataStructure(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.chemConnectCompoundDataStructure);
		String compid = createSuffix(obj,element);
		compobj.setIdentifier(compid);
		
		ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(compobj,"no parent");
		compound.setParentLink(obj.getIdentifier());
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);
		
		return hierarchy;
	}
	
	
	static DatabaseObjectHierarchy createSetOfKeywords(DatabaseObject obj) {
		DatabaseObject keyobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.setOfKeywords);
		String keysid = createSuffix(obj,element);
		keyobj.setIdentifier(keysid);
		
		SetOfKeywords keywords = new SetOfKeywords(keyobj);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(keywords);
		
		return hierarchy;
	}
	
	static DatabaseObjectHierarchy createPurposeConceptPair(DatabaseObject obj) {
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.purposeConceptPair);
		DatabaseObject conceptobj = new DatabaseObject(obj);
		String conceptid = createSuffix(obj,element);
		conceptobj.setIdentifier(conceptid);
		ChemConnectCompoundDataStructure conceptcompound = new ChemConnectCompoundDataStructure(conceptobj,obj.getIdentifier());
		PurposeConceptPair pair = new PurposeConceptPair(conceptcompound, "no concept","no purpose");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(pair);
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy createDataSpecification(DatabaseObject obj) {
		DatabaseObject specobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.dataSpecification);
		String specsid = createSuffix(obj,element);
		specobj.setIdentifier(specsid);
		
		DatabaseObjectHierarchy concepthier = createPurposeConceptPair(specobj);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();
		DataSpecification spec = new DataSpecification(specobj, concept.getIdentifier());
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(spec);
		hierarchy.addSubobject(concepthier);
		return hierarchy;
	}
	public static DatabaseObjectHierarchy createParameterSpecification(DatabaseObject obj) {
		DatabaseObject specobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.parameterSpecification);
		String specsid = createSuffix(obj,element);
		specobj.setIdentifier(specsid);
		
		DatabaseObjectHierarchy valuehier = createValueUnits(specobj);
		DatabaseObjectHierarchy dspechier = createDataSpecification(obj);
		DataSpecification dspec = (DataSpecification) dspechier.getObject();
		ValueUnits value = (ValueUnits) valuehier.getObject();
		ParameterSpecification specs = new ParameterSpecification(dspec,"no uncertainty",value.getIdentifier());
		specs.setIdentifier(specsid);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(specs);
		hierarchy.addSubobject(valuehier);
		
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy createValueUnits(DatabaseObject obj) {
		DatabaseObject unitsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.valueUnits);
		String unitsid = createSuffix(obj,element);
		unitsobj.setIdentifier(unitsid);
		
		ValueUnits units = new ValueUnits(unitsobj,"no unit class","no value units");
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(units);
		
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy createAttributeInDataset(DatabaseObject obj) {
		DatabaseObject attrobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.attributeInDataset);
		String attrid = createSuffix(obj,element);
		attrobj.setIdentifier(attrid);
		
		AttributeInDataset attr = new AttributeInDataset(attrobj,"parametername");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(attr);
		
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy createParameterValue(DatabaseObject obj) {
		DatabaseObject valueobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.parameterValue);
		String valueid = createSuffix(obj,element);
		valueobj.setIdentifier(valueid);

		DatabaseObjectHierarchy attribute = createAttributeInDataset(obj);
		AttributeInDataset attr = (AttributeInDataset) attribute.getObject();
		DatabaseObjectHierarchy parameterspec = createParameterSpecification(valueobj);
		ParameterSpecification pspec = (ParameterSpecification) parameterspec.getObject();
		
		ParameterValue value = new ParameterValue(attr,pspec.getIdentifier(),"no value","no uncertainty");
		value.setIdentifier(valueid);
		
		DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(value);
		hier.addSubobject(attribute);
		hier.addSubobject(parameterspec);

		return hier;
	}
	
	public static DatabaseObjectHierarchy fillParameterSpecification(DatabaseObject obj, String parameter) {
		DatabaseObjectHierarchy valuehier = createParameterValue(obj);
		ParameterValue value = (ParameterValue) valuehier.getObject();
		
		String specID = value.getParameterSpec();
		DatabaseObjectHierarchy  spechier = valuehier.getSubObject(specID);
		ParameterSpecification parameterspec = (ParameterSpecification) spechier.getObject();
		
		String unitsID = parameterspec.getUnits();
		DatabaseObjectHierarchy  unitshier = spechier.getSubObject(unitsID);
		ValueUnits units = (ValueUnits) unitshier.getObject();
				
		String conceptID = parameterspec.getPurposeandconcept();
		DatabaseObjectHierarchy concepthier = spechier.getSubObject(conceptID);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();
		
		ConceptParsing.fillAnnotatedExample(parameter,units,concept,parameterspec,value);
		ConceptParsing.fillInProperties(parameter,units,concept,parameterspec,value);
		
		return valuehier;
	}
	
	static String createSuffix(DatabaseObject obj, String elementname, Map<String, DataElementInformation> elementmap) {
		DataElementInformation element = elementmap.get(elementname);
		return createSuffix(obj,element);
	}
	static String createSuffix(DatabaseObject obj, DataElementInformation element) {
		return obj.getIdentifier() + "-" + element.getSuffix();
	}
	
	static Map<String, DataElementInformation> createElementMap(String dataelement) {
		info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures = 
				DatasetOntologyParsing.subElementsOfStructure(dataelement);
		return createElementMap(structures);
	}
	static Map<String, DataElementInformation> createElementMap(info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures) {
		Map<String, DataElementInformation> elementmap = new HashMap<String, DataElementInformation>();
		for(DataElementInformation element : structures) {
			elementmap.put(element.getDataElementName(), element);
		}
		return elementmap;
	}
	
	
	
}
