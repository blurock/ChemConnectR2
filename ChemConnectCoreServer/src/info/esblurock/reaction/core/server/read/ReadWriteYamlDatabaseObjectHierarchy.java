package info.esblurock.reaction.core.server.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.image.GCSServiceRoutines;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ReadWriteYamlDatabaseObjectHierarchy {
	
	public static void writeAsYamlToGCS(String id, String topclass, String username,String sessionid) throws IOException {
		DatabaseObjectHierarchy tophierarchy = null;
		String toptype = DatasetOntologyParsing.getStructureFromDataStructure(topclass);
		if(toptype.compareTo(MetaDataKeywords.datasetCatalogHierarchy) == 0) {
			tophierarchy = collectDatasetCatalogHierarchy(id);
		} else {
			tophierarchy = ExtractCatalogInformation.getCatalogObject(id, toptype);
		}
		
		String yaml = yamlStringFromDatabaseObjectHierarchy(tophierarchy);

		ChemConnectDataStructure structure = (ChemConnectDataStructure) tophierarchy.getObject();
		String idS = structure.getCatalogDataID();
		DatabaseObjectHierarchy catalogHier = tophierarchy.getSubObject(idS);
		DataCatalogID catalogID = (DataCatalogID) catalogHier.getObject();
		String extension = ConceptParsing.getFileExtension(StandardDatasetMetaData.yamlFileType);
		String filename = catalogID.blobFilenameFromCatalogID(extension);
		String contentType = ConceptParsing.getContentType(StandardDatasetMetaData.textFileType);
		contentType = "text/plain";
		System.out.println("writeYamlObjectHierarchy: extension:   " + extension);
		System.out.println("writeYamlObjectHierarchy: filename:    " + filename);
		System.out.println("writeYamlObjectHierarchy: contentType: " + contentType);
		
		String path = catalogID.getFullPath("/");
		
		String title = structure.getClass().getSimpleName() + ": " + structure.getIdentifier();
		GCSServiceRoutines.uploadFileBlob(tophierarchy.getObject().getIdentifier(),
			GoogleCloudStorageConstants.storageBucket, 
			sessionid,username,
			path, filename,contentType,title,yaml);
	}
	
	public static DatabaseObjectHierarchy  collectDatasetCatalogHierarchy(String id) {
		String dataType = MetaDataKeywords.datasetCatalogHierarchy;
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(id,dataType);
		collectDatasetCatalogHierarchy(hierarchy);
		return hierarchy;
		
	}
		public static void collectDatasetCatalogHierarchy(DatabaseObjectHierarchy hierarchy) {
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) hierarchy.getObject();
		DatabaseObjectHierarchy linkhier = hierarchy.getSubObject(catalog.getChemConnectObjectLink());
		ArrayList<DatabaseObjectHierarchy> subs = linkhier.getSubobjects();
		for(DatabaseObjectHierarchy sub : subs) {
			DataObjectLink link = (DataObjectLink) sub.getObject();
			if(link.getLinkConcept().compareTo(MetaDataKeywords.linkSubCatalog) == 0) {
				DatabaseObjectHierarchy subhier = collectDatasetCatalogHierarchy(link.getDataStructure());
				hierarchy.addSubobject(subhier);
			}
		}		
	}

	
	public static String yamlStringFromDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException {
		WriteReadDatabaseObjects.updateSourceID(hierarchy);
		Map<String,Object> map1 = yamlDatabaseObjectHierarchy(hierarchy);
		System.out.println(map1);
		MapUtils.debugPrint(System.out, "Map: ", map1);
		StringWriter wS = new StringWriter(1000000);
		YamlWriter writer = new YamlWriter(wS);
		writer.write(map1);
		writer.close();
		String yaml = wS.toString();
		
		return yaml;
	}

	public static Map<String, Object> yamlDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy)
			throws IOException {
		Map<String, Object> mapping = new HashMap<String, Object>();
		DatabaseObject object = objecthierarchy.getObject();
		String name = object.getClass().getSimpleName();
		mapping.put("structure", name);
		InterpretData interpret = InterpretData.valueOf(name);
		Map<String, Object> objmapping = interpret.createYamlFromObject(object);
		if(name.compareTo("ChemConnectCompoundMultiple") == 0) {
			Map<String, Object> multmap = new HashMap<String, Object>();
			mapping.put("dataset:ChemConnectCompoundMultiple",multmap);
			ArrayList<DatabaseObjectHierarchy> lst = objecthierarchy.getSubobjects();
			for(DatabaseObjectHierarchy hierarchy : lst) {
				DatabaseObject obj = hierarchy.getObject();
				Map<String, Object> submap =  yamlDatabaseObjectHierarchy(hierarchy);
				multmap.put(obj.getIdentifier(), submap);
			}
		} else {
			ArrayList<DatabaseObjectHierarchy> lst = objecthierarchy.getSubobjects();
			for(DatabaseObjectHierarchy hierarchy : lst) {
				DatabaseObject obj = hierarchy.getObject();
				Map<String, Object> submap =  yamlDatabaseObjectHierarchy(hierarchy);
				mapping.put(obj.getIdentifier(), submap);
			}			
		}
		Set<String> subnames = objmapping.keySet();
		for (String subname : subnames) {
			Object obj = objmapping.get(subname);
			if(obj.getClass().getCanonicalName().compareTo(ArrayList.class.getCanonicalName()) == 0) {
				mapping.put(subname, obj);
			} else if(obj.getClass().getCanonicalName().compareTo(String.class.getCanonicalName()) == 0) {
				String n = (String) obj;
				mapping.put(subname, n);
			}
		}
		/*
		Set<String> subnames = objmapping.keySet();
		for (String subname : subnames) {
			Object obj = objmapping.get(subname);
			
			if(subname.compareTo("dataset:ChemConnectCompoundMultiple") == 0) {
				ArrayList<String> subobjectIDs = (ArrayList<String>) obj;
				Map<String,Object> submap = new HashMap<String,Object>();
				for(String id : subobjectIDs) {
					DatabaseObjectHierarchy objhier = objecthierarchy.getSubObject(id);
					Map<String, Object> multmap = yamlDatabaseObjectHierarchy(objhier);
					submap.put(id, multmap);
				}
				mapping.put("dataset:ChemConnectCompoundMultiple", submap);
			} else if(obj.getClass().getCanonicalName().compareTo(ArrayList.class.getCanonicalName()) == 0) {
				mapping.put(subname, obj);
			} else {
				String value = (String) obj;
				DatabaseObjectHierarchy objhier = objecthierarchy.getSubObject(value);
				if (objhier != null) {
					Map<String, Object> subobjmapping = yamlDatabaseObjectHierarchy(objhier);
					DatabaseObject dataobj = objhier.getObject();
					String id = dataobj.getIdentifier();
					System.out.println("yaml: " + value + ": " + id + "\n" + subobjmapping);
					mapping.put(subname, id);
					mapping.put(id, subobjmapping);
				} else {
					String n = (String) objmapping.get(subname);
					mapping.put(subname, n);
				}					
			}
		}
		*/
		return mapping;
	}
	
	@SuppressWarnings("unchecked")
	public static DatabaseObjectHierarchy readYamlDatabaseObjectHierarchy(DatabaseObject top,
			Map<String, Object> mapping, String sourceID) throws IOException {
		String structurename = (String) mapping.get("structure");
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy();
		InterpretData interpret = InterpretData.valueOf(structurename);
		if (structurename.compareTo("ChemConnectCompoundMultiple") == 0) {
			ChemConnectCompoundMultiple mult = (ChemConnectCompoundMultiple) interpret.fillFromYamlString(top, mapping, sourceID);
			Map<String, Object> submap = (Map<String, Object>) mapping.get("dataset:ChemConnectCompoundMultiple");
			mult.setNumberOfElements(0);
			if (submap != null) {
				Set<String> keys = submap.keySet();
				mult.setNumberOfElements(keys.size());
				for (String key : keys) {
					Map<String, Object> multimap = (Map<String, Object>) submap.get(key);
					DatabaseObjectHierarchy subhier = readYamlDatabaseObjectHierarchy(top, multimap, sourceID);
					hierarchy.addSubobject(subhier);
				}
			}
			hierarchy.setObject(mult);
		} else {
			Set<String> keys = mapping.keySet();
			for (String name : keys) {
				Object obj = mapping.get(name);
				if (!String.class.isInstance(obj)) {
					if(obj.getClass().getCanonicalName().compareTo(ArrayList.class.getCanonicalName()) == 0) {
						mapping.put(name, obj);
					} else {
					Map<String, Object> submap = (Map<String, Object>) obj;
					DatabaseObjectHierarchy subhier = readYamlDatabaseObjectHierarchy(top, submap, sourceID);
					DatabaseObject subobj = subhier.getObject();
					mapping.put(name, subobj.getIdentifier());
					hierarchy.addSubobject(subhier);
				}
			}
			}
			DatabaseObject newobj = interpret.fillFromYamlString(top, mapping, sourceID);
			hierarchy.setObject(newobj);
		}
		return hierarchy;
	}
	
	public static void iniitializeFromYamlAndWriteToDatabase(String addr,
			String newuser, String sourceID) throws IOException {
		DatabaseObjectHierarchy tophierarchy = initializeFromYamlDatabaseObjectHierarchy(addr,newuser,sourceID);
		ArrayList<DatabaseObjectHierarchy> lst = new ArrayList<DatabaseObjectHierarchy>();
		Map<String, Object> mapping = getAndModifyYamlMap(addr,newuser);
		recoverConceptFromLinks(tophierarchy,mapping,lst);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(tophierarchy);
		for(DatabaseObjectHierarchy hierarchy : lst) {
			WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(hierarchy);
		}
	}

	public static Map<String, Object> getAndModifyYamlMap(String addr,
			String newuser) throws IOException {
		URL url = new URL(addr);
		BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = breader.readLine()) != null) {
	      builder.append(line + "\n");
	    }
		String modified0 = builder.toString().replaceAll("Guest", newuser);
		Map<String, Object> mapping = stringToYamlMap(modified0);
		return mapping;
	}
	
	public static Map<String, Object> stringToYamlMap(String yaml) throws YamlException {
		InputStream modin = IOUtils.toInputStream(yaml);
		Reader targetReader = new InputStreamReader(modin);
		YamlReader reader = new YamlReader(targetReader);
		Object object = reader.read();
		@SuppressWarnings("unchecked")
		Map<String, Object> mapping = (Map<String, Object>) object;
		return mapping;
	}
	
	public static DatabaseObjectHierarchy initializeFromYamlDatabaseObjectHierarchy(String addr,
			String newuser, String sourceID) throws IOException {
		Map<String, Object> mapping = getAndModifyYamlMap(addr,newuser);
		DatabaseObject top = null;
		DatabaseObjectHierarchy hierarchy = null;
		try {
			hierarchy = ReadWriteYamlDatabaseObjectHierarchy.readYamlDatabaseObjectHierarchy(top, mapping, sourceID);
		} catch (MalformedURLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return hierarchy;
	}

	
	
	public static String yamlMapToString(Map<String,Object> map) throws YamlException {
		StringWriter wS = new StringWriter(1000000);
		YamlWriter writer = new YamlWriter(wS);
		writer.write(map);
		writer.close();
		String yaml = wS.toString();
		System.out.println(yaml);
		return yaml;
	}
	
	public static void recoverConceptFromLinks(DatabaseObjectHierarchy hierarchy, 
			Map<String,Object> map, ArrayList<DatabaseObjectHierarchy> lst) throws IOException  {
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) hierarchy.getObject();
		DatabaseObjectHierarchy linkhier = hierarchy.getSubObject(catalog.getChemConnectObjectLink());
		ArrayList<DatabaseObjectHierarchy> subs = linkhier.getSubobjects();
		for(DatabaseObjectHierarchy sub : subs) {
			DataObjectLink link = (DataObjectLink) sub.getObject();
			if(link.getLinkConcept().compareTo(MetaDataKeywords.linkSubCatalog) == 0) {
				@SuppressWarnings("unchecked")
				Map<String,Object> mapping = (Map<String,Object>) map.get(link.getDataStructure());
				DatabaseObjectHierarchy cathierarchy = readYamlDatabaseObjectHierarchy(null, mapping, null);
				lst.add(cathierarchy);
				recoverConceptFromLinks(cathierarchy,mapping,lst);
			}
		}
	}

	
}
