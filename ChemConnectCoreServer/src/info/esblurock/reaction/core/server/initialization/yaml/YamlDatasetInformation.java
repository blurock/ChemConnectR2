package info.esblurock.reaction.core.server.initialization.yaml;

import java.util.List;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.ontology.dataset.DataElementInformation;
import info.esblurock.reaction.ontology.test.dataset.ClassificationInformation;

public class YamlDatasetInformation {

	String dataelement;
	DatabaseObject object;
	ClassificationInformation classification;
	List<DataElementInformation> elementInformation;

	public YamlDatasetInformation(String dataelement, DatabaseObject object, ClassificationInformation classification,
			List<DataElementInformation> elements) {
		this.dataelement = dataelement;
		this.object = object;
		this.classification = classification;
		this.elementInformation = elements;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Dataset: ");
		build.append(dataelement);
		build.append("\n");
		if(object != null) {
			build.append("Object: \t(" + object.getClass().getCanonicalName() + ") " +  object.getIdentifier() + "\n");
		}
		String classificationS = classification.toString();
		build.append("Classification: ");
		build.append(classificationS);
		build.append("\nElement Information:\n");
		if (elementInformation == null) {
			build.append("no elements\n");
		} else {
			for (DataElementInformation info : elementInformation) {
				String structureS = info.toString();
				build.append("\t" + structureS + "\n");
			}
		}
		return build.toString();
	}

	public String getDataelement() {
		return dataelement;
	}

	public DatabaseObject getObject() {
		return object;
	}

	public ClassificationInformation getClassification() {
		return classification;
	}

	public List<DataElementInformation> getElementInformation() {
		return elementInformation;
	}
	
}
