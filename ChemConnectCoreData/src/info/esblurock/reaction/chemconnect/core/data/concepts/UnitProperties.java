package info.esblurock.reaction.chemconnect.core.data.concepts;

import java.io.Serializable;


public class UnitProperties implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String unitName;
	String symbol;
	String abbreviation;
	String conversionOffset;
	String conversionMultiplier;
	String code;
	
	boolean classificationType;

	public UnitProperties() {
		init();
	}
	public UnitProperties(String unitName) {
		this.unitName = unitName;
		init();
	}
	void init() {
		classificationType = false;
		code = "0";
		conversionMultiplier = "1";
		conversionOffset = "0";
	}
	public void fillAsValue(String conversionOffset, String conversionMultiplier,
			String symbol, String abbreviation, String code) {
		this.conversionOffset = conversionOffset; 
		this.conversionMultiplier = conversionMultiplier;
		this.symbol = symbol;
		this.abbreviation = abbreviation;
		this.code = code;
	}
	public void fillAsClassification(String classification) {
		unitName = classification;
		classificationType = true;
		symbol = "key";
		abbreviation = "key";
	}
	
	public boolean isClassificationType() {
		return classificationType;
	}
	public void setClassificationType(boolean classificationType) {
		this.classificationType = classificationType;
	}
	public String getUnitName() {
		return unitName;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public String getConversionOffset() {
		return conversionOffset;
	}
	public String getConversionMultiplier() {
		return conversionMultiplier;
	}
	public String getCode() {
		return code;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "Unit: '" + unitName + "' (" + abbreviation + ": " + symbol + ", " + code  + ")\n");
		String newprefix = prefix + "\t";
		build.append(newprefix + "SI = " + conversionOffset + "+ v*(" + conversionMultiplier + ")\n");
		return build.toString();
	}
	

}
