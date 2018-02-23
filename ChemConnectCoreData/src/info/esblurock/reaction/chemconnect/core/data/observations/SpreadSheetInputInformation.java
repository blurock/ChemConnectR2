package info.esblurock.reaction.chemconnect.core.data.observations;

import java.io.Serializable;

public class SpreadSheetInputInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String CVS = "CVS";
	public static String XLS = "XLS";
	public static String SpaceDelimited = "Delimited";
	public static String TabDelimited = "Delimited";
	public static String Delimited = "Delimited";
	
	public static String URL = "URL";
	public static String STRINGSOURCE = "String";
	
	public static String[] choices = {CVS,XLS,SpaceDelimited,TabDelimited,Delimited};
	public static String[] sourcechoices = {URL,STRINGSOURCE};
	
	
	String type;
	String delimitor;
	String source;
	String sourceType;
	
	public SpreadSheetInputInformation() {
		this.type = CVS;
		this.delimitor = ",";
		this.source = "a,b,c";
	}
	

	public SpreadSheetInputInformation(String type, String sourceType, String source) {
		this.type = type;
		this.sourceType = sourceType;
		this.delimitor = ",";
		this.source = source;
	}
	public SpreadSheetInputInformation(String type, String sourceType, String source, String delimitor) {
		this.type = type;
		this.sourceType = sourceType;
		this.delimitor = delimitor;
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public String getDelimitor() {
		return delimitor;
	}

	public String getSource() {
		return source;
	}

	public String getSourceType() {
		return sourceType;
	}

	public boolean isType(String type) {
		boolean ans = this.type.compareTo(type) == 0;
		return ans;
	}
	public boolean isSourceType(String source) {
		boolean ans = this.sourceType.compareTo(source) == 0;
		return ans;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + type + ": " + sourceType + "(" + delimitor + ") ");
		if(isSourceType(URL)) {
			build.append(source);
		} else {
			build.append("Source as string");
		}
		return build.toString();
	}
}
