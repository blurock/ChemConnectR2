package info.esblurock.reaction.chemconnect.core.data.base;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;


@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundDataStructure extends DatabaseObject {
	
	@Index
	String parentLink;

	public ChemConnectCompoundDataStructure() {
		parentLink = null;
	}

	public ChemConnectCompoundDataStructure(String identifier, String sourceID) {
		parentLink = null;
	}

	public ChemConnectCompoundDataStructure(DatabaseObject dataobject, String parentLink) {
		this.fill(dataobject,parentLink);
	}
	
	public ChemConnectCompoundDataStructure(ChemConnectCompoundDataStructure compound) {
		super(compound);
		this.parentLink = compound.getParentLink();
	}
	
	public void fill(DatabaseObject object) {
		super.fill(object);
		ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) object;
		this.parentLink = compound.getParentLink();
	}

	public void fill(DatabaseObject dataobject, String parentLink) {
		super.fill(dataobject);
		this.parentLink = parentLink;
	}
		
	public String getParentLink() {
		return parentLink;
	}
	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parent: " + parentLink + "\n");
		return build.toString();
	}
}
