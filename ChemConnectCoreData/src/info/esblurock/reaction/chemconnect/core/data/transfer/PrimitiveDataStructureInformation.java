package info.esblurock.reaction.chemconnect.core.data.transfer;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class PrimitiveDataStructureInformation extends DatabaseObject {

	@Index
	String type;
	@Index
	String propertyType;
	@Index
	String value;
	
	public PrimitiveDataStructureInformation() {
		this.type = null;
		this.propertyType = null;
		this.value = null;
	}
	
	public PrimitiveDataStructureInformation(DatabaseObject obj, String type, String propertyType,
			String value) {
		super(obj);
		this.type = type;
		this.propertyType = propertyType;
		this.value = value;
	}
	public PrimitiveDataStructureInformation(PrimitiveDataStructureInformation info) {
		super(info);
		this.type = info.getType();
		this.propertyType = info.getPropertyType();
		this.value = info.getValue();
	}
	public String getPropertyType() {
		return propertyType;
	}
	public String getType() {
		return type;
	}
	public String getValue() {
		return value;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + " (");
		builder.append(propertyType);
		builder.append(",");
		builder.append(type);
		builder.append(") ");
		if(value != null) {
			builder.append(value);
		}
		return builder.toString();
	}
	
}
