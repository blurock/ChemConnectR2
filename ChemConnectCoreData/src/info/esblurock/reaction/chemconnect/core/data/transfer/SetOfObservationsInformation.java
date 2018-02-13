package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.ArrayList;

public class SetOfObservationsInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<PrimitiveParameterSpecificationInformation> dimensions;
	ArrayList<PrimitiveParameterSpecificationInformation> measures;
	String valueType;
	String topConcept;
	
	public SetOfObservationsInformation() {
		init();
	}

	void init() {
		dimensions = new ArrayList<PrimitiveParameterSpecificationInformation>();
		measures = new ArrayList<PrimitiveParameterSpecificationInformation>();
		valueType = null;
	}
	
	public void addMeasure(PrimitiveParameterSpecificationInformation measure) {
		measures.add(measure);
	}
	public void addDimension(PrimitiveParameterSpecificationInformation dimension) {
		dimensions.add(dimension);
	}
	
	
	
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getTopConcept() {
		return topConcept;
	}

	public void setTopConcept(String topConcept) {
		this.topConcept = topConcept;
	}

	public ArrayList<PrimitiveParameterSpecificationInformation> getDimensions() {
		return dimensions;
	}

	public ArrayList<PrimitiveParameterSpecificationInformation> getMeasures() {
		return measures;
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Set of Observations: (" + topConcept + ") " + valueType + "\n");
		String newprefix = prefix + "\t";

		build.append(prefix + "Dimensions:\n");
		for(PrimitiveParameterSpecificationInformation info : dimensions) {
			newprefix = prefix + "\t";
			build.append(info.toString(newprefix));
		}
		build.append("Measures:\n");
		for(PrimitiveParameterSpecificationInformation info : measures) {
			newprefix = prefix + "\t";
			build.append(info.toString(newprefix));
		}
		return build.toString();
	}
}
