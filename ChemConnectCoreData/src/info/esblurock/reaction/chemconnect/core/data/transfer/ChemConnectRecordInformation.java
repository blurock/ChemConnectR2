package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectCompoundDataStructure;

public class ChemConnectRecordInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	DatabaseObject object;
	String structureType;
	ChemConnectCompoundDataStructure structure;
	Map<String,Object> mapping;
	
	Map<String, DataElementInformation> recordmap;
	
	public ChemConnectRecordInformation() {
		this.object = null;
		this.structureType = "";
		this.structure = null;
		this.recordmap = new HashMap<String, DataElementInformation>();
	}
	
	public ChemConnectRecordInformation(DatabaseObject object, String structureType,
			ChemConnectCompoundDataStructure structure,
			Map<String,Object> mapping) {
		super();
		this.object = object;
		this.structureType = structureType;
		this.structure = structure;
		this.mapping = mapping;
		this.recordmap = new HashMap<String, DataElementInformation>();
		for(DataElementInformation element : structure) {
			String name = element.getIdentifier();
			this.recordmap.put(name, element);
		}
	}
	public DatabaseObject getObject() {
		return object;
	}
	public void setObject(DatabaseObject object) {
		this.object = object;
	}
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	public ChemConnectCompoundDataStructure getStructure() {
		return structure;
	}
	public void setStructure(ChemConnectCompoundDataStructure structure) {
		this.structure = structure;
	}
	
	public Map<String, Object> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, Object> mapping) {
		this.mapping = mapping;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		build.append(prefix + "ChemConnectRecordInformation\n");
		
		build.append(prefix + "Structure Type: " + structureType);
		
		
		
		build.append(prefix + "Object:\n");
		if(object != null) {
			build.append(object.toString(prefix));
		} else {
			build.append("no object defined\n");
		}
		build.append(prefix + "Structure:\n");
		if(structure != null) {
			build.append(structure.toString(prefix));
		} else {
			build.append("no structure defined\n");
		}
		build.append(prefix + "Mapping:\n");
		Set<String> names = mapping.keySet();
		for(String name : names) {
			build.append(prefix + " " + name + ":  " + mapping.get(name) + "\n");
		}
		
		
		return build.toString();
	}
	
}
