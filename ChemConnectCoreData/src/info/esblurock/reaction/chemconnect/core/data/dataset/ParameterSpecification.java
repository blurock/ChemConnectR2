package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
@SuppressWarnings("serial")
public class ParameterSpecification extends DataSpecification {
	
	@Index
	String parameterLabel;
	@Index
	String DataPointUncertainty;
	@Index
	String Units;
	

	public ParameterSpecification() {
	}

	public ParameterSpecification(DataSpecification spec, String parameterLabel, String dataPointUncertainty, String units) {
		fill(spec,parameterLabel, dataPointUncertainty,units);
	}
	public ParameterSpecification(ParameterSpecification spec) {
		fill(spec,spec.getParameterLabel(), spec.getDataPointUncertainty(), spec.getUnits());
	}
	
	public void fill(ParameterSpecification spec) {
		fill(spec,spec.getParameterLabel(),spec.getDataPointUncertainty(), spec.getUnits());
	}

	public void fill(DataSpecification spec, String parameterLabel,String dataPointUncertainty, String units) {
		super.fill(spec);
		DataPointUncertainty = dataPointUncertainty;
		Units = units;
		this.parameterLabel = parameterLabel;
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
		builder.append(prefix + "Units:   " + Units + "\n");
		return builder.toString();
	}	

}
