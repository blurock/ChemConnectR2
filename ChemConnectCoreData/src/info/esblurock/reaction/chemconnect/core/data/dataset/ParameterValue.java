package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ParameterValue extends AttributeInDataset {
	
	@Index
	String valueAsString;
	@Index
	String uncertainty;
	@Index
	String parameterSpec;
	
	public ParameterValue() {
	}
	public ParameterValue(AttributeInDataset attribute, String valueAsString, String uncertainty, String parameterSpec) {
		fill(attribute,valueAsString,uncertainty,parameterSpec);
	}
	public void fill(AttributeInDataset attribute, String valueAsString, String uncertainty, String parameterSpec) {
		super.fill(attribute);
		this.valueAsString = valueAsString;
		this.uncertainty = uncertainty;
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
