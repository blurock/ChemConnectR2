package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class ParameterSpecification extends DataSpecification {
	
	@Index
	String parameterLabel;
	@Index
	String observationParameterType;
	@Index
	String DataPointUncertainty;
	@Index
	String Units;
	

	public ParameterSpecification() {
	}

	public ParameterSpecification(DataSpecification spec, String parameterLabel, 
			String dataPointUncertainty, String units, String observationParameterType) {
		this.fill(spec,parameterLabel, dataPointUncertainty,units,observationParameterType);
	}
	public ParameterSpecification(ParameterSpecification spec) {
		this.fill(spec,spec.getParameterLabel(), 
				spec.getDataPointUncertainty(), spec.getUnits(), spec.getObservationParameterType());
	}
	
	public void fill(ParameterSpecification spec) {
		this.fill(spec,spec.getParameterLabel(),
				spec.getDataPointUncertainty(), spec.getUnits(), spec.getObservationParameterType());
	}

	public void fill(DataSpecification spec, String parameterLabel,
			String dataPointUncertainty, String units, String observationParameterType) {
		super.fill(spec);
		DataPointUncertainty = dataPointUncertainty;
		Units = units;
		this.parameterLabel = parameterLabel;
		this.observationParameterType = observationParameterType;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ParameterSpecification spec = (ParameterSpecification) object;
		DataPointUncertainty = spec.getDataPointUncertainty();
		Units = spec.getUnits();
		this.parameterLabel = spec.getParameterLabel();
		this.observationParameterType = spec.getObservationParameterType();
	}

	public String getDataPointUncertainty() {
		return DataPointUncertainty;
	}

	public String getUnits() {
		return Units;
	}

	public void setDataPointUncertainty(String dataPointUncertainty) {
		DataPointUncertainty = dataPointUncertainty;
	}

	public void setUnits(String units) {
		Units = units;
	}

	public String getParameterLabel() {
		return parameterLabel;
	}

	public void setParameterLabel(String parameterLabel) {
		this.parameterLabel = parameterLabel;
	}

	public String getObservationParameterType() {
		return observationParameterType;
	}

	public void setObservationParameterType(String observationParameterType) {
		this.observationParameterType = observationParameterType;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Parameter Label: " + parameterLabel + "\n");
		builder.append(prefix + " +/-: " + DataPointUncertainty + "\n");
		builder.append(prefix + "Units:   " + Units + " (" + observationParameterType +  ")\n");
		return builder.toString();
	}	

}
