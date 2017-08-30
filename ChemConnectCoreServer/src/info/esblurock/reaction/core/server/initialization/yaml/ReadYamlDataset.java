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
import info.esblurock.reaction.chemconnect.core.data.contact.Organization;
import info.esblurock.reaction.io.dataset.InterpretData;
import info.esblurock.reaction.ontology.dataset.DataElementInformation;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.ontology.test.dataset.ClassificationInformation;

public class ReadYamlDataset {

	@SuppressWarnings("unchecked")
	public static ArrayList<ListOfElementInformation> readYaml(InputStream in) throws IOException {
		Reader targetReader = new InputStreamReader(in);
		YamlReader reader = new YamlReader(targetReader);
		ArrayList<ListOfElementInformation> total = null;
		try {
			Object object = reader.read();
			Map<String, Object> map = (Map<String, Object>) object;
			total = ExtractListOfObjects(map);
		} catch (YamlException e) {
			throw new IOException("error reading yaml file");
		}
		return total;
	}
	public static ArrayList<ListOfElementInformation> ExtractListOfObjects(Map<String, Object> map) throws IOException {
		ArrayList<ListOfElementInformation> total = new ArrayList<ListOfElementInformation>();
			for (Object nameO : map.keySet()) {
				String name = (String) nameO;
				ListOfElementInformation elements = findListOfElementInformation(name, map);
				total.add(elements);
			}
		return total;
	}
	
	
	@SuppressWarnings("unchecked")
	public static ListOfElementInformation findListOfElementInformation(String name, Map<String, Object> map) throws IOException {
		ListOfElementInformation elements = new ListOfElementInformation(name);
		Map<String, Object> structuremap = null;
		if(map != null) {
			structuremap = (Map<String, Object>) map.get(name);
		}
		DatabaseObject top = null;
		InterpretData interpret = InterpretData.valueOf("DatabaseObject");
		if(structuremap != null) {
			top = interpret.fillFromYamlString(null, structuremap);
			supplementWithIDData(top,structuremap);
		}
		ClassificationInformation classify = DatasetOntologyParsing.getIdentificationInformation(top, name);
		
		Organization org = null;
		if(structuremap != null) {
			InterpretData interpretorg = InterpretData.valueOf(classify.getDataType());
			org = (Organization) interpretorg.fillFromYamlString(top, structuremap);
		}
		YamlDatasetInformation yamlorg = new YamlDatasetInformation(name,org,classify,null);
		elements.add(yamlorg);
		
		List<String> subelements = DatasetOntologyParsing.getSubElements(name);
		for (String subelementname : subelements) {
			ClassificationInformation classification = DatasetOntologyParsing.getIdentificationInformation(top, subelementname);
			YamlDatasetInformation dataset = findDatasetInformation(classification, structuremap);
			elements.add(dataset);
		}
		return elements;
	}

	public static void supplementWithIDData(DatabaseObject top, Map<String, Object> map) {
		Set<String> keys = map.keySet();
		Map<String, Object> newmap = new HashMap<String, Object>();
		for(String key : keys) {
			String id = DatasetOntologyParsing.id(key);
			String subid = top.getIdentifier();
			if(id != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> submap = (Map<String, Object>) map.get(key);
				subid = (String) submap.get("dc:identifier");
				if(subid == null) {
					subid = top.getIdentifier();
				}
				submap.put("dc:identifier", subid);
				newmap.put(id, subid);
			}
		}
		map.putAll(newmap);
	}

	public static String determineObjectName(String elementname) {
		String objectName = elementname;
		String obj = DatasetOntologyParsing.identifierFromID(elementname);
		if(obj != null) {
			objectName = obj;
		}
		return objectName;
	}
	public static String determineObjectID(String elementname) {
		String idName = elementname;
		String id = DatasetOntologyParsing.idFromObject(elementname);
		if(id != null) {
			idName = id;
		}
		return idName;
	}
	public static String identifierFromID(Map<String, Object> map, String obj) {
		@SuppressWarnings("unchecked")
		Map<String, Object> subelement = (Map<String, Object>) map.get(obj);
		System.out.println(obj);
		System.out.println(map);
		String identifier = (String) subelement.get("dc:identifier");
		return identifier;
	}
	
	
	public static YamlDatasetInformation findDatasetInformation(ClassificationInformation classification, Map<String, Object> structuremap) throws IOException {			
			DatabaseObject subobj = null;
			if(structuremap != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> substructuremap = (Map<String, Object>) structuremap.get(classification.getIdentifier());
				InterpretData interpret = InterpretData.valueOf(classification.getDataType());
				subobj = interpret.fillFromYamlString(classification.getTop(), substructuremap);
			}
			List<DataElementInformation> lst = DatasetOntologyParsing.getSubElementsOfStructure(classification.getIdName());
			YamlDatasetInformation dataset = new YamlDatasetInformation(classification.getIdName(), subobj, classification,lst);
		return dataset;
	}
	
}
