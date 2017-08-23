package info.esblurock.reaction.ontology.dataset;

public class DataElementInformation {
	
	String dataElementName;
	boolean singlet;
	int numberOfElements;
	
	public DataElementInformation(String dataElementName, boolean singlet, int numberOfElements) {
		super();
		this.dataElementName = dataElementName;
		this.singlet = singlet;
		this.numberOfElements = numberOfElements;
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
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(dataElementName + ": ");
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
