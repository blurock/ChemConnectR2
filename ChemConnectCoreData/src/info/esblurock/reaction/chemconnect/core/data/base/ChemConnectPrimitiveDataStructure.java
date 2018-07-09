package info.esblurock.reaction.chemconnect.core.data.base;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectPrimitiveDataStructure extends DatabaseObject {
	@Index 
	String simpleObject;
	@Index 
	String objectType;
	
	public ChemConnectPrimitiveDataStructure() {
		super();
	}
	public ChemConnectPrimitiveDataStructure(DatabaseObject object, String simpleObject, String objectType) {
		super(object);
		this.simpleObject = simpleObject;
		this.objectType = objectType;
	}
	public String getSimpleObject() {
		return simpleObject;
	}
	public void setStringObject(String simpleObject) {
		this.simpleObject = simpleObject;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append("Object: " + simpleObject + "\n");
		build.append("Type  : " + objectType + "\n");
		return build.toString();
	}
}
