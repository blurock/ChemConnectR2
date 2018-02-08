package info.esblurock.reaction.chemconnect.core.data.concepts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class UnitProperties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String unitName;
	Map<String,String> properties;
	ArrayList<String> types;
	
	public UnitProperties() {
		init();
	}
	
	public UnitProperties(String unitName) {
		this.unitName = unitName;
		init();
	}
	
	void init() {
		properties = new HashMap<String,String>();
		types = new ArrayList<String>();
	}
	public void addProperty(String attribute, String value) {
		properties.put(attribute, value);
	}
	public void addType(String type) {
		types.add(type);
	}	
	public String getValue(String attribute) {
		return properties.get(attribute);
	}
	public boolean isOfType(String type) {
		boolean ans = false;
		Iterator<String> iter = types.iterator();
		while(!ans && iter.hasNext()) {
			String t = iter.next();
			if(type.compareTo(t) == 0) {
				ans = true;
			}
		}
		return ans;
	}
	public Map<String,String> getPropertyList() {
		return properties;
	}
	public ArrayList<String> getTypes() {
		return types;
	}

	public String getUnitName() {
		return unitName;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Unit: '" + unitName + "'\n");
		String newprefix = prefix + "\t";
		Set<String> names = properties.keySet();
		for(String name : names) {
			build.append(newprefix + "a: '" + name + "'\t v: '" + properties.get(name) + "'\n");
		}
		build.append(prefix + "Types: ");
		for(String type : types) {
			build.append(newprefix + type + "\t");
		}
		build.append("\n");
		return build.toString();
	}
	

}
