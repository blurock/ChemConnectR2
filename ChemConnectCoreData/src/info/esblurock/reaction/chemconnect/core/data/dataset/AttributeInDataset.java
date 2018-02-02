package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class AttributeInDataset extends DatabaseObject {
	
	@Index
	String parameterLabel;

	public AttributeInDataset() {
	}
	public AttributeInDataset(AttributeInDataset attribute) {
		fill(attribute);
	}
	public AttributeInDataset(DatabaseObject obj, String parameterLabel) {
		fill(obj,parameterLabel);
	}
	
	public void fill(AttributeInDataset attribute) {
		super.fill(attribute);
		this.parameterLabel = attribute.getParameterLabel();
	}
	public void fill(DatabaseObject obj, String parameterLabel) {
		super.fill(obj);
		this.parameterLabel = parameterLabel;
	}
	public String getParameterLabel() {
		return parameterLabel;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Label: " + parameterLabel + "\n");
		return build.toString();
	}
}
