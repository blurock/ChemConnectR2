package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.xalan.xsltc.dom.MultiValuedNodeHeapIterator;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.concepts.AttributeDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactHasSite;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
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
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInterpretation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.ontology.OntologyKeys;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class CreateDefaultObjectsFactory {
	
	public static String noUnitClassS = "no unit class";
	public static String noValueUnitsS = "no value units";
	public static String noPurposeS = "no purpose";
	public static String noConceptS = "no concept";
	
	public static String userdescription = "info/esblurock/reaction/core/server/resources/UserDescriptionText.txt";
	public static String orgdescription = "info/esblurock/reaction/core/server/resources/OrgDescriptionText.txt";
	public static String personDescription = "info/esblurock/reaction/core/server/resources/PersonDescriptionText.txt";

	static DatabaseObjectHierarchy fillDescriptionDataData(DatabaseObject obj, String onelinedescription,
			String concept, String purpose) {
		DatabaseObjectHierarchy descrhier = InterpretData.DescriptionDataData.createEmptyObject(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(onelinedescription);

		setOneLineDescription(descrhier, onelinedescription);
		setPurposeConceptPair(descrhier, concept, purpose);

		return descrhier;
	}

	public static DatabaseObjectHierarchy fillMinimalPersonDescription(DatabaseObject obj, String username, String userClassification,
			NameOfPerson person, DataCatalogID datid) {
		String uid = obj.getIdentifier() + "-" + username;
		obj.setIdentifier(uid);
		DatabaseObjectHierarchy infohier = InterpretData.IndividualInformation.createEmptyObject(obj);
		IndividualInformation info = (IndividualInformation) infohier.getObject();

		DatabaseObjectHierarchy personalhier = infohier.getSubObject(info.getPersonalDescriptionID());
		PersonalDescription personal = (PersonalDescription) personalhier.getObject();
		personal.setUserClassification(userClassification);
		
		insertDataCatalogID(infohier,datid);

		String nameID = personal.getNameOfPersonIdentifier();
		DatabaseObjectHierarchy namehier = personalhier.getSubObject(nameID);
		NameOfPerson name = (NameOfPerson) namehier.getObject();
		name.fill(name, person.getTitle(), person.getGivenName(), person.getFamilyName());

		String onlinedescription = person.getGivenName() + " " + person.getFamilyName();
		setOneLineDescription(infohier, onlinedescription);
		setDescriptionInHierarchy(infohier,info.getDescriptionDataData(),personDescription,username);

		createContactInfoData(infohier,info.getContactInfoData(),"dataset:EmailContactType","first.last@email.com");
		createContactInfoData(infohier,info.getContactInfoData(),"dataset:TelephoneContactType","+00-000-000-0000");
		createContactHasSite(infohier,info.getContactHasSite(),"dataset:CompanyHomepage","https://homepage.com");
		
		String concept = "dataset:ChemConnectContactUser";
		String purpose = "dataset:PurposeUser";
		setPurposeConceptPair(infohier, concept, purpose);

		return infohier;
	}

	public static void createContactInfoData(DatabaseObjectHierarchy infohier, String id,
			String contactType, String contactkey) {
		DatabaseObjectHierarchy contactmulthier = infohier.getSubObject(id);
		
		ChemConnectCompoundMultiple contactmult = (ChemConnectCompoundMultiple) contactmulthier.getObject();
		int numlinks = contactmulthier.getSubobjects().size();
		String numlinkS = Integer.toString(numlinks);
		
		DatabaseObjectHierarchy contacthier = InterpretData.ContactInfoData.createEmptyObject(contactmult);
		ContactInfoData contact = (ContactInfoData) contacthier.getObject();
		String newid = contact.getIdentifier() + numlinkS;
		contact.setIdentifier(newid);
		
		contactmulthier.addSubobject(contacthier);
		
		contact.setContactType(contactType);
		contact.setContact(contactkey);
	}
	
	public static void createContactHasSite(DatabaseObjectHierarchy infohier, String id,
			String siteType, String sitekey) {		
		DatabaseObjectHierarchy contactmulthier = infohier.getSubObject(id);		
		ChemConnectCompoundMultiple contactmult = (ChemConnectCompoundMultiple) contactmulthier.getObject();
		int numlinks = contactmulthier.getSubobjects().size();
		String numlinkS = Integer.toString(numlinks);
		
		DatabaseObjectHierarchy contacthier = InterpretData.ContactHasSite.createEmptyObject(contactmult);
		ContactHasSite site = (ContactHasSite) contacthier.getObject();
		String newid = site.getIdentifier() + numlinkS;
		site.setIdentifier(newid);

		contactmulthier.addSubobject(contacthier);
		
		site.setHttpAddressType(siteType);
		site.setHttpAddress(sitekey);
	}
		
	public static void insertDataCatalogID(DatabaseObjectHierarchy hierarchy, DataCatalogID datid) {
		ChemConnectDataStructure info = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(info.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setCatalogBaseName(datid.getCatalogBaseName());
		catid.setDataCatalog(datid.getDataCatalog());
		catid.setSimpleCatalogName(datid.getSimpleCatalogName());
	}
	
	public static DatabaseObjectHierarchy fillOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID datid) {
		String concept = MetaDataKeywords.conceptContact;
		String purpose = MetaDataKeywords.purposeOrganization;
		String uid = obj.getIdentifier() + "-" + shortname;
		obj.setIdentifier(uid);

		DatabaseObjectHierarchy orghier = InterpretData.Organization.createEmptyObject(obj);
		Organization org = (Organization) orghier.getObject();

		DatabaseObjectHierarchy orgdescrhier = orghier.getSubObject(org.getOrganizationDescriptionID());
		OrganizationDescription orgdescr = (OrganizationDescription) orgdescrhier.getObject();
		orgdescr.setOrganizationName(organizationname);
		
		insertDataCatalogID(orghier,datid);

		setOneLineDescription(orghier, organizationname);
		setPurposeConceptPair(orghier, concept, purpose);

		return orghier;
	}

	public static DatabaseObjectHierarchy fillMethodologyDefinition(DatabaseObject obj, 
			String methodologyS, String title, DataCatalogID datid) {
		DatabaseObjectHierarchy methodhier = InterpretData.ChemConnectMethodology.createEmptyObject(obj);
		ChemConnectMethodology methodology = (ChemConnectMethodology) methodhier.getObject();
		methodology.setMethodologyType(methodologyS);
		
		insertDataCatalogID(methodhier,datid);
				
		String obssetid = methodology.getObservationSpecs();
		DatabaseObjectHierarchy obssethier = methodhier.getSubObject(obssetid);
		fillInputOutputObservationSpecifications(methodologyS,obssethier);
		
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
			paramsethier.addSubobject(paramhier);
		}
		parammulti.setNumberOfElements(parammulti.getNumberOfElements() + attrs.size());
		return methodhier;
	}
	
	public static DatabaseObjectHierarchy fillSubSystemDescription(DatabaseObject obj, String devicename,
			DataCatalogID datid) {
		DatabaseObjectHierarchy hierarchy = InterpretData.SubSystemDescription.createEmptyObject(obj);
		SubSystemDescription device = (SubSystemDescription) hierarchy.getObject();
		
		device.setSubSystemType(devicename);

		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(device.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(devicename);

		insertDataCatalogID(hierarchy,datid);
		
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(device.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setCatalogBaseName(datid.getCatalogBaseName());
		catid.setDataCatalog(datid.getDataCatalog());
		catid.setSimpleCatalogName(datid.getSimpleCatalogName());
		
		setOneLineDescription(hierarchy, devicename);
		
		DatabaseObjectHierarchy pcHier = InterpretData.PurposeConceptPair.createEmptyObject(descr);
		PurposeConceptPair pair = (PurposeConceptPair) pcHier.getObject();
		ConceptParsing.fillInPurposeConceptPair(devicename, pair);
		
		setPurposeConceptPair(hierarchy, pair.getConcept(), pair.getPurpose());
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept(devicename);
		String paramid = device.getParameterValues();
		DatabaseObjectHierarchy paramsethier = hierarchy.getSubObject(paramid);
		ChemConnectCompoundMultiple parammulti = (ChemConnectCompoundMultiple) paramsethier.getObject();
	
		for (AttributeDescription attr : attrs) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(parammulti, attr.getAttributeName());
			paramsethier.addSubobject(paramhier);
		}

		Set<String> subsystems = ConceptParsing.immediateSubSystems(devicename);
		Set<String> components = ConceptParsing.immediateComponents(devicename);
		String subsystemid = device.getSubSystems();
		DatabaseObjectHierarchy subsystemhier = hierarchy.getSubObject(subsystemid);
		ChemConnectCompoundMultiple subsystemmulti = (ChemConnectCompoundMultiple) subsystemhier.getObject();
		for(String subsystem : subsystems) {
			String simple = ChemConnectCompoundDataStructure.removeNamespace(subsystem);
			DatabaseObject subobj = new DatabaseObject(subsystemmulti);
			String id = subobj.getIdentifier() + "-" + simple;
			subobj.setIdentifier(id);
			DatabaseObjectHierarchy subhierarchy = fillSubSystemDescription(subobj,subsystem,datid);
			subsystemhier.addSubobject(subhierarchy);
		}
		for(String component : components) {
			String simple = ChemConnectCompoundDataStructure.removeNamespace(component);
			DatabaseObject subobj = new DatabaseObject(subsystemmulti);
			String id = subobj.getIdentifier() + "-" + simple;
			subobj.setIdentifier(id);
			DatabaseObjectHierarchy subhierarchy = fillSubSystemDescription(subobj,component,datid);
			subsystemhier.addSubobject(subhierarchy);
		}


		String obspecsetID = device.getObservationSpecs();
		DatabaseObjectHierarchy obspecset = hierarchy.getSubObject(obspecsetID);
		

		DatabaseObject obsobj = new DatabaseObject(obj);
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.observationSpecs);
		String obsid = createSuffix(obj, element);
		obsobj.setIdentifier(obsid);
		fillInputOutputObservationSpecifications(devicename,obspecset);
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy fillObservationsFromSpreadSheet(DatabaseObject obj, DataCatalogID catid, int numberOfColumns, int numberOfRows) {
		DatabaseObjectHierarchy hierarchy = InterpretData.ObservationsFromSpreadSheet.createEmptyObject(obj);
		ObservationsFromSpreadSheet observations = (ObservationsFromSpreadSheet) hierarchy.getObject();
		
		DatabaseObjectHierarchy inputhierarchy = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) inputhierarchy.getObject();
		DatabaseObjectHierarchy interprethierarchy = hierarchy.getSubObject(observations.getSpreadSheetInterpretation());
		SpreadSheetInterpretation interpret = (SpreadSheetInterpretation) interprethierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy cathierarchy = hierarchy.getSubObject(observations.getCatalogDataID());
		DataCatalogID cat = (DataCatalogID) cathierarchy.getObject();
		
		DatabaseObjectHierarchy titlehier = observehierarchy.getSubObject(values.getObservationRowValueTitles());
		ObservationValueRowTitle rowtitles = (ObservationValueRowTitle) titlehier.getObject();
		
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		
		cat.setDataCatalog(catid.getDataCatalog());
		cat.setSimpleCatalogName(catid.getSimpleCatalogName());
		cat.setPath(catid.getPath());
		cat.setCatalogBaseName(catid.getCatalogBaseName());
		
		StringBuilder build = new StringBuilder();
		ArrayList<String> titles = new ArrayList<String>();
		for(int colcount=0; colcount<numberOfColumns;colcount++) {
			String title = "Column" + colcount;
			titles.add(title);
		}
		for(int rowcount= 0; rowcount < numberOfRows; rowcount++) {
			DatabaseObjectHierarchy obshier = InterpretData.ObservationValueRow.createEmptyObject(valuemult);
			ObservationValueRow obs = (ObservationValueRow) obshier.getObject();
			String id = obs.getIdentifier() + rowcount;
			obs.setIdentifier(id);
			valuemulthier.addSubobject(obshier);
		
			
			for(int colcount= 0; colcount < numberOfColumns - 1; colcount++) {
				obs.addValue("0");
				build.append("0, ");
			}
			obs.addValue("0");
			obs.setRowNumber(rowcount);
			build.append("0\n");
		}
		valuemult.setNumberOfElements(numberOfRows);
		input.setDelimitor(",");
		input.setDelimitorType("dataset:CSV");
		input.setSource(build.toString());
		input.setSourceType("dataset:StringSource");
		
		interpret.setStartRow(0);
		interpret.setEndRow(numberOfRows-1);
		interpret.setStartColumn(0);
		interpret.setEndColumn(numberOfColumns-1);
		interpret.setNoBlanks(false);
		interpret.setTitleSearchKey("");

		rowtitles.setParameterLabel(titles);

		return hierarchy;
	}
	
	
	public static void fillInputOutputObservationSpecifications(String setofobservationsS,
			DatabaseObjectHierarchy obspecset) {
		ChemConnectCompoundMultiple obspecmulti = (ChemConnectCompoundMultiple) obspecset.getObject();
		boolean measure = true;
		Set<String> measureobs = ConceptParsing.setOfObservationsForSubsystem(setofobservationsS,measure);
		for(String observation : measureobs) {
			fillObservationSpecification(observation, measure, obspecmulti,obspecset);
		}
		measure = false;
		Set<String> dimensionobs = ConceptParsing.setOfObservationsForSubsystem(setofobservationsS,measure);
		for(String observation : dimensionobs) {
			fillObservationSpecification(observation, measure, obspecmulti,obspecset);
		}
	}
	public static DatabaseObjectHierarchy createEmptyMultipleObject(DatabaseObjectHierarchy multhierarchy) {
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) multhierarchy.getObject();
		String dataType = multiple.getType();
		String numS = String.valueOf(multiple.getNumberOfElements());
		DatabaseObject obj = new DatabaseObject(multiple);
		obj.nullKey();
		ClassificationInformation info = DatasetOntologyParsing.getIdentificationInformation(dataType);
		String structureName = info.getDataType();
		InterpretData interpret = InterpretData.valueOf(structureName);
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		String uid = hierarchy.getObject().getIdentifier() + numS;
		hierarchy.getObject().setIdentifier(uid);
		multhierarchy.addSubobject(hierarchy);
		return hierarchy;
	}
	
	public static void fillObservationSpecification(String observation, 
			boolean measure,
			ChemConnectCompoundMultiple obspecmulti,
			DatabaseObjectHierarchy obspecset) {
			String obsspecID = obspecmulti.getIdentifier();
			DatabaseObject subobsobj = new DatabaseObject(obspecmulti);
			String specid = subobsobj.getIdentifier() + "-" + ChemConnectCompoundDataStructure.removeNamespace(observation);
			subobsobj.setIdentifier(specid);
			DatabaseObjectHierarchy obsspechier = InterpretData.ObservationSpecification.createEmptyObject(subobsobj);
			fillObservationSpecification(obsspechier,observation,obsspecID,measure);
			obspecset.addSubobject(obsspechier);
	}
	public static void fillObservationSpecification(DatabaseObjectHierarchy obsspechier, String observation, String parentID, boolean measure) {
		fillObservationSpecification(obsspechier,observation,parentID);
		ObservationSpecification specification = (ObservationSpecification) obsspechier.getObject();
		String observationParameterType = ConceptParsing.getStructureType(observation);
		String qualified = ConceptParsing.qualifyStructureType(observationParameterType, measure);
		specification.setObservationParameterType(qualified);
		
	}
	public static void fillObservationSpecification(DatabaseObjectHierarchy obsspechier, String observation, String parentID) {
		ObservationSpecification specification = (ObservationSpecification) obsspechier.getObject();
		specification.setParentLink(parentID);
		specification.setSpecificationLabel(observation);
		String measurespecid = specification.getMeasureSpecifications();
		fillMeasurementValues(observation, obsspechier, measurespecid,false,true);
		String dimensionspecid = specification.getDimensionSpecifications();
		fillMeasurementValues(observation, obsspechier, dimensionspecid,true,true);
		String observationParameterType = ConceptParsing.getStructureType(observation);
		specification.setObservationParameterType(observationParameterType);
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
			DatabaseObject obj, String id, String onelinedescription, String catagorytype) throws IOException {

		DatabaseObject aobj = new DatabaseObject(obj);
		String aid = DatasetCatalogHierarchy.createFullCatalogName(obj.getIdentifier(), id);
		aobj.setIdentifier(aid);
		aobj.nullKey();

		DatabaseObjectHierarchy cathierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(aobj);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) cathierarchy.getObject();
		setOneLineDescription(cathierarchy, onelinedescription);
		
		DatabaseObjectHierarchy idhier = cathierarchy.getSubObject(catalog.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) idhier.getObject();
		catid.setCatalogBaseName(obj.getIdentifier());
		catid.setDataCatalog(catagorytype);
		catid.setSimpleCatalogName(id);

		setPurposeConceptPair(cathierarchy, catagorytype, StandardDatasetMetaData.purposeDefineSubCatagory);
		
		connectInCatalogHierarchy(topcatalog, catalog);
				
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(cathierarchy);
		return cathierarchy;
	}


	public static String userCatalogHierarchyID(String username) {
		String uid = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.datasetCatalogHierarchy));
		String uidcat = uid + "-" + username;
		DatabaseObject catobj = new DatabaseObject(uidcat,username,username,"");
		DataElementInformation element = DatasetOntologyParsing
				.getSubElementStructureFromIDObject(OntologyKeys.datasetCatalogHierarchy);
		String catid = createSuffix(catobj, element);
		return catid;
	}
	
	public static DatabaseObjectHierarchy fillDataCatalogID(DatabaseObject obj, String parentLink, String catalogbase, 
			String catalog, String simple, ArrayList<String> path) {
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,parentLink);
		
		DataCatalogID id = new DataCatalogID(structure,catalogbase,catalog,simple,path);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(id);
		return hierarchy;
	}
	
	public static void connectInCatalogHierarchy(DatasetCatalogHierarchy parentcatalog, DatasetCatalogHierarchy childcatalog) throws IOException {
		String linkid = parentcatalog.getChemConnectObjectLink();
		
		InterpretData multiinterpret = InterpretData.valueOf("ChemConnectCompoundMultiple");
		ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multiinterpret.readElementFromDatabase(linkid);
		DatabaseObjectHierarchy subcatalog = addConnectionToMultiple(multi, childcatalog.getIdentifier());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(subcatalog);
		DatabaseWriteBase.writeDatabaseObject(multi);
	}
	
	
	public static void connectInCatalogHierarchy(DatabaseObjectHierarchy parent, DatabaseObjectHierarchy child) {
		DatasetCatalogHierarchy parentcatalog = (DatasetCatalogHierarchy) parent.getObject();
		DatasetCatalogHierarchy childcatalog = (DatasetCatalogHierarchy) child.getObject();

		DatabaseObjectHierarchy multilnkhier = parent.getSubObject(parentcatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy subcatalog = addConnectionToMultiple(multilnk, childcatalog.getIdentifier());
		multilnkhier.addSubobject(subcatalog);
		multilnk.setNumberOfElements(multilnk.getNumberOfElements() + 1);
	}
	
	public static DatabaseObjectHierarchy addConnectionToMultiple(ChemConnectCompoundMultiple multilnk,
			String childid) {
		int numlinks = multilnk.getNumberOfElements();
		String numlinkS = Integer.toString(numlinks);

		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(multilnk, numlinkS, MetaDataKeywords.linkSubCatalog,
				childid);

		return subcatalog;
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, String username, String userid) {
		DatabaseObject dobj = new DatabaseObject(obj);
		String uid = dobj.getIdentifier() + "-" + username;
		dobj.setIdentifier(uid);
		dobj.nullKey();
		String onelinedescription = "Catalog of '" + username + "'";
		System.out.println("fillCataogHierarchyForUser: " + dobj.getIdentifier());
		DatabaseObjectHierarchy userhierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(dobj);
		DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy) userhierarchy.getObject();
		setOneLineDescription(userhierarchy, onelinedescription);
		
		DatabaseObjectHierarchy idhier = userhierarchy.getSubObject(usercatalog.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) idhier.getObject();
		catid.setCatalogBaseName(obj.getIdentifier());
		catid.setDataCatalog(StandardDatasetMetaData.conceptUserDataCatagory);
		catid.setSimpleCatalogName(username);
		ArrayList<String> userPath = new ArrayList<String>();
		catid.setPath(userPath);
		
		setDescriptionInHierarchy(userhierarchy, usercatalog.getDescriptionDataData(),userdescription, username);

		setPurposeConceptPair(userhierarchy, 
				StandardDatasetMetaData.purposeDefineSubCatagory, 
				StandardDatasetMetaData.conceptUserDataCatagory);

		
		DatabaseObjectHierarchy multilnkhier = userhierarchy.getSubObject(usercatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy userlink = fillDataObjectLink(multilnk, "0", MetaDataKeywords.linkUser, userid);
		multilnkhier.addSubobject(userlink);
		multilnk.setNumberOfElements(1);
		return userhierarchy;
	}

	public static void setDescriptionInHierarchy(DatabaseObjectHierarchy hierarchy, String descrid, String resource, String name) {
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(descrid);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		
		try {
			String descrabstract = IOUtils.toString(CreateDefaultObjectsFactory.class.getClassLoader().getResourceAsStream(resource), "UTF-8");
			if(name != null) {
				descrabstract = descrabstract.replace("#####", name);
			}
			descr.setDescriptionAbstract(descrabstract);
		} catch (IOException e) {
		}

	}
	
	public static DatabaseObjectHierarchy fillCataogHierarchyForOrganization(DatabaseObject obj,
			String organizationid, String orglinkid) {		
		
		DatabaseObject aobj = new DatabaseObject(obj);
		String orgid = obj.getIdentifier() + "-" + organizationid;
		aobj.setIdentifier(orgid);
		aobj.nullKey();
		String orgcatdescription = "Catalog of " + organizationid;
		System.out.println("");
		DatabaseObjectHierarchy orghierarchy = InterpretData.DatasetCatalogHierarchy.createEmptyObject(aobj);
		DatasetCatalogHierarchy orgcatalog = (DatasetCatalogHierarchy) orghierarchy.getObject();
		
		DatabaseObjectHierarchy idhier = orghierarchy.getSubObject(orgcatalog.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) idhier.getObject();
		catid.setCatalogBaseName(obj.getIdentifier());
		catid.setDataCatalog(StandardDatasetMetaData.conceptOrgDataCatagory);
		catid.setSimpleCatalogName(organizationid);
		ArrayList<String> userPath = new ArrayList<String>();
		catid.setPath(userPath);

		setOneLineDescription(orghierarchy, orgcatdescription);
		setDescriptionInHierarchy(orghierarchy, orgcatalog.getDescriptionDataData(),orgdescription, organizationid);

		setPurposeConceptPair(orghierarchy, 
				StandardDatasetMetaData.purposeDefineSubCatagory, 
				StandardDatasetMetaData.conceptOrgDataCatagory);

		
		DatabaseObjectHierarchy multiorghier = orghierarchy.getSubObject(orgcatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multiorg = (ChemConnectCompoundMultiple) multiorghier.getObject();
		DatabaseObjectHierarchy orglink = fillDataObjectLink(multiorg, "0", MetaDataKeywords.linkOrganization,
				orglinkid);
		multiorghier.addSubobject(orglink);
		multiorg.setNumberOfElements(1);
		return orghierarchy;
	}
	
	static public void createAndWriteDefaultUserOrgAndCatagories(	String username, String userrole, String access, String owner,
			String orgname, String title, String sourceID) {

		
		String indname = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.individualInformation));
		DatabaseObject usrcatobj = new DatabaseObject(indname,access,username,sourceID);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(usrcatobj,"");
		ArrayList<String> userPath = new ArrayList<String>();
		DataCatalogID namecatid = new DataCatalogID(structure,indname,"dataset:UserDataCatagory",username,userPath);
		NameOfPerson person = new NameOfPerson(usrcatobj, "", "", username);
		DatabaseObjectHierarchy user = CreateDefaultObjectsFactory.fillMinimalPersonDescription(usrcatobj, username, userrole, person,namecatid);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(user);
		//System.out.println(user.toString());
		
		String oname = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.organization));
		DatabaseObject orgobj = new DatabaseObject(oname, access, owner, sourceID);
		ChemConnectCompoundDataStructure orgstructure = new ChemConnectCompoundDataStructure(orgobj,"");
		ArrayList<String> orgPath = new ArrayList<String>(userPath);
		DataCatalogID orgnamecatid = new DataCatalogID(orgstructure,oname,"dataset:OrganizationDataCatagory",orgname,orgPath);
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillOrganization(orgobj, orgname, title,orgnamecatid);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(org);
		//System.out.println(org.toString());

		String catname = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.datasetCatalogHierarchy));
		//System.out.println("createAndWriteDefaultUserOrgAndCatagories\n" + namecatid.toString());
		DatabaseObject catobj = new DatabaseObject(catname, access, owner, sourceID);
		//System.out.println("before illCataogHierarchyForUser\n" + catobj);
		DatabaseObjectHierarchy usercat = fillCataogHierarchyForUser(catobj, username,
				user.getObject().getIdentifier());
		//System.out.println("createAndWriteDefaultUserOrgAndCatagories: " + usercat.toString());

		DatabaseObject orgcatobj = new DatabaseObject(usercat.getObject());
		DatabaseObjectHierarchy orgcat = fillCataogHierarchyForOrganization(orgcatobj,
				orgname, org.getObject().getIdentifier());
		//System.out.println("createAndWriteDefaultUserOrgAndCatagories: " + orgcat.toString());

		connectInCatalogHierarchy(usercat, orgcat);
		
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

	public static DatabaseObjectHierarchy fillSetOfObservations(DatabaseObject obj, String parameter, 
			String oneline,
			DataCatalogID datid) {
		DatabaseObjectHierarchy sethier = InterpretData.SetOfObservationValues.createEmptyObject(obj);
		SetOfObservationValues set = (SetOfObservationValues) sethier.getObject();
		
		insertDataCatalogID(sethier, datid);

		DatabaseObjectHierarchy pairhier = InterpretData.PurposeConceptPair.createEmptyObject(obj);
		PurposeConceptPair pair =(PurposeConceptPair) pairhier.getObject();
		ConceptParsing.fillInPurposeConceptPair(parameter, pair);

		setPurposeConceptPair(sethier, pair.getConcept(), pair.getPurpose());
		setOneLineDescription(sethier, oneline);
		
		DatabaseObjectHierarchy obsspechier = sethier.getSubObject(set.getObservationSpecification());
		ObservationSpecification spec = (ObservationSpecification) obsspechier.getObject();
		fillObservationSpecification(obsspechier, parameter, set.getIdentifier());
		DatabaseObjectHierarchy matrixvalueshier = sethier.getSubObject(set.getObservationMatrixValues());
		DatabaseObjectHierarchy measure = obsspechier.getSubObject(spec.getMeasureSpecifications());
		DatabaseObjectHierarchy dimension = obsspechier.getSubObject(spec.getDimensionSpecifications());
		fillObservationMatrixValues(matrixvalueshier,measure,dimension);
		
		DatabaseObjectHierarchy corrspechier = sethier.getSubObject(set.getMatrixSpecificationCorrespondenceSet());
		MatrixSpecificationCorrespondenceSet corrspec = (MatrixSpecificationCorrespondenceSet) corrspechier.getObject();
		DatabaseObjectHierarchy colcorrhier = corrspechier.getSubObject(corrspec.getMatrixSpecificationCorrespondence());
		fillMatrixSpecificationCorrespondence(colcorrhier,measure,dimension);
		
		
		
		return sethier;
	}
	
	public static void fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy colcorrhier,
			DatabaseObjectHierarchy measure,
			DatabaseObjectHierarchy dimension) {
		int count = addMatrixSpecificationCorrespondence(0,colcorrhier,dimension);
		addMatrixSpecificationCorrespondence(count,colcorrhier,measure);
	}
	
	public static int addMatrixSpecificationCorrespondence(int count,
			DatabaseObjectHierarchy colcorrhier,
			DatabaseObjectHierarchy spec) {
		ChemConnectCompoundMultiple colcorrset = (ChemConnectCompoundMultiple) colcorrhier.getObject();
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) spec.getObject();
		for(DatabaseObjectHierarchy hier: colcorrhier.getSubobjects()) {
			ParameterSpecification pspec = (ParameterSpecification) hier.getObject();
			String name = pspec.getParameterLabel();
			
			DatabaseObject corrobj = new DatabaseObject(multiple);
			int pos = name.indexOf(":");
			String corrid = multiple.getIdentifier() + "-" + name.substring(pos+1);
			corrobj.setIdentifier(corrid);

			
			DatabaseObjectHierarchy corrhier = InterpretData.MatrixSpecificationCorrespondence.createEmptyObject(corrobj);
			MatrixSpecificationCorrespondence corr = (MatrixSpecificationCorrespondence) corrhier.getObject();
			corr.setMatrixColumn(String.valueOf(count));
			corr.setSpecificationLabel(name);
			colcorrhier.addSubobject(corrhier);
			count++;
		}
		return count;
	}
	
	public static void fillObservationMatrixValues(DatabaseObjectHierarchy matrixvalues,
			DatabaseObjectHierarchy measure,
			DatabaseObjectHierarchy dimension) {
		ObservationMatrixValues values = (ObservationMatrixValues) matrixvalues.getObject();
		DatabaseObjectHierarchy titleshier = matrixvalues.getSubObject(values.getObservationRowValueTitles());
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titleshier.getObject();
		DatabaseObjectHierarchy valueshier = matrixvalues.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemultiple = (ChemConnectCompoundMultiple) valueshier.getObject();
		DatabaseObjectHierarchy rowvalueshier = InterpretData.ObservationValueRow.createEmptyObject(valuemultiple);
		ObservationValueRow rowvalues = (ObservationValueRow) rowvalueshier.getObject();
		valueshier.addSubobject(rowvalueshier);
		valuemultiple.setNumberOfElements(valuemultiple.getNumberOfElements() + 1);
		addTitlesAndSampleValues(titles,rowvalues,dimension);
		addTitlesAndSampleValues(titles,rowvalues,measure);
		
	}
	
	public static void addTitlesAndSampleValues(ObservationValueRowTitle titles, ObservationValueRow rowvalues, DatabaseObjectHierarchy multhier) {
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) multhier.getObject();
		for(DatabaseObjectHierarchy hier: multhier.getSubobjects()) {
			ParameterSpecification spec = (ParameterSpecification) hier.getObject();
			String name = spec.getParameterLabel();
			titles.addParameterTitle(name);
			rowvalues.add("0.0");
		}
	}
	
	public static DatabaseObjectHierarchy fillMeasurementValues(String parameter, DatabaseObjectHierarchy set, String measureid, boolean dimension, boolean specification) {
		String type = "<http://purl.org/linked-data/cube#measure>";
		if(dimension) {
			type = "<http://purl.org/linked-data/cube#dimension>";
		}
		Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(type, parameter);
		DatabaseObjectHierarchy measurehier = set.getSubObject(measureid);
		ChemConnectCompoundMultiple measremul = (ChemConnectCompoundMultiple) measurehier.getObject();
		for (AttributeDescription attr : measureset) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(measremul, attr.getAttributeName(),
					dimension,specification);
			measurehier.addSubobject(paramhier);
		}
		measremul.setNumberOfElements(measremul.getNumberOfElements() + measureset.size());
		set.addSubobject(measurehier);
		return measurehier;
	}
	public static DatabaseObjectHierarchy fillParameterValue(DatabaseObject valueobj, boolean dimension) {
		DatabaseObjectHierarchy attribute = InterpretData.ChemConnectCompoundDataStructure.createEmptyObject(valueobj);
		ChemConnectCompoundDataStructure attr = (ChemConnectCompoundDataStructure) attribute.getObject();
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
			boolean dimension, boolean specification) {
		DatabaseObject subobj = new DatabaseObject(obj);
		int pos = parameter.indexOf(":");
		String id = obj.getIdentifier() + "-" + parameter.substring(pos+1);
		subobj.setIdentifier(id);

		DatabaseObjectHierarchy valuehier = null;
		if (dimension) {
			if(specification) {
				valuehier = InterpretData.DimensionParameterSpecification.createEmptyObject(subobj);
			} else {
				valuehier = InterpretData.DimensionParameterValue.createEmptyObject(subobj);
			}
		} else {
			if(specification) {
				valuehier = InterpretData.MeasureParameterSpecification.createEmptyObject(subobj);
			} else {
				valuehier = InterpretData.MeasurementParameterValue.createEmptyObject(subobj);
			}
		}
		if(specification) {
			fillParameterSpecification(valuehier,parameter);
		} else {
			fillParameterValueAndSpecification(valuehier,parameter);			
		}
		return valuehier;
	}

	public static DatabaseObjectHierarchy fillParameterSpecification(DatabaseObjectHierarchy spechier, String parameter) {
		ParameterSpecification parameterspec = (ParameterSpecification) spechier.getObject();

		String unitsID = parameterspec.getUnits();
		
		DatabaseObjectHierarchy unitshier = spechier.getSubObject(unitsID);
		ValueUnits units = (ValueUnits) unitshier.getObject();

		String conceptID = parameterspec.getPurposeandconcept();

		DatabaseObjectHierarchy concepthier = spechier.getSubObject(conceptID);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();

		ConceptParsing.fillAnnotatedExample(parameter, units, concept, parameterspec, null);
		ConceptParsing.fillInProperties(parameter, units, concept);
		
		parameterspec.setParameterLabel(parameter);

		return spechier;
	}

	
	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObjectHierarchy valuehier, String parameter) {
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
		
		parameterspec.setParameterLabel(parameter);

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

	
	/*
	public static DatabaseObjectHierarchy fillMeasurementSpecification(String parameter, DatabaseObjectHierarchy set, String measureid, boolean dimension) {
		String type = "<http://purl.org/linked-data/cube#measure>";
		if(dimension) {
			type = "<http://purl.org/linked-data/cube#dimension>";
		}
		Set<AttributeDescription> measureset = ConceptParsing.propertyInConcept(type, parameter);

		DatabaseObjectHierarchy measurehier = set.getSubObject(measureid);
		ChemConnectCompoundMultiple measremul = (ChemConnectCompoundMultiple) measurehier.getObject();
		for (AttributeDescription attr : measureset) {
			DatabaseObjectHierarchy paramhier = fillParameterSpecification(measremul, attr.getAttributeName(),
					dimension);
			measremul.addID(paramhier.getObject().getIdentifier());
			measurehier.addSubobject(paramhier);
		}
		set.addSubobject(measurehier);
		return measurehier;
	}
	*/
	/*
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
*/
/*
	public static DatabaseObjectHierarchy fillParameterSpecification(DatabaseObject obj, String parameter,
			boolean dimension) {
		DatabaseObject subobj = new DatabaseObject(obj);
		int pos = parameter.indexOf(":");
		String id = obj.getIdentifier() + "-" + parameter.substring(pos+1);
		subobj.setIdentifier(id);

		DatabaseObjectHierarchy valuehier = null;
		if (dimension) {
			valuehier = InterpretData.DimensionParameterSpecification.createEmptyObject(subobj);
		} else {
			valuehier = InterpretData.MeasureParameterSpecification.createEmptyObject(subobj);
		}
		fillParameterValueAndSpecification(valuehier,parameter);
		return valuehier;
	}
*/
	

	
}
