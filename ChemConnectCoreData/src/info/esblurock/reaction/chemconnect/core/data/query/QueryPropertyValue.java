package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;

public class QueryPropertyValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String property;
	boolean stringvalue;
	String valueS;
	boolean intvalue;
	int valueI;
	boolean doublevalue;
	double valueD;
	
	void init() {
		stringvalue = false;
		intvalue = false;
		doublevalue = false;
	}
	public QueryPropertyValue() {
	}
	public QueryPropertyValue(String property, String value) {
		this.property = property;
		this.valueS = value;
		stringvalue = true;
	}
	public QueryPropertyValue(String property, int value) {
		this.property = property;
		this.valueI = value;
		intvalue = true;
	}
	public QueryPropertyValue(String property, double value) {
		this.property = property;
		this.valueD = value;
		doublevalue = true;
	}
	public String getProperty() {
		return property;
	}
	
	public boolean isStringvalue() {
		return stringvalue;
	}
	public String getValueS() {
		return valueS;
	}
	public boolean isIntvalue() {
		return intvalue;
	}
	public int getValueI() {
		return valueI;
	}
	public boolean isDoublevalue() {
		return doublevalue;
	}
	public double getValueD() {
		return valueD;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("(");
		build.append(property);
		build.append(", ");
		if(isStringvalue()) {
			build.append("String: " + valueS);
		} else if(isDoublevalue()) {
			build.append("double: " + valueD);
		} else if(isIntvalue()) {
			build.append("int: " + valueI);
		}
		build.append(")");
		return build.toString();
	}

}
