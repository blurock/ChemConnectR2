package info.esblurock.reaction.core.server.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;


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
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.spreadsheet.block.IsolateBlockFromMatrix;
import info.esblurock.reaction.io.db.QueryBase;
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
import info.esblurock.reaction.chemconnect.core.data.dataset.SingleObservationDataset;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationCorrespondenceSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectProtocol;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationDatasetFromProtocol;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ValueParameterComponents;
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

		createContactInfoData(infohier,info.getContactInfoData(),MetaDataKeywords.emailContactType,"first.last@email.com");
		createContactInfoData(infohier,info.getContactInfoData(),
				MetaDataKeywords.telephoneContactType,"+00-000-000-0000");
		createContactHasSite(infohier,info.getContactHasSite(),
				MetaDataKeywords.companyHomepage,"https://homepage.com");
		
		String concept = MetaDataKeywords.chemConnectContactUser;
		String purpose = MetaDataKeywords.purposeUser;
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
		catid.setPath(new ArrayList<String>(datid.getPath()));
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

	public static DatabaseObjectHierarchy fillObservationSetFromProtocol(DatabaseObject obj,
			ArrayList<String> observationIDs,
			String protocolS, String protocolID,
			String title, DataCatalogID datid) {
		DatabaseObjectHierarchy obssethier = InterpretData.ObservationDatasetFromProtocol.createEmptyObject(obj);
		ObservationDatasetFromProtocol obsset = (ObservationDatasetFromProtocol) obssethier.getObject();
		
		insertDataCatalogID(obssethier,datid);
		
		PurposeConceptPair pair = new PurposeConceptPair();
		ConceptParsing.fillInPurposeConceptPair(protocolS, pair);
		setPurposeConceptPair(obssethier, pair.getConcept(), pair.getPurpose());
		
		String description = ConceptParsing.getComment(protocolS);
		if(description == null) {
			description = "Protocol: " + ChemConnectCompoundDataStructure.removeNamespace(protocolS);
		}
		String setdescription = "The observations from protocol. " + description;
		setOneLineDescriptionAndAbstract(obssethier, title, setdescription);

		HashMap<String, DatabaseObjectHierarchy> specmap = new HashMap<String, DatabaseObjectHierarchy>();
		for(String id: observationIDs) {
			DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(id, 
					OntologyKeys.singleObservationDataset);
			SingleObservationDataset spec = (SingleObservationDataset) hierarchy.getObject();
			DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(spec.getCatalogDataID());
			DataCatalogID catid = (DataCatalogID) catidhier.getObject();
			specmap.put(catid.getDataCatalog(), hierarchy);
		}
		
		
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(protocolID, 
				OntologyKeys.protocol);
		
		//ChemConnectProtocol protocol = (ChemConnectProtocol) hierarchy.getObject();
		//DatabaseObjectHierarchy links = hierarchy.getSubObject(protocol.getChemConnectObjectLink());
		//ArrayList<String> inputlinks = findDataObjectLink(links, MetaDataKeywords.conceptLinkCorrespondenceSpecificationInput);
		//ArrayList<String> outputlinks = findDataObjectLink(links, MetaDataKeywords.conceptLinkCorrespondenceSpecificationOutput);
		
		DatabaseObjectHierarchy linkhier = obssethier.getSubObject(obsset.getChemConnectObjectLink());
		ChemConnectCompoundMultiple linkmultiple = (ChemConnectCompoundMultiple) linkhier.getObject();
		linkStructure(linkmultiple, linkhier, 0, MetaDataKeywords.conceptLinkProtocol, hierarchy.getObject().getIdentifier());
		linkmultiple.setNumberOfElements(1);
		int count = linkmultiple.getNumberOfElements();
		count = addSpecificationLinks(protocolS,false,MetaDataKeywords.conceptLinkSingleObservationInput,specmap,count,obssethier,linkhier);
		count = addSpecificationLinks(protocolS,true,MetaDataKeywords.conceptLinkSingleObservationOutput,specmap,count,obssethier,linkhier);
		linkmultiple.setNumberOfElements(count);
		
		return obssethier;
	}
	public static DatabaseObjectHierarchy fillProtocolDefinition(DatabaseObject obj,
			ArrayList<String> specificationIDs,
			String protocolS, String title, DataCatalogID datid) {
		DatabaseObjectHierarchy methodhier = InterpretData.ChemConnectProtocol.createEmptyObject(obj);
		ChemConnectProtocol methodology = (ChemConnectProtocol) methodhier.getObject();
		
		insertDataCatalogID(methodhier,datid);
		
		String paramid = methodology.getParameterValues();
		DatabaseObjectHierarchy paramsethier = methodhier.getSubObject(paramid);
		ChemConnectCompoundMultiple parammulti = (ChemConnectCompoundMultiple) paramsethier.getObject();
		
		PurposeConceptPair pair = new PurposeConceptPair();
		ConceptParsing.fillInPurposeConceptPair(protocolS, pair);
		setPurposeConceptPair(methodhier, pair.getConcept(), pair.getPurpose());
		
		String description = ConceptParsing.getComment(protocolS);
		if(description == null) {
			description = "Protocol: " + ChemConnectCompoundDataStructure.removeNamespace(protocolS);
		}
		setOneLineDescriptionAndAbstract(methodhier, title, description);
		
		Set<AttributeDescription> attrs = ConceptParsing.attributesInConcept(protocolS);
		for (AttributeDescription attr : attrs) {
			DatabaseObjectHierarchy paramhier = fillParameterValueAndSpecification(parammulti, attr.getAttributeName());
			paramsethier.addSubobject(paramhier);
		}
		parammulti.setNumberOfElements(parammulti.getNumberOfElements() + attrs.size());
		
		HashMap<String, DatabaseObjectHierarchy> specmap = new HashMap<String, DatabaseObjectHierarchy>();
		for(String id: specificationIDs) {
			DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(id, 
					MetaDataKeywords.observationCorrespondenceSpecification );
			ObservationCorrespondenceSpecification spec = (ObservationCorrespondenceSpecification) hierarchy.getObject();
			DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(spec.getCatalogDataID());
			DataCatalogID catid = (DataCatalogID) catidhier.getObject();
			specmap.put(catid.getDataCatalog(), hierarchy);
		}
		DatabaseObjectHierarchy linkhier = methodhier.getSubObject(methodology.getChemConnectObjectLink());
		ChemConnectCompoundMultiple linkmultiple = (ChemConnectCompoundMultiple) linkhier.getObject();
		int count = linkmultiple.getNumberOfElements();
		count = addSpecificationLinks(protocolS,false,MetaDataKeywords.conceptLinkCorrespondenceSpecificationInput,specmap,0,methodhier,linkhier);
		count = addSpecificationLinks(protocolS,true,MetaDataKeywords.conceptLinkCorrespondenceSpecificationOutput,specmap,count,methodhier,linkhier);
		linkmultiple.setNumberOfElements(count);
		return methodhier;
	}
	
	public static int addSpecificationLinks(String protocolS, 
			boolean measure,String linkconcept,
			HashMap<String, DatabaseObjectHierarchy> specmap,
			int count,
			DatabaseObjectHierarchy methodhier,
			DatabaseObjectHierarchy linkhier) {
		Set<String> observations = ConceptParsing.setOfObservationsForProtocol(protocolS,measure);
		for(String spec : observations) {
			DatabaseObjectHierarchy inputspechier = specmap.get(spec);
			if(inputspechier != null) {
				count = linkStructure(linkhier.getObject(),linkhier,count,
						linkconcept,
						inputspechier.getObject().getIdentifier());
				methodhier.addSubobject(inputspechier);
			}
		}
		return count;
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
		catid.setPath(new ArrayList<String>(datid.getPath()));
		
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
	
	public static DatabaseObjectHierarchy fillObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid) {
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		obj.setSourceID(sourceID);
		obj.nullKey();
		
		DatabaseObjectHierarchy hierarchy = InterpretData.ObservationBlockFromSpreadSheet.createEmptyObject(obj);
		ObservationBlockFromSpreadSheet obsblock = (ObservationBlockFromSpreadSheet) hierarchy.getObject();
		
		replaceDataCatalogID(hierarchy,obsblock.getCatalogDataID(),datid);
		
		return hierarchy;
	}
	
	private static void replaceDataCatalogID(DatabaseObjectHierarchy hierarchy, String identifier, DataCatalogID catid) {
		DatabaseObjectHierarchy cathierarchy = hierarchy.getSubObject(identifier);
		DataCatalogID cat = (DataCatalogID) cathierarchy.getObject();
		cat.setDataCatalog(catid.getDataCatalog());
		cat.setSimpleCatalogName(catid.getSimpleCatalogName());
		cat.setPath(catid.getPath());
		cat.setCatalogBaseName(catid.getCatalogBaseName());
	}
	
	
	public static DatabaseObjectHierarchy fillObservationsFromSpreadSheetFull(DatabaseObject obj, DataCatalogID catid, int numberOfColumns, int numberOfRows) {
		DatabaseObjectHierarchy hierarchy = InterpretData.ObservationsFromSpreadSheetFull.createEmptyObject(obj);
		ObservationsFromSpreadSheetFull observations = (ObservationsFromSpreadSheetFull) hierarchy.getObject();
		
		DatabaseObjectHierarchy inputhierarchy = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) inputhierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		replaceDataCatalogID(hierarchy,observations.getCatalogDataID(),catid);
		StringBuilder build = new StringBuilder();
		
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
		input.setDelimitorType(SpreadSheetInputInformation.CSV);
		input.setSource(build.toString());
		input.setSourceType(SpreadSheetInputInformation.STRINGSOURCE);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillSingleObservationDataset(DatabaseObject obj, 
			String observationS,
			String correspondenceSpecification,
			String observationMatrix,
			DataCatalogID catid) throws IOException {
		DatabaseObjectHierarchy hierarchy = InterpretData.SingleObservationDataset.createEmptyObject(obj);
		SingleObservationDataset single = (SingleObservationDataset) hierarchy.getObject();
		replaceDataCatalogID(hierarchy, single.getCatalogDataID(),catid);
		DatabaseObjectHierarchy multihierarchy = hierarchy.getSubObject(single.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multihierarchy.getObject();
		int numlinks = multilnk.getNumberOfElements();
		numlinks = linkStructure(obj,multihierarchy,numlinks,
				MetaDataKeywords.conceptLinkCorrespondenceSpecification,
				correspondenceSpecification);
		numlinks = linkStructure(obj,multihierarchy,numlinks,
				MetaDataKeywords.conceptLinkObservationsFromSpreadSheetFull,
				observationMatrix);
		multilnk.setNumberOfElements(numlinks+1);
		
		DatabaseObjectHierarchy corrshierarchy = ExtractCatalogInformation.getCatalogObject(correspondenceSpecification, 
				MetaDataKeywords.observationCorrespondenceSpecification );
		ObservationCorrespondenceSpecification corrspec = (ObservationCorrespondenceSpecification) corrshierarchy.getObject();

		DatabaseObjectHierarchy observationhierarchy = ExtractCatalogInformation.getCatalogObject(observationMatrix,
				MetaDataKeywords.observationsFromSpreadSheetFull);
		
		ObservationCorrespondenceSpecification obsspec = (ObservationCorrespondenceSpecification) corrshierarchy.getObject();
		String links = obsspec.getChemConnectObjectLink();
		DatabaseObjectHierarchy speclinks = corrshierarchy.getSubObject(links);
		ArrayList<String> isolateset = findDataObjectLink(speclinks,MetaDataKeywords.conceptLinkBlockIsolation);
		String isolateID = isolateset.get(0);
		DatabaseObjectHierarchy isolatehierarchy = ExtractCatalogInformation.getCatalogObject(isolateID,
				MetaDataKeywords.spreadSheetBlockIsolation);
		SpreadSheetBlockIsolation blockisolate = (SpreadSheetBlockIsolation) isolatehierarchy.getObject();		
		DatabaseObjectHierarchy obs = IsolateBlockFromMatrix.isolateFromMatrix(catid, observationhierarchy, blockisolate);
		ObservationsFromSpreadSheetFull matrix = (ObservationsFromSpreadSheetFull) obs.getObject();
		DatabaseObjectHierarchy isolatedvalues = obs.getSubObject(matrix.getObservationMatrixValues());
		ObservationMatrixValues obsmatrix = (ObservationMatrixValues) isolatedvalues.getObject();
		DatabaseObjectHierarchy matrixhier = isolatedvalues.getSubObject(obsmatrix.getObservationRowValue());
		
		DatabaseObjectHierarchy singlevalueshier = hierarchy.getSubObject(single.getObservationValueRows());
		ChemConnectCompoundMultiple singlevaluesmultiple = (ChemConnectCompoundMultiple) singlevalueshier.getObject();
		
		
		ArrayList<DatabaseObjectHierarchy> valueset = matrixhier.getSubobjects();
		singlevaluesmultiple.setNumberOfElements(valueset.size());
		int count = 0;
		for(DatabaseObjectHierarchy valuehier : valueset) {
			ObservationValueRow value = (ObservationValueRow) valuehier.getObject();
			DatabaseObjectHierarchy copyhier = InterpretData.ObservationValueRow.createEmptyObject(singlevaluesmultiple);
			ObservationValueRow copyvalue = (ObservationValueRow) copyhier.getObject();
			String id = copyvalue.getIdentifier();
			copyvalue.setIdentifier(id + "-" + count);
			count++;
			ArrayList<String> vset = new ArrayList<String>(value.getRow());
			copyvalue.setRow(vset);
			singlevalueshier.addSubobject(copyhier);
		}
		
		DatabaseObjectHierarchy specsethier = corrshierarchy.getSubObject(corrspec.getMatrixSpecificationCorrespondenceSet());
		MatrixSpecificationCorrespondenceSet specset = (MatrixSpecificationCorrespondenceSet) specsethier.getObject();
		
		DatabaseObjectHierarchy multiplespec = specsethier.getSubObject(specset.getMatrixSpecificationCorrespondence());
		ArrayList<DatabaseObjectHierarchy> spechierset = multiplespec.getSubobjects();
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> uncertain = new ArrayList<String>();
		
		spechierset.sort(new Comparator<DatabaseObjectHierarchy>() {

			@Override
			public int compare(DatabaseObjectHierarchy o1, DatabaseObjectHierarchy o2) {
				MatrixSpecificationCorrespondence spec1 = (MatrixSpecificationCorrespondence) o1.getObject();
				MatrixSpecificationCorrespondence spec2 = (MatrixSpecificationCorrespondence) o2.getObject();
				return spec1.getColumnNumber() - spec2.getColumnNumber();
			}
		});
		for(DatabaseObjectHierarchy spechier : spechierset) {
			MatrixSpecificationCorrespondence spec = (MatrixSpecificationCorrespondence) spechier.getObject();
			titles.add(spec.getSpecificationLabel());
			uncertain.add(String.valueOf(spec.isIncludesUncertaintyParameter()));
		}
		
		DatabaseObjectHierarchy obsspechier = corrshierarchy.getSubObject(corrspec.getObservationSpecification());
		ObservationSpecification spec = (ObservationSpecification) obsspechier.getObject();
		DatabaseObjectHierarchy dimensionspec = obsspechier.getSubObject(spec.getDimensionSpecifications());
		DatabaseObjectHierarchy measurespec = obsspechier.getSubObject(spec.getMeasureSpecifications());
		
		DatabaseObjectHierarchy compmulthier = hierarchy.getSubObject(single.getParameterValueComponents());
		ChemConnectCompoundMultiple compmult = (ChemConnectCompoundMultiple) compmulthier.getObject();
		
		setInParameterValueComponents(dimensionspec,titles,uncertain,compmult,compmulthier);
		setInParameterValueComponents(measurespec,titles,uncertain,compmult,compmulthier);
		compmult.setNumberOfElements(titles.size());
		
		return hierarchy;
	}
	
	public static void setInParameterValueComponents(
			DatabaseObjectHierarchy spec,
			ArrayList<String> titles, ArrayList<String> uncertain,
			ChemConnectCompoundMultiple compmult, DatabaseObjectHierarchy compmulthier) {
		int count = 0;
		Iterator<String> iter = uncertain.iterator();
		for(String col : titles) {
			
			DatabaseObjectHierarchy pspechier = findParameterSpecification(spec,col);
			String uncertainS = iter.next();
			if(pspechier != null) {
				ParameterSpecification pspec = (ParameterSpecification) pspechier.getObject();
				DatabaseObjectHierarchy comphier = InterpretData.ValueParameterComponents.createEmptyObject(compmult);
				ValueParameterComponents components = (ValueParameterComponents) comphier.getObject();
				String id = components.getIdentifier();
				String compid = id + "-" + ChemConnectCompoundDataStructure.removeNamespace(col)+"-"+count;
				DatabaseObjectHierarchy unithier = pspechier.getSubObject(pspec.getUnits());
				ValueUnits units = (ValueUnits) unithier.getObject();
				components.setIdentifier(compid);
				components.setParameterLabel(col);
				components.setUnitsOfValue(units.getUnitsOfValue());
				components.setPostion(count);
				boolean uncertainB = Boolean.valueOf(uncertainS);
				components.setUncertaintyValue(uncertainB);
				compmulthier.addSubobject(comphier);
			}
			count++;
		}

	}
	
	public static DatabaseObjectHierarchy findParameterSpecification(DatabaseObjectHierarchy specs, String id) {
		DatabaseObjectHierarchy parameterspechier = null;
		Iterator<DatabaseObjectHierarchy> iter = specs.getSubobjects().iterator();
		while(iter.hasNext() && parameterspechier == null) {
			DatabaseObjectHierarchy spechier = iter.next();
			ParameterSpecification spec = (ParameterSpecification) spechier.getObject();
			if(id.compareTo(spec.getParameterLabel()) == 0) {
				parameterspechier = spechier;
			}
		}
		return parameterspechier;
		
	}
	
	public static ArrayList<String> findDataObjectLink(DatabaseObjectHierarchy links, String id) {
		Iterator<DatabaseObjectHierarchy> iter = links.getSubobjects().iterator();
		ArrayList<String> set = new ArrayList<String>();
		while(iter.hasNext()) {
			DatabaseObjectHierarchy linkhier = iter.next();
			DataObjectLink link = (DataObjectLink) linkhier.getObject();
			if(id.compareTo(link.getLinkConcept()) == 0) {
				String datalink = link.getDataStructure();
				set.add(datalink);
			}
		}
		return set;
	}
	
	
	public static int linkStructure(DatabaseObject obj,DatabaseObjectHierarchy multihierarchy, 
			int numlinks, String linkconcept, String id) {
		numlinks++;
		String numlinkS = Integer.toString(numlinks);
		DatabaseObjectHierarchy link = fillDataObjectLink(obj, numlinkS, 
				linkconcept,id);
		multihierarchy.addSubobject(link);
		return numlinks;
	}
	
	public static DatabaseObjectHierarchy fillObservationsFromSpreadSheet(DatabaseObject obj, 
			DataCatalogID catid, SpreadSheetInputInformation spreadinfo,
			int numberOfColumns, int numberOfRows) {
		DatabaseObjectHierarchy hierarchy = InterpretData.ObservationsFromSpreadSheet.createEmptyObject(obj);
		ObservationsFromSpreadSheet observations = (ObservationsFromSpreadSheet) hierarchy.getObject();
		
		DatabaseObjectHierarchy titlehierarchy = hierarchy.getSubObject(observations.getObservationValueRowTitle());
		ObservationValueRowTitle title = (ObservationValueRowTitle) titlehierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		replaceDataCatalogID(hierarchy,observations.getCatalogDataID(),catid);
		StringBuilder build = new StringBuilder();
		
		DatabaseObjectHierarchy infohier = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation info = (SpreadSheetInputInformation) infohier.getObject();
		info.setSource(spreadinfo.getSource());
		info.setDelimitor(spreadinfo.getDelimitor());
		info.setType(spreadinfo.getType());
		info.setSourceType(spreadinfo.getSourceType());
		
		ArrayList<String> defaulttitles = new ArrayList<String>();
		for(int colcount= 0; colcount < numberOfColumns - 1; colcount++) {
			String coltitle = "col:" + colcount;
			defaulttitles.add(coltitle);
		}
		title.setParameterLabel(defaulttitles);
		
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
	public static DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple) {
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
			DatabaseObjectHierarchy obsspechier = InterpretData.ObservationSpecification.createEmptyObject(obspecmulti);
			ObservationSpecification specification = (ObservationSpecification) obsspechier.getObject();
			specification.setIdentifier(specid);
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

	public static void setOneLineDescriptionAndAbstract(DatabaseObjectHierarchy hierarchy, String oneline, String description) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		descr.setOnlinedescription(oneline);
		descr.setDescriptionAbstract(description);
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
		
		DatabaseObject obj = new DatabaseObject(multi);
		obj.setSourceID(childcatalog.getSourceID());
		
		DatabaseObjectHierarchy subcatalog = addConnectionToMultiple(obj, multi, childcatalog.getIdentifier());
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(subcatalog);
		DatabaseWriteBase.writeDatabaseObject(multi);
	}
	
	
	
	public static void connectInCatalogHierarchy(DatabaseObjectHierarchy parent, DatabaseObjectHierarchy child) {
		DatasetCatalogHierarchy parentcatalog = (DatasetCatalogHierarchy) parent.getObject();
		DatasetCatalogHierarchy childcatalog = (DatasetCatalogHierarchy) child.getObject();

		DatabaseObjectHierarchy multilnkhier = parent.getSubObject(parentcatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy subcatalog = addConnectionToMultiple(multilnk, multilnk, childcatalog.getIdentifier());
		multilnkhier.addSubobject(subcatalog);
		multilnk.setNumberOfElements(multilnk.getNumberOfElements() + 1);
	}
	
	public static DatabaseObjectHierarchy addConnectionToMultiple(
			DatabaseObject obj,
			ChemConnectCompoundMultiple multilnk,
			String childid) {
		int numlinks = multilnk.getNumberOfElements();
		String numlinkS = Integer.toString(numlinks);
		multilnk.setNumberOfElements(numlinks+1);

		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(obj, numlinkS, MetaDataKeywords.linkSubCatalog,
				childid);

		return subcatalog;
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, String username, String userid) {
		DatabaseObject dobj = new DatabaseObject(obj);
		String uid = dobj.getIdentifier() + "-" + username;
		dobj.setIdentifier(uid);
		dobj.nullKey();
		String onelinedescription = "Catalog of '" + username + "'";
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
		
		String oname = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.organization));
		DatabaseObject orgobj = new DatabaseObject(oname, access, owner, sourceID);
		ChemConnectCompoundDataStructure orgstructure = new ChemConnectCompoundDataStructure(orgobj,"");
		ArrayList<String> orgPath = new ArrayList<String>(userPath);
		DataCatalogID orgnamecatid = new DataCatalogID(orgstructure,oname,"dataset:OrganizationDataCatagory",orgname,orgPath);
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillOrganization(orgobj, orgname, title,orgnamecatid);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(org);

		String catname = DatasetCatalogHierarchy.createFullCatalogName(username,
				ChemConnectCompoundDataStructure.removeNamespace(OntologyKeys.datasetCatalogHierarchy));
		DatabaseObject catobj = new DatabaseObject(catname, access, owner, sourceID);
		DatabaseObjectHierarchy usercat = fillCataogHierarchyForUser(catobj, username,
				user.getObject().getIdentifier());
		DatabaseObject orgcatobj = new DatabaseObject(usercat.getObject());
		DatabaseObjectHierarchy orgcat = fillCataogHierarchyForOrganization(orgcatobj,
				orgname, org.getObject().getIdentifier());
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
		DatabaseObjectHierarchy sethier = InterpretData.ObservationCorrespondenceSpecification.createEmptyObject(obj);
		ObservationCorrespondenceSpecification set = (ObservationCorrespondenceSpecification) sethier.getObject();
		
		insertDataCatalogID(sethier, datid);

		DatabaseObjectHierarchy pairhier = InterpretData.PurposeConceptPair.createEmptyObject(obj);
		PurposeConceptPair pair =(PurposeConceptPair) pairhier.getObject();
		ConceptParsing.fillInPurposeConceptPair(parameter, pair);
		setPurposeConceptPair(sethier, pair.getConcept(), pair.getPurpose());
		setOneLineDescription(sethier, oneline);
		
		DatabaseObjectHierarchy obsspechier = sethier.getSubObject(set.getObservationSpecification());
		fillObservationSpecification(obsspechier, parameter, set.getIdentifier());
		
		
		
		return sethier;
	}
	/** fillMatrixSpecificationCorrespondence
	 * 
	 * Fill in matrix correspondence from matrix and specification
	 *   
	 *   MatrixSpecificationCorrespondence hierarchy
	 * 
	 * @param colcorrhier Hierarchy for MatrixSpecificationCorrespondenceSet
	 */
	public static DatabaseObjectHierarchy fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy corrspechier,
			ArrayList<String> coltitles) {
		MatrixSpecificationCorrespondenceSet corrspec = (MatrixSpecificationCorrespondenceSet) corrspechier.getObject();
		DatabaseObjectHierarchy colcorrhier = corrspechier.getSubObject(corrspec.getMatrixSpecificationCorrespondence());

		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) colcorrhier.getObject();
		DatabaseObject corrobj = new DatabaseObject(multiple);
		int count = 0;
		for(String coltitle : coltitles) {
			DatabaseObjectHierarchy corrhier = InterpretData.MatrixSpecificationCorrespondence.createEmptyObject(corrobj);
			MatrixSpecificationCorrespondence corr = (MatrixSpecificationCorrespondence) corrhier.getObject();
			String corrid = multiple.getIdentifier() + "-" + count + "-" + coltitle;
			corr.setIdentifier(corrid);
			corr.setColumnNumber(count);
			corr.setMatrixColumn(coltitle);
			corr.setSpecificationLabel(MetaDataKeywords.undefined);
			colcorrhier.addSubobject(corrhier);
			count++;
		}
		multiple.setNumberOfElements(count);
		return corrspechier;
	}
	
	public static void fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy colcorrhier,
			DatabaseObjectHierarchy measure,
			DatabaseObjectHierarchy dimension) {
		int count = addMatrixSpecificationCorrespondence(0,colcorrhier,measure);
		addMatrixSpecificationCorrespondence(count,colcorrhier,dimension);
		//addMatrixSpecificationCorrespondence(count,colcorrhier,dimension);
	}
	
	public static int addMatrixSpecificationCorrespondence(int count,
			DatabaseObjectHierarchy colcorrhier,
			DatabaseObjectHierarchy spec) {
		
		//ChemConnectCompoundMultiple colcorrset = (ChemConnectCompoundMultiple) colcorrhier.getObject();
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) spec.getObject();
		for(DatabaseObjectHierarchy hier: spec.getSubobjects()) {
			
			ParameterSpecification pspec = (ParameterSpecification) hier.getObject();
			String name = pspec.getParameterLabel();
			
			//DatabaseObject corrobj = new DatabaseObject(multiple);
			int pos = name.indexOf(":");
			String corrid = multiple.getIdentifier() + "-" + name.substring(pos+1);
			//corrobj.setIdentifier(corrid);
			
			DatabaseObjectHierarchy corrhier = InterpretData.MatrixSpecificationCorrespondence.createEmptyObject(colcorrhier.getObject());
			MatrixSpecificationCorrespondence corr = (MatrixSpecificationCorrespondence) corrhier.getObject();
			corr.setIdentifier(corrid);
			corr.setColumnNumber(count);
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
		DatabaseObjectHierarchy valueshier = matrixvalues.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemultiple = (ChemConnectCompoundMultiple) valueshier.getObject();
		DatabaseObjectHierarchy rowvalueshier = InterpretData.ObservationValueRow.createEmptyObject(valuemultiple);
		ObservationValueRow rowvalues = (ObservationValueRow) rowvalueshier.getObject();
		valueshier.addSubobject(rowvalueshier);
		valuemultiple.setNumberOfElements(valuemultiple.getNumberOfElements() + 1);
		addTitlesAndSampleValues(rowvalues,dimension);
		addTitlesAndSampleValues(rowvalues,measure);
		
	}
	
	public static void addTitlesAndSampleValues(ObservationValueRow rowvalues, DatabaseObjectHierarchy multhier) {
		int num = multhier.getSubobjects().size();
		for(int i=0; i<num ; i++) {
			rowvalues.add("0.0");
		}
	}
	
	/**
	 * @param parameter     The name of the parameter
	 * @param set           The DatabaseObjectHierarchy of the set of parameters (or parameter specifications)
	 * @param measureid     The ID of the parent
	 * @param dimension     true if a dimension
	 * @param specification true if specification
	 * @return
	 */
	public static DatabaseObjectHierarchy fillMeasurementValues(String parameter, DatabaseObjectHierarchy set, 
			String measureid, boolean dimension, boolean specification) {
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
				valuehier = InterpretData.DimensionParameterSpecification.createEmptyObject(obj);
			} else {
				valuehier = InterpretData.DimensionParameterValue.createEmptyObject(obj);
			}
		} else {
			if(specification) {
				valuehier = InterpretData.MeasureParameterSpecification.createEmptyObject(obj);
			} else {
				valuehier = InterpretData.MeasurementParameterValue.createEmptyObject(obj);
			}
		}
		DatabaseObject valueobj = valuehier.getObject();
		valueobj.setIdentifier(id);
		
		if(specification) {
			fillParameterSpecification(valuehier,parameter);
		} else {
			fillParameterValueAndSpecification(valuehier,parameter);			
		}
		return valuehier;
	}

	public static DatabaseObjectHierarchy fillParameterSpecification(DatabaseObjectHierarchy spechier, String parameter) {
		ParameterSpecification parameterspec = (ParameterSpecification) spechier.getObject();
		parameterspec.setParameterLabel(parameter);
		
		DatabaseObjectHierarchy unitshier = InterpretData.ValueUnits.createEmptyObject(parameterspec);
		ValueUnits units = (ValueUnits) unitshier.getObject();
		parameterspec.setUnits(units.getIdentifier());
		DatabaseObjectHierarchy concepthier = InterpretData.PurposeConceptPair.createEmptyObject(parameterspec);
		PurposeConceptPair concept = (PurposeConceptPair) concepthier.getObject();
		parameterspec.setPurposeandconcept(concept.getIdentifier());
		ConceptParsing.fillAnnotatedExample(parameter, units, concept, parameterspec, null);
		ConceptParsing.fillInProperties(parameter, units, concept);
		
		DatabaseObjectHierarchy newspechier = new DatabaseObjectHierarchy(parameterspec);
		newspechier.addSubobject(unitshier);
		newspechier.addSubobject(concepthier);
		
		spechier.replaceInfo(newspechier);

		return spechier;
	}

	
	public static DatabaseObjectHierarchy fillParameterValueAndSpecification(DatabaseObjectHierarchy valuehier, String parameter) {
		ParameterValue value = (ParameterValue) valuehier.getObject();
		String specID = value.getParameterSpec();
		DatabaseObjectHierarchy spechier = valuehier.getSubObject(specID);
		fillParameterSpecification(spechier,parameter);
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
