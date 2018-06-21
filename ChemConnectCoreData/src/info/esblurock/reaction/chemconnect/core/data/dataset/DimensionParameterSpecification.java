package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

@Entity
@SuppressWarnings("serial")
public class DimensionParameterSpecification extends ParameterSpecification {

	public DimensionParameterSpecification() {
		super();
	}

	public DimensionParameterSpecification(DataSpecification spec, String parameterLabel, String dataPointUncertainty,
			String units) {
		super(spec, parameterLabel, dataPointUncertainty, units);
	}
	public DimensionParameterSpecification(ParameterSpecification spec) {
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
