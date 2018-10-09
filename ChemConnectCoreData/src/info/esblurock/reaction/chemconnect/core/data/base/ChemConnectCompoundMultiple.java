package info.esblurock.reaction.chemconnect.core.data.base;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultiple extends DatabaseObject {
	@Index 
	String type;
	@Index 
	int numberOfElements;
	
	public ChemConnectCompoundMultiple() {
		super();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj, String type) {
		super(obj);
		this.type = type;
		numberOfElements = 0;
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj,String type, int numberOfElements) {
		super(obj);
		this.type = type;
		this.numberOfElements = numberOfElements;
	}
	public void fill(DatabaseObject obj) {
		super.fill(obj);
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) obj;
		this.type = multiple.getType();
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + numberOfElements +  " elements of type: '" + type + "'\n");
		return build.toString();
	}
}
