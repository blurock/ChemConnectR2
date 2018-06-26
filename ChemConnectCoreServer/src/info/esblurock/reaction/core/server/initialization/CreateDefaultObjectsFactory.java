package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.chemconnect.core.data.dataset.AttributeInDataset;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.dataset.DimensionParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.MeasurementParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterValue;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectMethodology;
import info.esblurock.reaction.ontology.OntologyKeys;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class CreateDefaultObjectsFactory {
	
	public static String noUnitClassS = "no unit class";
	public static String noValueUnitsS = "no value units";
	public static String noPurposeS = "no purpose";
	public static String noConceptS = "no concept";

	static DatabaseObjectHierarchy fillDescriptionDataData(DatabaseObject obj, String onelinedescription,
			String concept, String purpose) {
		DatabaseObjectHierarchy descrhier = InterpretData.DescriptionDataData.createEmptyObject(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(onelinedescription);

		setOneLineDescription(descrhier, onelinedescription);
		setPurposeConceptPair(descrhier, concept, purpose);

		return descrhier;
	}

	public static DatabaseObjectHierarchy fillMinimalPersonDescription(DatabaseObject obj, String userClassification,
			NameOfPerson person) {
		DatabaseObjectHierarchy infohier = InterpretData.IndividualInformation.createEmptyObject(obj);
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

	public static DatabaseObjectHierarchy fillOrganization(DatabaseObject obj, String organizationname) {
		String concept = MetaDataKeywords.conceptContact;
		String purpose = MetaDataKeywords.purposeOrganization;

		DatabaseObjectHierarchy orghier = InterpretData.Organization.createEmptyObject(obj);
		Organization org = (Organization) orghier.getObject();

		DatabaseObjectHierarchy orgdescrhier = orghier.getSubObject(org.getOrganizationDescriptionID());
		OrganizationDescription orgdescr = (OrganizationDescription) orgdescrhier.getObject();
		orgdescr.setOrganizationName(organizationname);

		setOneLineDescription(orghier, organizationname);
		setPurposeConceptPair(orghier, concept, purpose);

		return orghier;
	}

	public static DatabaseObjectHierarchy fillMethodologyDefinition(DatabaseObject obj, 
			String methodologyS, String title) {
		DatabaseObjectHierarchy methodhier = InterpretData.ChemConnectMethodology.createEmptyObject(obj);
				//createMethodologyDefinition(obj);
		ChemConnectMethodology methodology = (ChemConnectMethodology) methodhier.getObject();
		methodology.setMethodologyType(methodologyS);
		
		String obssetid = methodology.getObservationSpecs();
		DatabaseObjectHierarchy obssethier = methodhier.getSubObject(obssetid);
		fillObservationSpecification(methodologyS,obssethier);
		
		String paramid = methodology.getParameterValues();
		DatabaseObjectHierarchy paramsethier = methodhier.getSubObject(paramid);
		ChemConnectCompoundMultiple parammulti = (ChemConnectCompoundMultiple) paramsethier.getObject();
		
		PurposeConceptPair pair = new PurposeConceptPair();
		ConceptParsing.fillInPurposeConceptPair(methodologyS, pair);
		setPurposeConceptPair(methodhier, pair.getConcept(), pair.getPurpose());
		setOneLineDescription(methodhier, title);
		
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept(methodologyS);
		for (AttributeDescription attr : attrs) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(parammulti, attr.getAttributeName());
			parammulti.addID(paramhier.getObject().getIdentifier());
			paramsethier.addSubobject(paramhier);
		}
		return methodhier;
	}
	public static DatabaseObjectHierarchy fillSubSystemDescription(DatabaseObject obj, String devicename,
			String purpose, String concept) {
		DatabaseObjectHierarchy hierarchy = InterpretData.SubSystemDescription.createEmptyObject(obj);
		SubSystemDescription device = (SubSystemDescription) hierarchy.getObject();
		
		device.setSubSystemType(devicename);

		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(device.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(devicename);

		setOneLineDescription(hierarchy, devicename);
		setPurposeConceptPair(hierarchy, concept, purpose);
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept(devicename);
		String paramid = device.getParameterValues();
		DatabaseObjectHierarchy paramsethier = hierarchy.getSubObject(paramid);
		ChemConnectCompoundMultiple parammulti = (ChemConnectCompoundMultiple) paramsethier.getObject();
	
		for (AttributeDescription attr : attrs) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(parammulti, attr.getAttributeName());
			parammulti.addID(paramhier.getObject().getIdentifier());
			paramsethier.addSubobject(paramhier);
		}

		Set<String> subsystems = ConceptParsing.immediateSubSystems(devicename);
		Set<String> components = ConceptParsing.immediateComponents(devicename);
		String subsystemid = device.getSubSystems();
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


		String obspecsetID = device.getObservationSpecs();
		DatabaseObjectHierarchy obspecset = hierarchy.getSubObject(obspecsetID);
		

		DatabaseObject obsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.observationSpecs);
		String obsid = createSuffix(obj, element);
		obsobj.setIdentifier(obsid);
		fillObservationSpecification(devicename,obspecset);
		return hierarchy;
	}
	
	public static void fillObservationSpecification(String setofobservationsS,
			DatabaseObjectHierarchy obspecset) {
		ChemConnectCompoundMultiple obspecmulti = (ChemConnectCompoundMultiple) obspecset.getObject();
		String obsspecID = obspecmulti.getIdentifier();
		
		
		Set<String> observations = ConceptParsing.setOfObservationsForSubsystem(setofobservationsS);
		for(String observation : observations) {
			DatabaseObject subobsobj =new DatabaseObject(obspecmulti);
			String specid = subobsobj.getIdentifier() + "-" + removeNamespace(observation);
			subobsobj.setIdentifier(specid);
			DatabaseObjectHierarchy obsspechier = InterpretData.ObservationSpecification.createEmptyObject(subobsobj);
			ObservationSpecification specification = (ObservationSpecification) obsspechier.getObject();
			specification.setParentLink(obsspecID);
			obspecmulti.addID(specification.getIdentifier());
			obspecset.addSubobject(obsspechier);
			specification.setObservationLabel(observation);
			String measurespecid = specification.getMeasureSpecifications();
			fillMeasurementValues(observation, obsspechier, measurespecid);
			String dimensionspecid = specification.getDimensionSpecifications();
			fillDimensionValues(observation, obsspechier, dimensionspecid);
		}
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

		String pairID = descr.getSourceConcept();
		DatabaseObjectHierarchy pairhierarchy = descrhierarchy.getSubObject(pairID);
		PurposeConceptPair pair = (PurposeConceptPair) pairhierarchy.getObject();
		pair.setConcept(concept);
		pair.setPurpose(purpose);
	}
/*
 *  1. Create link ID (aobj.getIdentifier())
 *  2. Set up new link   (cathierarchy -> catalog)
 *  3. Set in one line description (setOneLineDescription)
 *  4. Read in top catagory link list (toplinkstructure from topcatalog.getChemConnectObjectLink())
 *  5. Determine number of links already in list (linknum)
 *  6. Create an object link (linkhier -> lnk)
 */
	public static DatabaseObjectHierarchy fillDatasetCatalogHierarchy(DatasetCatalogHierarchy topcatalog,
			DatabaseObject obj, String id, String onelinedescription) throws IOException {
		DataElementInformation linkelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.dataObjectLink);

		DatabaseObject aobj = new DatabaseObject(obj);
		String aid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), id);
		aobj.setIdentifier(aid);
		aobj.nullKey();

		DatabaseObjectHierarchy cathierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(aobj);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) cathierarchy.getObject();
		catalog.setSimpleCatalogName(id);
		setOneLineDescription(cathierarchy, onelinedescription);
				
		// Get object link list (multiple) from top catalog
		String classname = ChemConnectCompoundMultiple.class.getCanonicalName();
		ChemConnectCompoundMultiple toplinkstructure = (ChemConnectCompoundMultiple) QueryBase.getDatabaseObjectFromIdentifier(classname,
				topcatalog.getChemConnectObjectLink());
		int num = toplinkstructure.getIds().size();
		String linknum = Integer.toString(num + 1);
		
		
		DatabaseObjectHierarchy linkhier = fillDataObjectLink(toplinkstructure,linknum,MetaDataKeywords.linkSubCatalog,
				catalog.getIdentifier());
		DataObjectLink lnk = (DataObjectLink) linkhier.getObject();
		
		toplinkstructure.addID(lnk.getIdentifier());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(cathierarchy);
		DatabaseWriteBase.writeDatabaseObject(toplinkstructure);
		DatabaseWriteBase.writeDatabaseObject(lnk);
		return cathierarchy;
	}


	public static String userCatalogHierarchyID(String username) {
		String uid = DatasetCatalogHierarchy.createFullCatalogName("Catalog", username);
		DatabaseObject catobj = new DatabaseObject(uid,username,username,"");
		DataElementInformation usrelement = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.individualInformation);
		String indid = createSuffix(catobj, usrelement);
		catobj.setIdentifier(indid);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.datasetCatalogHierarchy);
		String catid = createSuffix(catobj, element);
		return catid;
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, String userid,
			String orgcatalog) {
		String uid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), userid);
		DatabaseObject dobj = new DatabaseObject(obj);
		dobj.setIdentifier(uid);
		dobj.nullKey();

		String onelinedescription = "User's Catalog";
		DatabaseObjectHierarchy userhierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(dobj);
		DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy) userhierarchy.getObject();
		usercatalog.setSimpleCatalogName(userid);
		setOneLineDescription(userhierarchy, onelinedescription);
		
		DatabaseObjectHierarchy multilnkhier = userhierarchy.getSubObject(usercatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(multilnk, "1", MetaDataKeywords.linkSubCatalog,
				orgcatalog);
		DatabaseObjectHierarchy userlink = fillDataObjectLink(multilnk, "2", MetaDataKeywords.linkUser, userid);
		multilnk.addID(subcatalog.getObject().getIdentifier());
		multilnk.addID(userlink.getObject().getIdentifier());
		multilnkhier.addSubobject(subcatalog);
		multilnkhier.addSubobject(userlink);

		return userhierarchy;
	}
	
	public static DatabaseObjectHierarchy fillCataogHierarchyForOrganization(DatabaseObject obj,
			String organizationid, String orglinkid) {		
		
		DatabaseObject aobj = new DatabaseObject(obj);
		String orgsuffix = "orglink";
		String aid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), orgsuffix);
		aobj.setIdentifier(aid);
		aobj.nullKey();
		String orgcatdescription = "Institute's Catalog";
		DatabaseObjectHierarchy orghierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(aobj);
		DatasetCatalogHierarchy orgcatalog = (DatasetCatalogHierarchy) orghierarchy.getObject();
		orgcatalog.setSimpleCatalogName(organizationid);
		setOneLineDescription(orghierarchy, orgcatdescription);

		
		DatabaseObjectHierarchy multiorghier = orghierarchy.getSubObject(orgcatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multiorg = (ChemConnectCompoundMultiple) multiorghier.getObject();
		DatabaseObjectHierarchy orglink = fillDataObjectLink(multiorg, "1", MetaDataKeywords.linkOrganization,
				orglinkid);
		multiorghier.addSubobject(orglink);
		multiorg.addID(orglink.getObject().getIdentifier());

		return orghierarchy;
	}
	static public void createAndWriteDefaultUserOrgAndCatagories(	String username, String access, String owner,
			String orgname, String title, String sourceID) {
		DatabaseObject obj = new DatabaseObject(username, access, owner, sourceID);
		NameOfPerson person = new NameOfPerson(obj, "", "", username);
		DatabaseObjectHierarchy user = CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj, username, person);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(user);

		DatabaseObject orgobj = new DatabaseObject(orgname, access, owner, sourceID);
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillOrganization(orgobj, title);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(org);

		DatabaseObject catobj = new DatabaseObject("Catalog", access, owner, sourceID);

		DatabaseObjectHierarchy orgcat = CreateDefaultObjectsFactory.fillCataogHierarchyForOrganization(catobj,
				"BlurockConsultingAB", org.getObject().getIdentifier());

		DatabaseObjectHierarchy usercat = CreateDefaultObjectsFactory.fillCataogHierarchyForUser(catobj,
				user.getObject().getIdentifier(), orgcat.getObject().getIdentifier());
		
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(orgcat);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(usercat);
		
	}

	static DatabaseObjectHierarchy fillOrganizationDescription(DatabaseObject obj, String organizationname) {

		DatabaseObjectHierarchy orghier = InterpretData.OrganizationDescription.createEmptyObject(obj);
		OrganizationDescription descr = (OrganizationDescription) orghier.getObject();
		descr.setOrganizationName(organizationname);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);

		return hierarchy;
	}

	static DatabaseObjectHierarchy fillDataObjectLink(DatabaseObject obj, String linknumber, String concept,
			String linkedobj) {
		DatabaseObjectHierarchy linkhier = InterpretData.DataObjectLink.createEmptyObject(obj);
		DataObjectLink userlink = (DataObjectLink) linkhier.getObject();

		userlink.setLinkConcept(concept);
		userlink.setDataStructure(linkedobj);
		String id = userlink.getIdentifier() + linknumber;
		userlink.setIdentifier(id);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);

		return hierarchy;

	}

	static DatabaseObjectHierarchy fillPersonalDescription(DatabaseObject obj, NameOfPerson person,
			String userClassification) {

		DatabaseObjectHierarchy descrhier = InterpretData.PersonalDescription.createEmptyObject(obj);
		PersonalDescription description = (PersonalDescription) descrhier.getObject();

		DatabaseObjectHierarchy personhier = descrhier.getSubObject(description.getNameOfPersonIdentifier());
		NameOfPerson subperson = (NameOfPerson) personhier.getObject();
		subperson.fill(subperson, person.getTitle(), person.getGivenName(), person.getFamilyName());

		description.setUserClassification(userClassification);
		return descrhier;
	}

	public static DatabaseObjectHierarchy fillSetOfObservations(DatabaseObject obj, String parameter, String oneline,
			String concept, String purpose) {
		/*
		String measure = "<http://purl.org/linked-data/cube#measure>";
		String dimension = "<http://purl.org/linked-data/cube#dimension>";
		Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(measure, parameter);
		Set<AttributeDescription> dimensionset = ConceptParsing.propertyInConcept(dimension, parameter);
        */
		DatabaseObjectHierarchy sethier = InterpretData.SetOfObservationValues.createEmptyObject(obj);
		SetOfObservationValues set = (SetOfObservationValues) sethier.getObject();

		set.setParameterType(parameter);
		setPurposeConceptPair(sethier, concept, purpose);
		setOneLineDescription(sethier, oneline);
		
		String measureid = set.getMeasurementValues();
		fillMeasurementValues(parameter, sethier,measureid);
		String dimensionid = set.getDimensionValues();
		fillDimensionValues(parameter, sethier,dimensionid);
		
		return sethier;
	}
	
	public static DatabaseObjectHierarchy fillMeasurementValues(String parameter, DatabaseObjectHierarchy set, String measureid) {
		String measure = "<http://purl.org/linked-data/cube#measure>";
		Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(measure, parameter);

		DatabaseObjectHierarchy measurehier = set.getSubObject(measureid);
		ChemConnectCompoundMultiple measremul = (ChemConnectCompoundMultiple) measurehier.getObject();
		for (AttributeDescription attr : measureset) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(measremul, attr.getAttributeName(),
					false);
			measremul.addID(paramhier.getObject().getIdentifier());
			measurehier.addSubobject(paramhier);
		}
		set.addSubobject(measurehier);
		return measurehier;
	}
	
	public static DatabaseObjectHierarchy fillDimensionValues(String parameter, DatabaseObjectHierarchy set, String dimensionid) {
		String dimension = "<http://purl.org/linked-data/cube#dimension>";
		Set<AttributeDescription> dimensionset = ConceptParsing.propertyInConcept(dimension, parameter);
		DatabaseObjectHierarchy dimensionhier = set.getSubObject(dimensionid);
		ChemConnectCompoundMultiple dimensionmult = (ChemConnectCompoundMultiple) dimensionhier.getObject();
		for (AttributeDescription attr : dimensionset) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(dimensionmult,
					attr.getAttributeName(), true);
			dimensionmult.addID(paramhier.getObject().getIdentifier());
			dimensionhier.addSubobject(paramhier);
		}
		set.addSubobject(dimensionhier);
		return dimensionhier;
	}

	public static DatabaseObjectHierarchy fillParameterValue(DatabaseObject valueobj, boolean dimension) {
		DatabaseObjectHierarchy attribute = InterpretData.AttributeInDataset.createEmptyObject(valueobj);
		AttributeInDataset attr = (AttributeInDataset) attribute.getObject();
		DatabaseObjectHierarchy parameterspec = InterpretData.ParameterSpecification.createEmptyObject(valueobj);
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

	
	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObject obj, String parameter) {
		DatabaseObject subobj = new DatabaseObject(obj);
		int pos = parameter.indexOf(":");
		String id = obj.getIdentifier() + "-" + parameter.substring(pos+1);
		subobj.setIdentifier(id);
		DatabaseObjectHierarchy valuehier = InterpretData.ParameterValue.createEmptyObject(subobj);
		return fillParameterValueAndSpecification(valuehier,parameter);
	}
	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObject obj, String parameter,
			boolean dimension) {
		DatabaseObject subobj = new DatabaseObject(obj);
		int pos = parameter.indexOf(":");
		String id = obj.getIdentifier() + "-" + parameter.substring(pos+1);
		subobj.setIdentifier(id);

		DatabaseObjectHierarchy valuehier = null;
		if (dimension) {
			valuehier = InterpretData.DimensionParameterValue.createEmptyObject(subobj);
		} else {
			valuehier = InterpretData.MeasurementParameterValue.createEmptyObject(subobj);
		}
		fillParameterValueAndSpecification(valuehier,parameter);
		return valuehier;
	}
	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObjectHierarchy valuehier, String parameter) {
		System.out.println("fillParameterValueAndSpecification\n" + valuehier.toString());
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
