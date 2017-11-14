package info.esblurock.reaction.core.server.initialization.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;


import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DatasetInformationFromOntology;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ReadYamlDataset {

	@SuppressWarnings("unchecked")
	public static ArrayList<ListOfElementInformation> readYaml(InputStream in, String sourceID) throws IOException {
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
		ArrayList<ListOfElementInformation> total = null;
		try {
			Object object = reader.read();
			Map<String, Object> map = (Map<String, Object>) object;
			total = ExtractListOfObjects(map,sourceID);
		} catch (YamlException e) {
			throw new IOException("error reading yaml file\n" +  e.toString());
		}
		return total;
	}

	public static ArrayList<ListOfElementInformation> ExtractListOfObjects(Map<String, Object> map,
			String sourceID) throws IOException {
		ArrayList<ListOfElementInformation> total = new ArrayList<ListOfElementInformation>();
		for (Object nameO : map.keySet()) {
			String name = (String) nameO;
			if(name.compareTo("interpreter") != 0) {
				ListOfElementInformation elements = findListOfElementInformation(name, map,sourceID);
				total.add(elements);
			}
		}
		return total;
	}

	/**
	 * @param name
	 *            The identifier of the catalog structure object being described by
	 *            the map.
	 * @param map
	 *            The total map of objects
	 * @return The information representing the catalog structure object
	 * @throws IOException
	 * 
	 *             The values for the DatabaseObject are first isolated. These
	 *             values are the basis of the DatabaseObject of the sub elements
	 *             (unless otherwise specified).
	 * 
	 *             If an element of the catalog object structure is an ID, then the
	 *             map is augmented with the ID's this means that the actual values
	 *             do not have to be listed. However, the sub-element corresponding
	 *             to the object have to be present in the map. This is accomplished
	 *             by the supplementWithIDData routine. If the sub-elements do not
	 *             have an identifier, they are filled in by the top catalog
	 *             identifier.
	 * 
	 *             After the augmentation, the top catalog structure is filled in.
	 * 
	 *             The each of the remaining substructures are filled
	 *             (findDatasetInformation)
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static ListOfElementInformation findListOfElementInformation(String name, Map<String, Object> map,
			String sourceID)
			throws IOException {
		Map<String, Object> structuremap = null;
		if (map != null) {
			structuremap = (Map<String, Object>) map.get(name);
			name = (String) structuremap.get(StandardDatasetMetaData.chemConnectDataStructure);
		}
		ListOfElementInformation elements = new ListOfElementInformation(name);
		DatabaseObject top = null;
		if (structuremap != null) {
			top = InterpretData.DatabaseObject.fillFromYamlString(null, structuremap,sourceID);
			supplementWithIDData(top, structuremap);
		}
		DataElementInformation dataelement = new DataElementInformation(name, null,true, 0, null, null);
			
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(top, dataelement);
		
		List<DataElementInformation> substructures = DatasetOntologyParsing.subElementsOfStructure(name);
		/*
		System.out.println("===================================================: " +  substructures.size());
		for (DataElementInformation element : substructures) {
			System.out.println("Before:   " + element.toString());
		}
		System.out.println("===================================================: " +  substructures.size());
		*/
		
		substructures = replaceID(substructures,structuremap);
		/*
		System.out.println("===================================================: " +  substructures.size());
		for (DataElementInformation element : substructures) {
			System.out.println("After:   " + element.toString());
		}
		System.out.println("===================================================: " +  substructures.size());
		 */
		DatabaseObject obj = null;
		if (structuremap != null) {
			InterpretData interpretorg = InterpretData.valueOf(classify.getDataType());
			obj = interpretorg.fillFromYamlString(top, structuremap,sourceID);
		}
		DatasetInformationFromOntology yamlorg = new DatasetInformationFromOntology(name, obj, classify, null);
		elements.add(yamlorg);

		for (DataElementInformation element : substructures) {
			/*
			System.out.println("findListOfElementInformation:" 
					+ element.getChemconnectStructure().compareTo("ID") + "  Element="
					+  element);
					*/
			if (element.getChemconnectStructure().compareTo("ID") != 0) {
				ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation(top,
						element);
				DatasetInformationFromOntology dataset = findDatasetInformation(classification, structuremap,sourceID);
				findIDStructures(top, dataset, elements, structuremap,sourceID);
				elements.add(dataset);
			} else {

			}
		}
		return elements;
	}

	static ListOfElementInformation findIDStructures(DatabaseObject top,
			DatasetInformationFromOntology dataset, 
			ListOfElementInformation elements, Map<String, Object> map,
			String sourceID) throws IOException {
		for(DataElementInformation element : dataset.getElementInformation()) {
			if(element.getChemconnectStructure().compareTo("ID") == 0) {
				DataElementInformation subinfo = DatasetOntologyParsing.getSubElementStructureFromIDObject(element.getDataElementName());
				ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation(top,
						subinfo);
				DatasetInformationFromOntology subdataset = findDatasetInformation(classification, map,sourceID);
				elements.add(subdataset);
				findIDStructures(top,subdataset,elements, map,sourceID);
			}
		}
		
		
		return elements;
	}
	
	
	/** Supplement identifer information
	 * @param substructures
	 * @param structuremap
	 * @return
	 * 
	 * This is used to supplement catalog object's information 
	 * (subclass of ChemConnectDataStructure which is subclass of Catalog)
	 * by adding the identifier information.
	 * 
	 * 
	 * All the classes that are subclasses of ID are replaced 
	 * 
	 * In this example the OrganizationID is replaced by the dataset:OrganizationDescription
	 * (which is found in the structuremap).
	 * 
	 * If the the object is a subclass of 'ID' (through element.getChemconnectStructure())
	 *   Then through the element.getDataElementName(), get the actual object information
	 *        (using getSubElementStructure)
	 *   Using the object type name, the structure is searched for its identifier (identifierFromID)
	 *   The pair: object type name and actual identifier is added to the top level.
	 * 
	 * replaceID: 
			dataset:OrganizationID:  dataset:OrganizationDescriptionID  (ID):  single

		replaceID: replaced
			dataset:OrganizationDescription:  
					org:Organization (dataset:ChemConnectCompoundDataStructure):  single
					
	    The pair dataset:DescriptionDataDataID=blurock
	 * 
	 */
	public static List<DataElementInformation> replaceID(List<DataElementInformation> substructures, Map<String, Object> structuremap) {
		List<DataElementInformation> replace = new ArrayList<DataElementInformation>();
		DataElementInformation info = null;
		for (DataElementInformation element : substructures) {
			if (element.getChemconnectStructure().compareTo("ID") == 0) {
				info = DatasetOntologyParsing.getSubElementStructureFromIDObject(element.getDataElementName());
				if (info != null) {
					info.setLink(element.getLink());
					if (structuremap != null) {
						String id = identifierFromID(structuremap, info.getIdentifier());
						structuremap.put(element.getIdentifier(), id);
					}
				} else {
					info = element;
				}
			} else {
				info = element;
			}
			replace.add(info);
		}
		return replace;
	}

	/**
	 * @param map The current map
	 * @param obj The object name (to be searched in the map)
	 * @return The identifier of that object
	 * 
	 */
	public static String identifierFromID(Map<String, Object> map, String obj) {
		@SuppressWarnings("unchecked")
		Map<String, Object> subelement = (Map<String, Object>) map.get(obj);
		String identifier = (String) subelement.get("dc:identifier");
		return identifier;
	}
	
	
	/** Supplement map with DatabaseObject data
	 * 
	 * @param top The DatabaseObject of the top structure
	 * @param map
	 * @return
	 * 
	 * The map of the subelements of the top structure (derived from DatabaseObject) are supplemented
	 * with the identifier, accessibility and publisher.
	 * 
	 * For each set of structure information (going through the keys)
	 *  - Get the submap using the key
	 *  If any of the dc:identifier, dataset:accessibility or dcterms:publisher is missing,
	 *  supplement the map with the DatabaseObject information.
	 *  
	 *  This only supplements classes which have an ID object (DatasetOntologyParsing.id(key) returns non-null)
	 *  
	 *  Supplementing the object information is only needed for the object with an ID class
	 *  (later, the identifier will be used).
	 */
	public static Map<String, Object> supplementWithIDData(DatabaseObject top, Map<String, Object> map) {
		Set<String> keys = map.keySet();
		Map<String, Object> newmap = new HashMap<String, Object>();
		for (String key : keys) {
			if(key.compareTo(StandardDatasetMetaData.chemConnectDataStructure) != 0) {
			String id = DatasetOntologyParsing.id(key);
			String subid = top.getIdentifier();
			if (id != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> submap = (Map<String, Object>) map.get(key);
				subid = (String) submap.get("dc:identifier");
				if (subid == null) {
					subid = top.getIdentifier();
				}
				String access = (String) submap.get("dataset:accessibility");
				if(access == null) {
					access = top.getAccess();
				}
				String publish = (String) submap.get("dcterms:publisher");
				if(publish == null) {
					publish = top.getAccess();
				}
				submap.put("dc:identifier", subid);
				submap.put("dataset:accessibility", access);
				submap.put("dcterms:publisher", publish);
				
				newmap.put(id, subid);
			}
		}
		}
		map.putAll(newmap);
		return map;
	}

	/**
	 * @param classification
	 * @param structuremap
	 * @return
	 * @throws IOException
	 * 
	 * Using the object identifier (from Classification), the submap is isolated.
	 * InterpretData (using the DataType form classification) fills in the data object
	 * 
	 * 
	 * 
	 */
	public static DatasetInformationFromOntology findDatasetInformation(ClassificationInformation classification,
			Map<String, Object> structuremap,String sourceID) throws IOException {
		DatabaseObject subobj = null;
		if (structuremap != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> substructuremap = (Map<String, Object>) structuremap
					.get(classification.getIdentifier());
			InterpretData interpret = InterpretData.valueOf(classification.getDataType());
			
			
			subobj = interpret.fillFromYamlString(classification.getTop(), substructuremap,sourceID);
		}
		List<DataElementInformation> lst = DatasetOntologyParsing.subElementsOfStructure(classification.getIdName());
		DatasetInformationFromOntology dataset = new DatasetInformationFromOntology(classification.getIdName(), subobj, classification,
				lst);
		return dataset;
	}

	
	
	/*
	public static String determineObjectName(String elementname) {
		String objectName = elementname;
		String obj = DatasetOntologyParsing.identifierFromID(elementname);
		if (obj != null) {
			objectName = obj;
		}
		return objectName;
	}

	public static String determineObjectID(String elementname) {
		String idName = elementname;
		String id = DatasetOntologyParsing.idFromObject(elementname);
		if (id != null) {
			idName = id;
		}
		return idName;
	}
*/

}
