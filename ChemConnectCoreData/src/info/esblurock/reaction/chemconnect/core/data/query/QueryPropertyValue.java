package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;

public class QueryPropertyValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String property;
	String value;

	
	public QueryPropertyValue() {
	}
	public QueryPropertyValue(String property, String value) {
		this.property = property;
		this.value = value;
	}
	public String getProperty() {
		return property;
	}
	public String getValue() {
		return value;
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("(");
		build.append(property);
		build.append(", ");
		build.append(value);
		build.append(")");
		return build.toString();
	}

}
