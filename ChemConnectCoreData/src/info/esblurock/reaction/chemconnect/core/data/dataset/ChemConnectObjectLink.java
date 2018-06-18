package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ChemConnectObjectLink extends ChemConnectCompoundDataStructure {

	@Index
	String dataStructure;
	@Index
	String linkConceptType;
	@Index
	String dataConceptType;
	
	public ChemConnectObjectLink() {
		dataStructure = "";
		linkConceptType = "";
		dataConceptType = "";
	}
	public ChemConnectObjectLink(String identifier, String sourceID) {
		super(identifier,sourceID);
		dataStructure = "";
		linkConceptType = "";
		dataConceptType = "";
	}
	public ChemConnectObjectLink(ChemConnectCompoundDataStructure compound, String dataStructure, String linkType, String dataType) {
		super(compound);
		this.dataStructure = dataStructure;
		this.linkConceptType = linkType;
		this.dataConceptType = dataType;
	}
	
	public void fill(ChemConnectCompoundDataStructure compound, String dataStructure, String linkType, String dataType) {
		super.fill(compound);
		this.dataStructure = dataStructure;
		this.linkConceptType = linkType;
		this.dataConceptType = dataType;
	}
	public void fill(DatabaseObject object) {
		super.fill(object);
		ChemConnectObjectLink link = (ChemConnectObjectLink) object;
		this.dataStructure = link.getDataStructure();
		this.linkConceptType = link.getLinkConceptType();
		this.dataConceptType = link.getDataConceptType();
	}
	public String getDataStructure() {
		return dataStructure;
	}
	public String getLinkConceptType() {
		return linkConceptType;
	}
	public String getDataConceptType() {
		return dataConceptType;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append("Data Structure: " + dataStructure + "\n");
		build.append("Link Concept: " + linkConceptType + "\n");
		build.append("Data Concept: " + dataConceptType + "\n");
		return build.toString();
	}
	
	
	
}
