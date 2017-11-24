package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapToChemConnectCompoundDataStructure implements Serializable {

	private static final long serialVersionUID = 1L;
	Map<String, ChemConnectCompoundDataStructure> map;
	
	public MapToChemConnectCompoundDataStructure() {
		map = new HashMap<String, ChemConnectCompoundDataStructure>();
	}
	
	public void addStructure(ChemConnectCompoundDataStructure structure) {
		map.put(structure.getRecordType(), structure);
	}
	
	public ChemConnectCompoundDataStructure getStructure(String structureName) {
		return map.get(structureName);
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix + "MapToChemConnectCompoundDataStructure\n");
		Set<String> keys = map.keySet();
		String newprefix = prefix + "\t";
		for(String key : keys) {
			builder.append(map.get(key).toString(newprefix));
			builder.append("\n");
		}
		return builder.toString();
	}
}
