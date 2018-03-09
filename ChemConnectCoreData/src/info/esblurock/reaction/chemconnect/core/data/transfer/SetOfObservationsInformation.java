package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/*
 * The valueType is put in the value of the PrimitiveDataStructureInformation class
 */

@SuppressWarnings("serial")
public class SetOfObservationsInformation extends PrimitiveParameterValueInformation {
	
	@Unindex
	ArrayList<PrimitiveParameterSpecificationInformation> dimensions;
	@Unindex
	ArrayList<PrimitiveParameterSpecificationInformation> measures;
	@Index
	String topConcept;

	public SetOfObservationsInformation() {
		init();
	}
	
	public SetOfObservationsInformation(SetOfObservationsInformation specs) {
		super(specs);
		init();
		this.topConcept = specs.getTopConcept();
		for(PrimitiveParameterSpecificationInformation info : dimensions) {
			addMeasure(info);
		}
		for(PrimitiveParameterSpecificationInformation info : measures) {
			addDimension(info);
		}
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
