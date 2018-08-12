package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ParameterValue extends DatabaseObject {
	
	@Index
	String valueAsString;
	@Index
	String uncertainty;
	@Index
	String parameterSpec;
	
	public ParameterValue() {
	}

	public ParameterValue(DatabaseObject attribute, String valueAsString, String uncertainty, String parameterSpec) {
		this.fill(attribute,valueAsString,uncertainty,parameterSpec);
	}
	
	public ParameterValue(ParameterValue value) {
		this.fill(value);
	}

	public void fill(ParameterValue value) {
		super.fill(value);
		this.valueAsString = value.getValueAsString();
		this.uncertainty = value.getUncertainty();
		this.parameterSpec = value.getParameterSpec();
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ParameterValue value = (ParameterValue) object;
		this.valueAsString = value.getValueAsString();
		this.uncertainty = value.getUncertainty();
		this.parameterSpec = value.getParameterSpec();
	}
	
	public void fill(DatabaseObject attribute, String valueAsString, String uncertainty, String parameterSpec) {
		super.fill(attribute);
		this.valueAsString = valueAsString;
		this.uncertainty = uncertainty;
		this.parameterSpec = parameterSpec;
	}
	public String getValueAsString() {
		return valueAsString;
	}
	public String getUncertainty() {
		return uncertainty;
	}
	public String getParameterSpec() {
		return parameterSpec;
	}
	
	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}
	public void setUncertainty(String uncertainty) {
		this.uncertainty = uncertainty;
	}
	public void setParameterSpec(String parameterSpec) {
		this.parameterSpec = parameterSpec;
	}
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "value: " + valueAsString);
		if(uncertainty != null)
			builder.append(" +/- " + uncertainty);
		if(parameterSpec != null) {
			builder.append("  (spec: " + parameterSpec + ")");
		}
		builder.append("\n");
		return builder.toString();
	}
}
