package info.esblurock.reaction.chemconnect.core.data.transfer;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class PrimitiveDataStructureInformation extends DatabaseObject {

	@Index
	String propertyType;
	@Index
	String value;
	
	public PrimitiveDataStructureInformation() {
	}
	
	public PrimitiveDataStructureInformation(DatabaseObject obj, String propertyType,
			String value) {
		super(obj);
		this.propertyType = propertyType;
		this.value = value;
	}
	public PrimitiveDataStructureInformation(PrimitiveDataStructureInformation info) {
		super(info);
		this.propertyType = info.getPropertyType();
		this.value = info.getValue();
	}
	public String getPropertyType() {
		return propertyType;
	}
	public String getValue() {
		return value;
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
		builder.append(" (");
		builder.append(propertyType);
		builder.append(") ");
		if(value != null) {
			builder.append(value);
		}
		return builder.toString();
	}
	
}
