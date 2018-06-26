package info.esblurock.reaction.core.server.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
			if (subname.compareTo("dataset:ChemConnectCompoundMultiple") == 0) {
				ArrayList<String> lst = (ArrayList<String>) obj;
				Map<String, Object> multobj = new HashMap<String, Object>();
				mapping.put("dataset:ChemConnectCompoundMultiple", multobj);
				for (String subobjname : lst) {
					DatabaseObjectHierarchy submulthier = objecthierarchy.getSubObject(subobjname);
					Map<String, Object> submultobj = yamlDatabaseObjectHierarchy(submulthier);
					multobj.put(subobjname, submultobj);
				}
			} else {
				String objname = (String) obj;
				DatabaseObjectHierarchy objhier = objecthierarchy.getSubObject(objname);
				if (objhier != null) {
					Map<String, Object> subobjmapping = yamlDatabaseObjectHierarchy(objhier);
					mapping.put(subname, subobjmapping);
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
			Map<String, Object> submap = (Map<String, Object>) mapping.get("dataset:ChemConnectCompoundMultiple");
			if (submap != null) {
				Set<String> keys = submap.keySet();
				for (String key : keys) {
					Map<String, Object> multimap = (Map<String, Object>) submap.get(key);
					DatabaseObjectHierarchy subhier = readYamlDatabaseObjectHierarchy(top, multimap, sourceID);
					hierarchy.addSubobject(subhier);
				}
			}
			DatabaseObject obj = interpret.fillFromYamlString(top, mapping, sourceID);
			hierarchy.setObject(obj);

		} else {
			Set<String> keys = mapping.keySet();
			for (String name : keys) {
				Object obj = mapping.get(name);
				if (!String.class.isInstance(obj)) {
					Map<String, Object> submap = (Map<String, Object>) obj;
					DatabaseObjectHierarchy subhier = readYamlDatabaseObjectHierarchy(top, submap, sourceID);
					DatabaseObject subobj = subhier.getObject();
					mapping.put(name, subobj.getIdentifier());
					hierarchy.addSubobject(subhier);
				}
			}
		}
		DatabaseObject obj = interpret.fillFromYamlString(top, mapping, sourceID);
		hierarchy.setObject(obj);
		return hierarchy;
	}

}
