package info.esblurock.reaction.ontology.dataset;

import java.io.Serializable;

public class DataElementInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	String dataElementName;
	boolean singlet;
	int numberOfElements;
	String chemconnectStructure;
	
	public DataElementInformation(String dataElementName, boolean singlet, int numberOfElements, String chemconnectStructure) {
		super();
		this.dataElementName = dataElementName;
		this.singlet = singlet;
		this.numberOfElements = numberOfElements;
		this.chemconnectStructure = chemconnectStructure;
	}
	public String getDataElementName() {
		return dataElementName;
	}
	public boolean isSinglet() {
		return singlet;
	}
	public int numberOfElements() {
		return numberOfElements;
	}
	public String getChemconnectStructure() {
		return chemconnectStructure;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(dataElementName);
		build.append("  (" + chemconnectStructure + "):  ");
		if(singlet) {
			build.append("single");
		} else {
			build.append("multiple");
		} 
		if(numberOfElements > 1) {
			build.append("# " + numberOfElements);
		}
		return build.toString();
	}

}
