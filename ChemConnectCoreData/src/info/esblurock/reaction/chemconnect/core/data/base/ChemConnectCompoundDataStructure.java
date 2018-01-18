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
		fill(dataobject,parentLink);
	}
	
	public ChemConnectCompoundDataStructure(ChemConnectCompoundDataStructure compound) {
		fill(compound);
	}

	public void fill(ChemConnectCompoundDataStructure compound) {
		super.fill(compound);
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

}
