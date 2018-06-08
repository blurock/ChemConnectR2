package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class SetOfObservationValues extends ChemConnectDataStructure {
	@Index
	String parameterType;
	@Index
	String measurementValues;
	@Index
	String dimensionValues;

	public SetOfObservationValues() {
		init();
	}
	
	public SetOfObservationValues(ChemConnectDataStructure structure) {
		super(structure);
		init();
	}
	
	public SetOfObservationValues(ChemConnectDataStructure structure, 
			String parameterType, String measurementValues, String dimensionValues) {
		super(structure);
		init();
		this.parameterType = parameterType;
		this.measurementValues = measurementValues;
		this.dimensionValues = dimensionValues;
	}
	
	void init() {
		measurementValues = "";
		dimensionValues = "";
		parameterType = "";
	}
	
	public String getMeasurementValues() {
		return measurementValues;
	}

	public String getDimensionValues() {
		return dimensionValues;
	}

	public void setMeasurementValues(String measurementValues) {
		this.measurementValues = measurementValues;
	}

	public void setDimensionValues(String dimensionValues) {
		this.dimensionValues = dimensionValues;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parameter:   " + parameterType + "\n");
		build.append(prefix + "Measure:     " + measurementValues + "\n");
		build.append(prefix + "Dimension:   " + dimensionValues + "\n");
		return build.toString();
	}
	
}
