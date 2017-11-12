package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;

public class PrimitivePropertyValueInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String value;
	String propertyClass;
	String propertyType;
	
	public PrimitivePropertyValueInformation() {
	}
	
	public PrimitivePropertyValueInformation(String value, String propertyClass, String propertyType) {
		super();
		this.value = value;
		this.propertyClass = propertyClass;
		this.propertyType = propertyType;
	}
	public String getValue() {
		return value;
	}
	public String getPropertyClass() {
		return propertyClass;
	}
	public String getPropertyType() {
		return propertyType;
	}
}
