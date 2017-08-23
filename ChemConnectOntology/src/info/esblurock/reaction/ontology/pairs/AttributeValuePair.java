package info.esblurock.reaction.ontology.pairs;

import java.io.Serializable;

import org.json.JSONObject;

public class AttributeValuePair implements Serializable {
	private static final long serialVersionUID = 1L;
	private String propertyValue;
	private String attributeValue;
	
	public AttributeValuePair(String property, String attribute) {
		propertyValue = property;
		attributeValue = attribute;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		if(propertyValue == null) {
				propertyValue = "";
		}
		if(attributeValue == null) {
			attributeValue = "";
		}
		obj.put(propertyValue,attributeValue);
		
		return obj;
	}
	@Override
	public String toString() {
		return toJSON().toString();
	}
}
