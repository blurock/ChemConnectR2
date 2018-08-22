package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

@Entity
@SuppressWarnings("serial")
public class MeasureParameterSpecification extends ParameterSpecification {

	public MeasureParameterSpecification() {
		super();
	}

	public MeasureParameterSpecification(DataSpecification spec, String parameterLabel, String dataPointUncertainty,
			String units, String parameterType) {
		super(spec, parameterLabel, dataPointUncertainty, units,parameterType);
	}

	public MeasureParameterSpecification(ParameterSpecification spec) {
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
