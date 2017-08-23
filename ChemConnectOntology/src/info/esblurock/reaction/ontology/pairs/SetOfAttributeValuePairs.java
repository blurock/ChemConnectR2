package info.esblurock.reaction.ontology.pairs;

import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class SetOfAttributeValuePairs extends HashSet<AttributeValuePair>{
	private static final long serialVersionUID = 1L;

	static final String attributeSetKey = "AttributeSetKey";
	
	String attributeSetName;
	
	public SetOfAttributeValuePairs(String name) {
		attributeSetName = name;
	}
	public SetOfAttributeValuePairs(SetOfAttributeValuePairs pairs) {
		attributeSetName = pairs.attributeSetName;
		for(AttributeValuePair pair: pairs) {
			this.add(pair);
		}
	}

	public JSONObject toJSON() {
		JSONArray arr = new JSONArray();
		int count = 0;
		for(AttributeValuePair pair: this) {
			JSONObject json = pair.toJSON();
			arr.put(count++, json);
		}
		JSONObject obj = new JSONObject();
		obj.put(attributeSetName, arr);
		return obj;
	}
	
	@Override
	public String toString() {
		JSONObject obj = this.toJSON();
		return obj.toString();
	}
	
	public String getProperty(String key) {
		String property = null;
		Iterator<AttributeValuePair> iter = this.iterator();
		while(property == null && iter.hasNext()) {
			AttributeValuePair pair = iter.next();
			if(pair.getAttributeValue().equals(key)) {
				property = pair.getPropertyValue();
			}
		}
		return property;
	}
}
