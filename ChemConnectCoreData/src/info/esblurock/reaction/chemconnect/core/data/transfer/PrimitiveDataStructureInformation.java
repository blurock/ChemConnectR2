package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;

public class PrimitiveDataStructureInformation implements Serializable{
	private static final long serialVersionUID = 1L;

	String propertyType;
	String identifier;
	String value;
	
	public PrimitiveDataStructureInformation() {
	}
	
	public PrimitiveDataStructureInformation(String propertyType, String identifier,
			String value) {
		super();
		this.propertyType = propertyType;
		this.identifier = identifier;
		this.value = value;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public String getValue() {
		return value;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(propertyType);
		builder.append(" (");
		builder.append(identifier);
		builder.append(") ");
		builder.append(value);
		return builder.toString();
	}
	
}
