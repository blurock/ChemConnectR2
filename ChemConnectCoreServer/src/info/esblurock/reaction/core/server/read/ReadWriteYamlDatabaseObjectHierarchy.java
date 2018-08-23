package info.esblurock.reaction.core.server.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;

public class ReadWriteYamlDatabaseObjectHierarchy {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> yamlDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy)
			throws IOException {
		Map<String, Object> mapping = new HashMap<String, Object>();
		DatabaseObject object = objecthierarchy.getObject();
		String name = object.getClass().getSimpleName();
		mapping.put("structure", name);
		InterpretData interpret = InterpretData.valueOf(name);
		Map<String, Object> objmapping = interpret.createYamlFromObject(object);
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
					mapping.put(subname, id);
					mapping.put(id, subobjmapping);
				} else {
					String n = (String) objmapping.get(subname);
					mapping.put(subname, n);
				}					

			}
		}
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
			if (submap != null) {
				Set<String> keys = submap.keySet();
				for (String key : keys) {
					mult.addID(key);
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
						System.out.println("readYamlDatabaseObjectHierarchy: " + name);
						System.out.println("readYamlDatabaseObjectHierarchy: \n" + obj.toString());
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

}
