package info.esblurock.reaction.core.server.initialization;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
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
import info.esblurock.reaction.chemconnect.core.data.dataset.DimensionParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.MeasurementParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfKeywords;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.ontology.OntologyKeys;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class CreateDefaultObjectsFactory {

	static DatabaseObjectHierarchy fillDescriptionDataData(DatabaseObject obj, String onelinedescription,
			String concept, String purpose) {
		DatabaseObjectHierarchy descrhier = createDescriptionDataData(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(onelinedescription);

		setOneLineDescription(descrhier, onelinedescription);
		setPurposeConceptPair(descrhier, concept, purpose);

		return descrhier;
	}

	public static DatabaseObjectHierarchy createIndividualInformation(DatabaseObject obj) {
		DatabaseObject indobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.individualInformation);
		String indid = createSuffix(obj, element);
		indobj.setIdentifier(indid);

		DatabaseObjectHierarchy structhier = createChemConnectDataStructure(indobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();

		DatabaseObjectHierarchy contact = createContactInfo(indobj);
		DatabaseObjectHierarchy location = createContactLocationInformation(indobj);
		DatabaseObjectHierarchy personalhier = createPersonalDescription(indobj);
		IndividualInformation info = new IndividualInformation(structure, contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(), personalhier.getObject().getIdentifier());
		info.setIdentifier(indid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(info);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(personalhier);
		top.transferSubObjects(structhier);

		return top;
	}

	public static DatabaseObjectHierarchy fillMinimalPersonDescription(DatabaseObject obj, String userClassification,
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

		String concept = "dataset:ChemConnectContactUser";
		String purpose = "dataset:PurposeUser";
		setPurposeConceptPair(infohier, concept, purpose);

		return infohier;
	}

	public static DatabaseObjectHierarchy createOrganization(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.organization);
		String compid = createSuffix(obj, element);
		compobj.setIdentifier(compid);
		DatabaseObjectHierarchy contact = createContactInfo(compobj);
		DatabaseObjectHierarchy location = createContactLocationInformation(compobj);
		DatabaseObjectHierarchy orgdescr = createOrganizationDescription(compobj);

		DatabaseObjectHierarchy structurehier = createChemConnectDataStructure(obj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
		Organization org = new Organization(structure, contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(), orgdescr.getObject().getIdentifier());
		org.setIdentifier(compid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(org);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(orgdescr);
		top.transferSubObjects(structurehier);

		return top;
	}

	public static DatabaseObjectHierarchy fillOrganization(DatabaseObject obj, String organizationname) {
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

	public static DatabaseObjectHierarchy createChemConnectCompoundMultiple(DatabaseObject obj,
			String type) {
		DataElementInformation refelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(type);
		String refid = createSuffix(obj, refelement);
		DatabaseObject refobj = new DatabaseObject(obj);
		refobj.setIdentifier(refid);
		ChemConnectCompoundMultiple refmult = new ChemConnectCompoundMultiple(refobj,refelement.getChemconnectStructure());
		DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(refmult);
		return refhier;
	}
	
	public static DatabaseObjectHierarchy createSubSystemDescription(DatabaseObject obj) {
		/*
		DatabaseObject subobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.subSystemDescription);
		String subid = createSuffix(obj, element);
		subobj.setIdentifier(subid);
*/
		DatabaseObjectHierarchy structhier = createChemConnectDataStructure(obj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();
		
		DatabaseObjectHierarchy spechier = createChemConnectCompoundMultiple(obj,OntologyKeys.observationSpecs);
		DatabaseObjectHierarchy paramshier = createChemConnectCompoundMultiple(obj,OntologyKeys.parameterValue);
		DatabaseObjectHierarchy subshier = createChemConnectCompoundMultiple(obj,OntologyKeys.subSystemDescription);
		
		SubSystemDescription device = new SubSystemDescription(structure,
				spechier.getObject().getIdentifier(),
				paramshier.getObject().getIdentifier(),
				subshier.getObject().getIdentifier()
				);
		device.setIdentifier(obj.getIdentifier());
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(device);
		hierarchy.transferSubObjects(structhier);
		hierarchy.addSubobject(spechier);
		hierarchy.addSubobject(paramshier);
		hierarchy.addSubobject(subshier);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillSubSystemDescription(DatabaseObject obj, String devicename,
			String purpose, String concept) {
		DatabaseObjectHierarchy hierarchy = createSubSystemDescription(obj);
		SubSystemDescription device = (SubSystemDescription) hierarchy.getObject();

		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(device.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(devicename);

		setOneLineDescription(hierarchy, devicename);
		setPurposeConceptPair(hierarchy, concept, purpose);
/*
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept(devicename);
		String paramid = device.getParameterValues();
		System.out.println(paramid);
		DatabaseObjectHierarchy paramsethier = hierarchy.getSubObject(paramid);
		ChemConnectCompoundMultiple parammulti = (ChemConnectCompoundMultiple) paramsethier.getObject();
	
		for (AttributeDescription attr : attrs) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(parammulti, attr.getAttributeName(),
					false);
			parammulti.addID(paramhier.getObject().getIdentifier());
			paramsethier.addSubobject(paramhier);
		}
		*/
		/*
		Set<String> subsystems = ConceptParsing.immediateSubSystems(devicename);
		Set<String> components = ConceptParsing.immediateComponents(devicename);
		String subsystemid = device.getParameterValues();
		System.out.println(subsystemid);
		DatabaseObjectHierarchy subsystemhier = hierarchy.getSubObject(subsystemid);
		ChemConnectCompoundMultiple subsystemmulti = (ChemConnectCompoundMultiple) subsystemhier.getObject();
		for(String subsystem : subsystems) {
			String simple = removeNamespace(subsystem);
			DatabaseObject subobj = new DatabaseObject(subsystemmulti);
			String id = subobj.getIdentifier() + "-" + simple;
			subobj.setIdentifier(id);
			DatabaseObjectHierarchy subhierarchy = fillSubSystemDescription(subobj,subsystem,concept,purpose);
			subsystemhier.addSubobject(subhierarchy);
			subsystemmulti.addID(subhierarchy.getObject().getIdentifier());
		}
		for(String component : components) {
			String simple = removeNamespace(component);
			DatabaseObject subobj = new DatabaseObject(subsystemmulti);
			String id = subobj.getIdentifier() + "-" + simple;
			subobj.setIdentifier(id);
			DatabaseObjectHierarchy subhierarchy = fillSubSystemDescription(subobj,component,concept,purpose);
			subsystemhier.addSubobject(subhierarchy);
			subsystemmulti.addID(subhierarchy.getObject().getIdentifier());			
		}
*/
		String obspecsetID = device.getObservationSpecs();
		DatabaseObjectHierarchy obspecset = hierarchy.getSubObject(obspecsetID);
		ChemConnectCompoundMultiple obspecmulti = (ChemConnectCompoundMultiple) obspecset.getObject();
		
		Set<String> observations = ConceptParsing.setOfObservationsForSubsystem(devicename);
		String measure = "<http://purl.org/linked-data/cube#measure>";

		DatabaseObject obsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.observationSpecs);
		String obsid = createSuffix(obj, element);
		obsobj.setIdentifier(obsid);

		for(String observation : observations) {
			DatabaseObject subobsobj =new DatabaseObject(obsobj);
			String specid = obsobj.getIdentifier() + "-" + removeNamespace(observation);
			subobsobj.setIdentifier(specid);
			DatabaseObjectHierarchy obsspechier = createObservationSpecification(subobsobj, obspecmulti.getIdentifier());
			ObservationSpecification specification = (ObservationSpecification) obsspechier.getObject();
			obspecmulti.addID(specification.getIdentifier());
			obspecset.addSubobject(obsspechier);
			
			String paramspecid = specification.getParameterSpecifications();
			DatabaseObjectHierarchy multihier = obsspechier.getSubObject(paramspecid);
			ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multihier.getObject();
			Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(measure, observation);
			for(AttributeDescription attr : measureset) {
				String id = specid + "-" + removeNamespace(attr.getAttributeName());
				DatabaseObject paramsub = new DatabaseObject(multi);
				paramsub.setIdentifier(id);
				DatabaseObjectHierarchy paramhier = createParameterSpecification(paramsub);
				ParameterSpecification param = (ParameterSpecification) paramhier.getObject();
				multihier.addSubobject(paramhier);

				DatabaseObjectHierarchy unithier = paramhier.getSubObject(param.getUnits());
				DatabaseObjectHierarchy purposehier = paramhier.getSubObject(param.getPurposeandconcept());
				ValueUnits units = (ValueUnits) unithier.getObject();
				PurposeConceptPair purposeandconcept = (PurposeConceptPair) purposehier.getObject();
				ConceptParsing.fillInProperties(attr.getAttributeName(), units, purposeandconcept);
			}

		}
		return hierarchy;
	}
	
	
	
	
	public static DatabaseObjectHierarchy createObservationSpecification(DatabaseObject obj, String parent) {
		/*
		DatabaseObject specobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.observationSpecs);
		String specid = createSuffix(obj, element);
		specobj.setIdentifier(specid);
		*/
		DatabaseObjectHierarchy parameterhier = createChemConnectCompoundMultiple(obj, OntologyKeys.parameterSpecification);
		DatabaseObjectHierarchy structhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) structhier.getObject();
		structure.setParentLink(parent);
		ObservationSpecification specification = new ObservationSpecification(structure,"dataset:VectorOfObservables",
				parameterhier.getObject().getIdentifier());
		specification.setIdentifier(obj.getIdentifier());
		DatabaseObjectHierarchy spechier = new DatabaseObjectHierarchy(specification);
		spechier.addSubobject(parameterhier);
		
		return spechier;
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

	public static DatabaseObjectHierarchy fillDatasetCatalogHierarchy(DatasetCatalogHierarchy topcatalog,
			DatabaseObject obj, String id, String onelinedescription) {
		DataElementInformation linkelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);

		DatabaseObject aobj = new DatabaseObject(obj);
		String aid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), id);
		aobj.setIdentifier(aid);

		DatabaseObjectHierarchy cathierarchy = createDatasetCatalogHierarchy(aobj);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) cathierarchy.getObject();
		setOneLineDescription(cathierarchy, onelinedescription);
		
		
		DatabaseObjectHierarchy lstructurehier = cathierarchy.getSubObject(catalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple lstructure = (ChemConnectCompoundMultiple) lstructurehier.getObject();
		int num = lstructure.getIds().size();
		String linknum = Integer.toString(num + 1);
		DatabaseObject lobj = new DatabaseObject(obj);
		String oid = createSuffix(lobj, linkelement);
		lobj.setIdentifier(oid + linknum);
		
		DatabaseObjectHierarchy linkhier = fillDataObjectLink(lstructure,linknum,MetaDataKeywords.linkSubCatalog,
				catalog.getIdentifier());
		DataObjectLink lnk = (DataObjectLink) linkhier.getObject();
		lstructure.addID(lnk.getIdentifier());
		lstructurehier.addSubobject(linkhier);
		
		return cathierarchy;
	}

	/*
	 * public static DatabaseObjectHierarchy
	 * createCataogHierarchyForUser(DatabaseObject obj, String userid, String
	 * organizationid) { DataElementInformation linkelement =
	 * DatasetOntologyParsing.getSubElementStructureFromIDObject(OntologyKeys.
	 * dataObjectLink);
	 * 
	 * String uid =
	 * DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), userid);
	 * DatabaseObject dobj = new DatabaseObject(obj); dobj.setIdentifier(uid);
	 * 
	 * DatabaseObjectHierarchy userhierarchy = createDatasetCatalogHierarchy(dobj);
	 * 
	 * DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy)
	 * userhierarchy.getObject();
	 * 
	 * String onelinedescription = "User's Catalog";
	 * setOneLineDescription(userhierarchy, onelinedescription); String concept =
	 * "dataset:ChemConnectConceptSubCatalog"; String purpose =
	 * "dataset:ChemConnectDefineSubCatagory"; setPurposeConceptPair(userhierarchy,
	 * concept, purpose);
	 * 
	 * DatabaseObject aobj = new DatabaseObject(dobj); String orgsuffix = "orglink";
	 * String aid =
	 * DatasetCatalogHierarchy.createFullCatalogName(dobj.getIdentifier(),
	 * orgsuffix); aobj.setIdentifier(aid); String orgcatdescription =
	 * "Institute's Catalog";
	 * 
	 * return userhierarchy; }
	 */
	public static DatabaseObjectHierarchy createChemConnectDataStructure(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.chemConnectDataStructure);
		String compid = createSuffix(obj, element);
		compobj.setIdentifier(compid);

		DatabaseObjectHierarchy descrhier = createDescriptionDataData(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		
		
		DataElementInformation refelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataSetReference);
		String refid = createSuffix(obj, refelement);
		DatabaseObject refobj = new DatabaseObject(compobj);
		refobj.setIdentifier(refid);
		DataElementInformation lnkelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		ChemConnectCompoundMultiple refmult = new ChemConnectCompoundMultiple(refobj,refelement.getChemconnectStructure());
		DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(refmult);

		String lnkid = createSuffix(obj, lnkelement);
		DatabaseObject lnkobj = new DatabaseObject(compobj);
		lnkobj.setIdentifier(lnkid);
		ChemConnectCompoundMultiple lnkmult = new ChemConnectCompoundMultiple(lnkobj,lnkelement.getChemconnectStructure());
		DatabaseObjectHierarchy lnkhier = new DatabaseObjectHierarchy(lnkmult);

		ChemConnectDataStructure compound = new ChemConnectDataStructure(compobj, 
				descr.getIdentifier(), refid,lnkid);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);
		hierarchy.addSubobject(descrhier);
		hierarchy.addSubobject(refhier);
		hierarchy.addSubobject(lnkhier);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy createDatasetCatalogHierarchy(DatabaseObject obj) {
		DatabaseObject catobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.datasetCatalogHierarchy);

		String catid = createSuffix(obj, element);
		catobj.setIdentifier(catid);

		DatabaseObjectHierarchy comphier = createChemConnectDataStructure(catobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) comphier.getObject();

		DatasetCatalogHierarchy catalog = new DatasetCatalogHierarchy(structure);
		catalog.setIdentifier(catid);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(catalog);
		hierarchy.transferSubObjects(comphier);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, String userid,
			String organizationid) {

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

		DatabaseObjectHierarchy multilnkhier = userhierarchy.getSubObject(usercatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(multilnk, "1", MetaDataKeywords.linkSubCatalog,
				orgcatalog.getIdentifier());
		DatabaseObjectHierarchy userlink = fillDataObjectLink(multilnk, "2", MetaDataKeywords.linkUser, userid);
		multilnk.addID(subcatalog.getObject().getIdentifier());
		multilnk.addID(userlink.getObject().getIdentifier());
		multilnkhier.addSubobject(subcatalog);
		multilnkhier.addSubobject(userlink);
		
		DatabaseObjectHierarchy multiorghier = userhierarchy.getSubObject(orgcatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multiorg = (ChemConnectCompoundMultiple) multiorghier.getObject();
		DatabaseObjectHierarchy orglink = fillDataObjectLink(multiorg, "1", MetaDataKeywords.linkOrganization,
				organizationid);
		multiorghier.addSubobject(orglink);
		multiorg.addID(orglink.getObject().getIdentifier());

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
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.organizationDescription);
		String orgid = createSuffix(obj, element);
		orgobj.setIdentifier(orgid);

		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(orgobj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

		OrganizationDescription descr = new OrganizationDescription(compound, "organization unit", "organization class",
				"organization", "suborganization");
		descr.setIdentifier(orgid);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);

		return hierarchy;
	}

	static DatabaseObjectHierarchy fillDataObjectLink(DatabaseObject obj, String linknumber, String concept,
			String linkedobj) {
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
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		String linkid = createSuffix(obj, element);
		linkobj.setIdentifier(linkid);

		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

		DataObjectLink userlink = new DataObjectLink(compound, "link concept", "linked object");

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);

		return hierarchy;
	}

	static DatabaseObjectHierarchy createContactInfo(DatabaseObject obj) {
		DatabaseObject contactobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.contactInfoData);
		String contactid = createSuffix(obj, element);
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
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.contactLocationInformation);
		String contactid = createSuffix(obj, element);
		contactobj.setIdentifier(contactid);

		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

		DatabaseObjectHierarchy gpshier = createGPSLocation(contactobj);
		GPSLocation gps = (GPSLocation) gpshier.getObject();

		ContactLocationInformation location = new ContactLocationInformation(compound, gps.getIdentifier());
		location.setIdentifier(contactid);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(location);
		top.addSubobject(gpshier);

		return top;
	}

	public static DatabaseObjectHierarchy createGPSLocation(DatabaseObject obj) {
		DatabaseObject gpsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.gPSLocation);
		String gpsid = createSuffix(obj, element);
		gpsobj.setIdentifier(gpsid);

		GPSLocation gps = new GPSLocation(gpsobj);
		DatabaseObjectHierarchy gpshier = new DatabaseObjectHierarchy(gps);

		return gpshier;
	}

	static DatabaseObjectHierarchy fillPersonalDescription(DatabaseObject obj, NameOfPerson person,
			String userClassification) {

		DatabaseObjectHierarchy descrhier = createPersonalDescription(obj);
		PersonalDescription description = (PersonalDescription) descrhier.getObject();

		DatabaseObjectHierarchy personhier = descrhier.getSubObject(description.getNameOfPersonIdentifier());
		NameOfPerson subperson = (NameOfPerson) personhier.getObject();
		subperson.fill(subperson, person.getTitle(), person.getGivenName(), person.getFamilyName());

		description.setUserClassification(userClassification);
		return descrhier;
	}

	static DatabaseObjectHierarchy createPersonalDescription(DatabaseObject obj) {
		DatabaseObject personobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.personalDescription);
		String personid = createSuffix(obj, element);
		personobj.setIdentifier(personid);

		DatabaseObjectHierarchy compoundhier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

		DatabaseObjectHierarchy namehier = createNameOfPerson(personobj);
		NameOfPerson person = (NameOfPerson) namehier.getObject();

		PersonalDescription description = new PersonalDescription(compound, "user class", person.getIdentifier());
		description.setIdentifier(personid);

		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(description);
		top.addSubobject(namehier);

		return top;
	}

	static DatabaseObjectHierarchy createNameOfPerson(DatabaseObject obj) {
		DatabaseObject personobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.nameOfPerson);
		String personid = createSuffix(obj, element);
		personobj.setIdentifier(personid);

		NameOfPerson person = new NameOfPerson(personobj, "title", "firstname", "lastname");
		DatabaseObjectHierarchy personhier = new DatabaseObjectHierarchy(person);

		return personhier;
	}

	static DatabaseObjectHierarchy createDescriptionDataData(DatabaseObject obj) {
		DatabaseObject descrobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.descriptionDataData);
		String descrid = createSuffix(obj, element);
		descrobj.setIdentifier(descrid);

		DatabaseObjectHierarchy comphier = createChemConnectCompoundDataStructure(obj);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) comphier.getObject();

		DatabaseObjectHierarchy pairhier = createPurposeConceptPair(descrobj);
		PurposeConceptPair pair = (PurposeConceptPair) pairhier.getObject();

		DatabaseObjectHierarchy keyshier = createSetOfKeywords(descrobj);
		SetOfKeywords keywords = (SetOfKeywords) keyshier.getObject();

		DescriptionDataData descr = new DescriptionDataData(compound, "one line", "full description",
				pair.getIdentifier(), new Date(), "datatype", keywords.getIdentifier());
		descr.setIdentifier(descrid);
		DatabaseObjectHierarchy descrhier = new DatabaseObjectHierarchy(descr);
		descrhier.addSubobject(pairhier);
		descrhier.addSubobject(keyshier);

		return descrhier;
	}

	public static DatabaseObjectHierarchy createChemConnectCompoundDataStructure(DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.chemConnectCompoundDataStructure);
		String compid = createSuffix(obj, element);
		compobj.setIdentifier(compid);

		ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(compobj, "no parent");
		compound.setParentLink(obj.getIdentifier());
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);

		return hierarchy;
	}

	static DatabaseObjectHierarchy createSetOfKeywords(DatabaseObject obj) {
		DatabaseObject keyobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.setOfKeywords);
		String keysid = createSuffix(obj, element);
		keyobj.setIdentifier(keysid);

		SetOfKeywords keywords = new SetOfKeywords(keyobj);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(keywords);

		return hierarchy;
	}

	static DatabaseObjectHierarchy createPurposeConceptPair(DatabaseObject obj) {
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.purposeConceptPair);
		DatabaseObject conceptobj = new DatabaseObject(obj);
		String conceptid = createSuffix(obj, element);
		conceptobj.setIdentifier(conceptid);
		ChemConnectCompoundDataStructure conceptcompound = new ChemConnectCompoundDataStructure(conceptobj,
				obj.getIdentifier());
		PurposeConceptPair pair = new PurposeConceptPair(conceptcompound, "no concept", "no purpose");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(pair);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy createDataSpecification(DatabaseObject obj) {
		
		DatabaseObject specobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataSpecification);
		String specsid = createSuffix(obj, element);
		specobj.setIdentifier(specsid);
		 
		DatabaseObjectHierarchy concepthier = createPurposeConceptPair(obj);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();
		DataSpecification spec = new DataSpecification(specobj, concept.getIdentifier());
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(spec);
		hierarchy.addSubobject(concepthier);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillSetOfObservations(DatabaseObject obj, String parameter, String oneline,
			String concept, String purpose) {
		String measure = "<http://purl.org/linked-data/cube#measure>";
		String dimension = "<http://purl.org/linked-data/cube#dimension>";

		Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(measure, parameter);
		Set<AttributeDescription> dimensionset = ConceptParsing.propertyInConcept(dimension, parameter);

		DatabaseObjectHierarchy sethier = createSetOfObservationValues(obj);
		SetOfObservationValues set = (SetOfObservationValues) sethier.getObject();

		setPurposeConceptPair(sethier, concept, purpose);
		setOneLineDescription(sethier, oneline);
		
		System.out.println(set.toString());
		
		String measureid = set.getMeasurementValues();
		System.out.println(measureid);
		DatabaseObjectHierarchy measurehier = sethier.getSubObject(measureid);
		ChemConnectCompoundMultiple measremul = (ChemConnectCompoundMultiple) measurehier.getObject();
		String dimensionid = set.getDimensionValues();
		DatabaseObjectHierarchy dimensionhier = sethier.getSubObject(dimensionid);
		ChemConnectCompoundMultiple dimensionmult = (ChemConnectCompoundMultiple) dimensionhier.getObject();
		
		set.setDimensionValues(dimensionid);

		for (AttributeDescription attr : measureset) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(measremul, attr.getAttributeName(),
					false);
			measremul.addID(paramhier.getObject().getIdentifier());
			measurehier.addSubobject(paramhier);
		}

		for (AttributeDescription attr : dimensionset) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(dimensionmult,
					attr.getAttributeName(), true);
			dimensionmult.addID(paramhier.getObject().getIdentifier());
			dimensionhier.addSubobject(paramhier);
		}
		sethier.addSubobject(measurehier);
		sethier.addSubobject(dimensionhier);

		return sethier;
	}

	private static DatabaseObjectHierarchy createSetOfObservationValues(DatabaseObject obj) {
		DatabaseObject obsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.setOfObservationValues);
		String obsid = createSuffix(obj, element);
		obsobj.setIdentifier(obsid);

		DatabaseObjectHierarchy comphier = createChemConnectDataStructure(obsobj);
		ChemConnectDataStructure structure = (ChemConnectDataStructure) comphier.getObject();
		SetOfObservationValues set = new SetOfObservationValues(structure);
		set.setIdentifier(obsid);
		
		DataElementInformation refelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataSetReference);
		DatabaseObject refobj = new DatabaseObject(obj);
		String refid = createSuffix(obsobj, refelement);
		refobj.setIdentifier(refid);
		ChemConnectCompoundMultiple refmult = new ChemConnectCompoundMultiple(refobj,refelement.getChemconnectStructure());
		DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(refmult);

		DataElementInformation lnkelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);
		DatabaseObject lnkobj = new DatabaseObject(obj);
		String lnkid = createSuffix(obsobj, lnkelement);
		lnkobj.setIdentifier(lnkid);
		ChemConnectCompoundMultiple lnkmult = new ChemConnectCompoundMultiple(lnkobj,lnkelement.getChemconnectStructure());
		DatabaseObjectHierarchy lnkhier = new DatabaseObjectHierarchy(lnkmult);

		DataElementInformation melement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.measurementParameterValue);
		DatabaseObject measureobj = new DatabaseObject(obsobj);
		String measureid = createSuffix(obsobj, melement);
		measureobj.setIdentifier(measureid);
		ChemConnectCompoundMultiple measremult = new ChemConnectCompoundMultiple(measureobj,melement.getChemconnectStructure());
		DatabaseObjectHierarchy measurehier = new DatabaseObjectHierarchy(measremult);
		set.setMeasurementValues(measureid);

		DataElementInformation delement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dimensionParameterValue);
		DatabaseObject dimensionobj = new DatabaseObject(obsobj);
		String dimensionid = createSuffix(obsobj, delement);
		dimensionobj.setIdentifier(dimensionid);
		ChemConnectCompoundMultiple dimensionmult = new ChemConnectCompoundMultiple(dimensionobj,delement.getChemconnectStructure());
		DatabaseObjectHierarchy dimensionhier = new DatabaseObjectHierarchy(dimensionmult);
		set.setDimensionValues(dimensionid);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
		hierarchy.addSubobject(lnkhier);
		hierarchy.addSubobject(refhier);
		hierarchy.addSubobject(measurehier);
		hierarchy.addSubobject(dimensionhier);
		hierarchy.transferSubObjects(comphier);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy createParameterSpecification(DatabaseObject obj) {
		DatabaseObject specobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.parameterSpecification);
		String specsid = createSuffix(obj, element);
		specobj.setIdentifier(specsid);

		DatabaseObjectHierarchy valuehier = createValueUnits(specobj);
		DatabaseObjectHierarchy dspechier = createDataSpecification(specobj);
		DataSpecification dspec = (DataSpecification) dspechier.getObject();
		ValueUnits value = (ValueUnits) valuehier.getObject();
		ParameterSpecification specs = new ParameterSpecification(dspec, "no uncertainty", value.getIdentifier());
		specs.setIdentifier(specsid);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(specs);
		hierarchy.addSubobject(valuehier);
		hierarchy.transferSubObjects(dspechier);
		hierarchy.transferSubObjects(valuehier);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy createValueUnits(DatabaseObject obj) {
		DatabaseObject unitsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.valueUnits);
		String unitsid = createSuffix(obj, element);
		unitsobj.setIdentifier(unitsid);

		ValueUnits units = new ValueUnits(unitsobj, "no unit class", "no value units");

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(units);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy createAttributeInDataset(DatabaseObject obj) {
		DatabaseObject attrobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.attributeInDataset);
		String attrid = createSuffix(obj, element);
		attrobj.setIdentifier(attrid);

		AttributeInDataset attr = new AttributeInDataset(attrobj, "parametername");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(attr);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy createMeasurementParameterValue(DatabaseObject obj) {
		DatabaseObject valueobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.measurementParameterValue);
		String valueid = createSuffix(obj, element);
		valueobj.setIdentifier(valueid);
		return fillParameterValue(obj, false);
	}

	public static DatabaseObjectHierarchy createDimensionParameterValue(DatabaseObject obj) {
		DatabaseObject valueobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dimensionParameterValue);
		String valueid = createSuffix(obj, element);
		valueobj.setIdentifier(valueid);
		return fillParameterValue(obj, true);
	}

	public static DatabaseObjectHierarchy fillParameterValue(DatabaseObject valueobj, boolean dimension) {
		DatabaseObjectHierarchy attribute = createAttributeInDataset(valueobj);
		AttributeInDataset attr = (AttributeInDataset) attribute.getObject();
		DatabaseObjectHierarchy parameterspec = createParameterSpecification(valueobj);
		ParameterSpecification pspec = (ParameterSpecification) parameterspec.getObject();
		ParameterValue value = null;
		if (dimension) {
			value = new DimensionParameterValue(attr, "no value", "no uncertainty", pspec.getIdentifier());
		} else {
			value = new MeasurementParameterValue(attr, "no value", "no uncertainty", pspec.getIdentifier());
		}
		value.setIdentifier(valueobj.getIdentifier());

		DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(value);
		hier.addSubobject(parameterspec);
		hier.transferSubObjects(attribute);

		return hier;
	}

	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObject obj, String parameter,
			boolean dimension) {
		DatabaseObject subobj = new DatabaseObject(obj);
		int pos = parameter.indexOf(":");
		String id = obj.getIdentifier() + "-" + parameter.substring(pos+1);
		subobj.setIdentifier(id);

		DatabaseObjectHierarchy valuehier = null;
		if (dimension) {
			valuehier = createDimensionParameterValue(subobj);
		} else {
			valuehier = createMeasurementParameterValue(subobj);
		}
		ParameterValue value = (ParameterValue) valuehier.getObject();
		String specID = value.getParameterSpec();
		DatabaseObjectHierarchy spechier = valuehier.getSubObject(specID);
		ParameterSpecification parameterspec = (ParameterSpecification) spechier.getObject();

		String unitsID = parameterspec.getUnits();
		DatabaseObjectHierarchy unitshier = spechier.getSubObject(unitsID);
		ValueUnits units = (ValueUnits) unitshier.getObject();

		String conceptID = parameterspec.getPurposeandconcept();

		DatabaseObjectHierarchy concepthier = spechier.getSubObject(conceptID);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();

		ConceptParsing.fillAnnotatedExample(parameter, units, concept, parameterspec, value);
		ConceptParsing.fillInProperties(parameter, units, concept);
		value.setParameterLabel(parameter);

		return valuehier;
	}

	static String createSuffix(DatabaseObject obj, String elementname, Map<String, DataElementInformation> elementmap) {
		DataElementInformation element = elementmap.get(elementname);
		return createSuffix(obj, element);
	}

	static String createSuffix(DatabaseObject obj, DataElementInformation element) {
		return obj.getIdentifier() + "-" + element.getSuffix();
	}

	static Map<String, DataElementInformation> createElementMap(String dataelement) {
		info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures = DatasetOntologyParsing
				.subElementsOfStructure(dataelement);
		return createElementMap(structures);
	}

	static Map<String, DataElementInformation> createElementMap(
			info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure structures) {
		Map<String, DataElementInformation> elementmap = new HashMap<String, DataElementInformation>();
		for (DataElementInformation element : structures) {
			elementmap.put(element.getDataElementName(), element);
		}
		return elementmap;
	}
	public static String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String ans = name;
		if(pos >= 0) {
			ans = name.substring(pos+1);
		}
		return ans;
	}

}
