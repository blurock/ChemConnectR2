package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class MeasurementParameterValue extends ParameterValue {

	public MeasurementParameterValue() {
		super();
	}
	public MeasurementParameterValue(ParameterValue value) {
		super(value);
	}
	public MeasurementParameterValue(DatabaseObject attribute, String valueAsString, String uncertainty,
			String parameterSpec) {
		super(attribute, valueAsString, uncertainty, parameterSpec);
	}
	
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		return builder.toString();
	}

}
