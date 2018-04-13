package info.esblurock.reaction.core.server.initialization;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.DatabasePerson;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterDescriptionSet;
import info.esblurock.reaction.chemconnect.core.data.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.DeviceSubsystemElement;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class CreateDefaultObjectsFactory {

	public static DatabaseObjectHierarchy createMinimalPersonDescription(DatabaseObject obj, 
			String userClassification,
			String purpose,
			NameOfPerson person) {
		Map<String, DataElementInformation> elementmap = createElementMap("dataset:DatabasePerson");
		String concept = "dataset:ChemConnectContactConcept";
		String onlinedescription = person.getGivenName() + " " + person.getFamilyName();

		DatabaseObjectHierarchy contact = createContactInfo(obj,elementmap);
		DatabaseObjectHierarchy location = createContactLocationInformation(obj,elementmap);
		DatabaseObjectHierarchy personal = createPersonalDescription(obj,elementmap,person,userClassification);
		DatabaseObjectHierarchy descr = createDescriptionDataData(obj,elementmap,
				onlinedescription, concept,purpose);
		
		ChemConnectDataStructure structure = new ChemConnectDataStructure(obj,descr.getObject().getIdentifier());
		DatabasePerson info = new DatabasePerson(structure, 
				contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(),
				personal.getObject().getIdentifier());
		
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(info);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(personal);
		top.addSubobject(descr);
		return top;
	}
	
	static DatabaseObjectHierarchy createMinimalOrganization(DatabaseObject obj,
			String organizationname, String purpose) {
		Map<String, DataElementInformation> elementmap = createElementMap("dataset:Organization");

		String concept = "dataset:ChemConnectContactConcept";
		DatabaseObjectHierarchy contact = createContactInfo(obj,elementmap);
		DatabaseObjectHierarchy location = createContactLocationInformation(obj,elementmap);
		DatabaseObjectHierarchy orgdescr = createOrganizationDescription(obj,organizationname,elementmap);
		DatabaseObjectHierarchy descr = createDescriptionDataData(obj,elementmap,
				organizationname, concept,purpose);
		
		
		ChemConnectDataStructure structure = new ChemConnectDataStructure(obj,descr.getObject().getIdentifier());
		Organization org = new Organization(structure,
				contact.getObject().getIdentifier(),
				location.getObject().getIdentifier(),
				orgdescr.getObject().getIdentifier()
				);
		
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(org);
		top.addSubobject(contact);
		top.addSubobject(location);
		top.addSubobject(orgdescr);
		top.addSubobject(descr);

		return top;
	}
	
	static DatabaseObjectHierarchy createSubSystemDescription(DatabaseObject obj,
			String devicename, String purpose, String concept) {
		Map<String, DataElementInformation> elementmap = createElementMap("dataset:SubSystemDescription");

		DatabaseObjectHierarchy descr = createDescriptionDataData(obj,elementmap,
				devicename, concept,purpose);

		ChemConnectDataStructure structure = new ChemConnectDataStructure(obj,descr.getObject().getIdentifier());
		DeviceSubsystemElement device = new DeviceSubsystemElement(structure);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(device);
		hierarchy.addSubobject(descr);
		return hierarchy;
	}	
	
	static DatabaseObjectHierarchy createOrganizationDescription(DatabaseObject obj, 
			String organizationname, Map<String, DataElementInformation> elementmap) {
		
		DatabaseObject oobj = new DatabaseObject(obj);
		String oid = createSuffix(obj,"dataset:OrganizationDescription",elementmap);
		oobj.setIdentifier(oid);
		ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(oobj,obj.getIdentifier());
		OrganizationDescription descr = new OrganizationDescription(compound,
				"","",organizationname,"");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);
		
		return hierarchy;
	}
	
	
	static DatabaseObjectHierarchy createContactInfo(DatabaseObject obj, Map<String, DataElementInformation> elementmap) {
		DatabaseObject cobj = new DatabaseObject(obj);
		String cid = createSuffix(obj,"dataset:ContactInfoData",elementmap);
		cobj.setIdentifier(cid);
		ChemConnectCompoundDataStructure compound1 = new ChemConnectCompoundDataStructure(cobj,obj.getIdentifier());
		ContactInfoData contact = new ContactInfoData(compound1);

		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(contact);
		
		return top;
	}
	
	static DatabaseObjectHierarchy createContactLocationInformation(DatabaseObject obj, Map<String, DataElementInformation> elementmap) {
		DatabaseObject lobj = new DatabaseObject(obj);
		String locationid = createSuffix(obj,"dataset:ContactLocationInformation",elementmap);
		lobj.setIdentifier(locationid);
		ChemConnectCompoundDataStructure compound2 = new ChemConnectCompoundDataStructure(lobj,obj.getIdentifier());
		
		DatabaseObject gpsobj = new DatabaseObject(compound2);
		Map<String, DataElementInformation> elementmap2 = createElementMap("dataset:ContactLocationInformation");
		String gpsid = createSuffix(compound2,"dataset:GPSLocation",elementmap2);
		GPSLocation gps = new GPSLocation(gpsobj);
		
		ContactLocationInformation location = new ContactLocationInformation(compound2,gpsid);
		
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(location);
		DatabaseObjectHierarchy sub = new DatabaseObjectHierarchy(gps);
		top.addSubobject(sub);

		return top;
	}
	
	static DatabaseObjectHierarchy createPersonalDescription(DatabaseObject obj, Map<String, DataElementInformation> elementmap,
			NameOfPerson person, String userClassification) {
		DatabaseObject dobj = new DatabaseObject(obj);
		String descid = createSuffix(obj,"dataset:PersonalDescription",elementmap);
		dobj.setIdentifier(descid);
		ChemConnectCompoundDataStructure compound3 = new ChemConnectCompoundDataStructure(dobj,obj.getIdentifier());
		
		String personid = createSuffix(compound3,"dataset:PersonalDescription",elementmap);
		person.setIdentifier(personid);
		
		PersonalDescription description = new PersonalDescription(compound3, userClassification,personid);
		
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(description);
		DatabaseObjectHierarchy sub = new DatabaseObjectHierarchy(person);
		top.addSubobject(sub);
		
		return top;
	}
	
	static DatabaseObjectHierarchy createDescriptionDataData(DatabaseObject obj, Map<String, DataElementInformation> elementmap,
			String onlinedescription, String concept, String purpose) {
		
		DatabaseObject cobj = new DatabaseObject(obj);
		String cid = createSuffix(obj,"dataset:DescriptionDataData",elementmap);
		cobj.setIdentifier(cid);
		ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(cobj,obj.getIdentifier());
				
		Map<String, DataElementInformation> subelementmap = createElementMap("dataset:DescriptionDataData");
		
		DatabaseObject conceptobj = new DatabaseObject(cobj);
		String conceptid = createSuffix(cobj,"dataset:PurposeConceptPair",subelementmap);
		conceptobj.setIdentifier(conceptid);
		PurposeConceptPair pair = new PurposeConceptPair(conceptobj, concept,purpose);
		
		String fulldescription = "";
		Date sourceDate = new Date();
		String dataType = "";
		HashSet<String> keywords = new HashSet<String>();
		DescriptionDataData descr = new DescriptionDataData(compound,
				onlinedescription, fulldescription, pair.getIdentifier(), 
				sourceDate, dataType, keywords);

		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(descr);
		DatabaseObjectHierarchy sub = new DatabaseObjectHierarchy(pair);
		top.addSubobject(sub);

		return top;
	}
	
	static String createSuffix(DatabaseObject obj, String elementname, Map<String, DataElementInformation> elementmap) {
		DataElementInformation element = elementmap.get(elementname);
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
