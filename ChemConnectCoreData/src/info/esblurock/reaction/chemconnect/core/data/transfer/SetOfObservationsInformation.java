package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.util.ArrayList;

/*
 * The valueType is put in the value of the PrimitiveDataStructureInformation class
 */

public class SetOfObservationsInformation extends PrimitiveParameterValueInformation {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<PrimitiveParameterSpecificationInformation> dimensions;
	ArrayList<PrimitiveParameterSpecificationInformation> measures;
	String topConcept;
	
	public SetOfObservationsInformation() {
		init();
	}
	
	public SetOfObservationsInformation(String identifier, String label, String topConcept, String valueType ) {
		super(label,identifier,valueType);
		this.topConcept = topConcept;
		init();
	}

	void init() {
		dimensions = new ArrayList<PrimitiveParameterSpecificationInformation>();
		measures = new ArrayList<PrimitiveParameterSpecificationInformation>();
	}
	
	public void addMeasure(PrimitiveParameterSpecificationInformation measure) {
		measures.add(measure);
	}
	public void addDimension(PrimitiveParameterSpecificationInformation dimension) {
		dimensions.add(dimension);
	}
	
	
	
	public String getValueType() {
		return getValue();
	}

	public void setValueType(String valueType) {
		setValue(valueType);
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
		build.append(prefix + "Set of Observations: (" + topConcept + ")\n");
		String newprefix = prefix + "\t";

		build.append(prefix + "Dimensions:\n");
		for(PrimitiveParameterSpecificationInformation info : dimensions) {
			newprefix = prefix + "\t";
			build.append(info.toString(newprefix));
		}
		build.append(prefix + "Measures:\n");
		for(PrimitiveParameterSpecificationInformation info : measures) {
			newprefix = prefix + "\t";
			build.append(info.toString(newprefix));
		}
		return build.toString();
	}
}
