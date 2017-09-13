package info.esblurock.reaction.ontology.dataset;

import java.io.Serializable;

public class DataElementInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	String dataElementName;
	boolean singlet;
	int numberOfElements;
	String chemconnectStructure;
	String identifier;
	String link;
	
	public DataElementInformation(String dataElementName, String link, boolean singlet, int numberOfElements, 
			String chemconnectStructure, String identifier) {
		super();
		this.dataElementName = dataElementName;
		this.singlet = singlet;
		this.numberOfElements = numberOfElements;
		this.chemconnectStructure = chemconnectStructure;
		this.identifier = identifier;
		this.link = link;
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
	public String getIdentifier() {
		return identifier;
	}
	public boolean isID() {
		return isChemConnectType("ID");
	}
	public boolean isChemConnectType(String typename) {
		return chemconnectStructure.compareTo(typename) == 0;
	}
	public boolean isDataElementType(String typename) {
		return dataElementName.compareTo(typename) == 0;
	}
	public boolean isKeywords() {
		return isChemConnectType("Keywords");
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(dataElementName);
		if(link != null) {
			build.append("(");
			build.append(link);
			build.append("):  ");
		} else {
			build.append(":  ");
		}
		build.append(identifier);
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
