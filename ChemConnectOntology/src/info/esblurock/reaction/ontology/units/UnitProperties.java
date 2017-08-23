package info.esblurock.reaction.ontology.units;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;

public class UnitProperties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String unitName;
	AttributeList propertyList;
	ArrayList<String> types;
	
	public UnitProperties() {
		init();
	}
	
	public UnitProperties(String unitName) {
		this.unitName = unitName;
		init();
	}
	
	void init() {
		propertyList = new AttributeList();
		types = new ArrayList<String>();
	}
	public void addProperty(String attribute, String value) {
		Attribute pair = new Attribute(attribute,value);
		propertyList.add(pair);
	}
	public void addType(String type) {
		types.add(type);
	}	
	public String getValue(String attribute) {
		String value = null;
		Iterator<Object> iter = propertyList.iterator();
		while(iter.hasNext() && value == null) {
			Attribute att = (Attribute) iter.next();
			if(att.getName().compareTo(attribute) == 0) {
				value = (String) att.getValue();
			}
		}
		return value;
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
	public AttributeList getPropertyList() {
		return propertyList;
	}
	public ArrayList<String> getTypes() {
		return types;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Unit: '" + unitName + "'\n");
		for(Object obj : propertyList) {
			Attribute att = (Attribute) obj;
			build.append("a: '" + att.getName() + "'\t v: '" + att.getValue() + "'\n");
		}
		build.append("Types: ");
		for(String type : types) {
			build.append(type + "\t");
		}
		build.append("\n");
		return build.toString();
	}
	

}
