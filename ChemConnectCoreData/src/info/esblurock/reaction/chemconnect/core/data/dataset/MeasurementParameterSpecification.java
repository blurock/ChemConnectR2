package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

@Entity
@SuppressWarnings("serial")
public class MeasurementParameterSpecification extends ParameterSpecification {

	public MeasurementParameterSpecification() {
		super();
	}

	public MeasurementParameterSpecification(DataSpecification spec, String parameterLabel, String dataPointUncertainty,
			String units, String parameterType) {
		super(spec, parameterLabel, dataPointUncertainty, units,parameterType);
	}

	public MeasurementParameterSpecification(ParameterSpecification spec) {
		super(spec);
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		return builder.toString();
	}	

	
}
