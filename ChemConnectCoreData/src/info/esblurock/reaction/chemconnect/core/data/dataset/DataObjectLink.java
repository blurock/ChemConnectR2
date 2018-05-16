package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class DataObjectLink  extends ChemConnectCompoundDataStructure {
	@Index
	String linkConcept;
	@Index
	String dataStructure;
	
	
	public DataObjectLink() {
	}
	public DataObjectLink(DataObjectLink link) {
		fill(link, link.getLinkConcept(), link.getDataStructure());
	}
	public DataObjectLink(ChemConnectCompoundDataStructure data, String linkConcept, String dataStructure) {
		fill(data, linkConcept, dataStructure);
	}
	public void fill(ChemConnectCompoundDataStructure data, String linkConcept, String dataStructure) {
		super.fill(data);
		this.linkConcept = linkConcept;
		this.dataStructure = dataStructure;
	}
	public String getLinkConcept() {
		return linkConcept;
	}
	public String getDataStructure() {
		return dataStructure;
	}
	
	
	
	public void setLinkConcept(String linkConcept) {
		this.linkConcept = linkConcept;
	}
	public void setDataStructure(String dataStructure) {
		this.dataStructure = dataStructure;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Concept: " + linkConcept + "\n");
		builder.append(prefix + "Data:    " + dataStructure + "\n");
		return builder.toString();
	}	
	
	
	

}
