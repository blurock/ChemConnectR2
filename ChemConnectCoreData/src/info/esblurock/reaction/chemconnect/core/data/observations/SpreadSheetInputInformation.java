package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
public class SpreadSheetInputInformation  extends ChemConnectCompoundDataStructure  {

	private static final long serialVersionUID = 1L;
	
	public static String CSV = "dataset:CSV";
	public static String XLS = "dataset:XLS";
	public static String SpaceDelimited    = "dataset:SpaceDelimited";
	public static String TabDelimited      = "dataset:TabDelimited";
	public static String Delimited         = "dataset:Delimited";
	public static String SpaceTabDelimited = "dataset:SpaceTabDelimited";
	
	public static String URL          = "dataset:URLSourceFile";
	public static String CHEMCONNECT  = "dataset:ChemConnectDataObject";
	public static String STRINGSOURCE = "dataset:StringSource";
	public static String BLOBSOURCE   = "dataset:BlobSourceFile";
	
	public static String[] choices = {CSV,XLS,SpaceDelimited,TabDelimited,Delimited};
	public static String[] sourcechoices = {URL,STRINGSOURCE,BLOBSOURCE};
	
	
	@Index
	String type;
	@Index
	String delimitor;
	@Index
	String source;
	@Index
	String sourceType;
	
	public SpreadSheetInputInformation() {
		this.type = CSV;
		this.delimitor = ",";
		this.source = "a,b,c";
	}
	
	public SpreadSheetInputInformation(ChemConnectCompoundDataStructure obj, String type, String sourceType, 
			String source) {
		super(obj);
		this.type = type;
		this.sourceType = sourceType;
		setDelimitorType(type);
		this.source = source;
	}
	public SpreadSheetInputInformation(SpreadSheetInputInformation input) {
		super(input);
		this.type = input.getType();
		this.sourceType = input.getSourceType();
		this.delimitor = input.getDelimitor();
		this.source = input.getSource();
	}

	public void setDelimitorType(String type) {
		if(type.compareTo(CSV) == 0) {
			delimitor = ",";
		} else if(type.compareTo(XLS) == 0) {
			delimitor = " ";
		} else if(type.compareTo(SpaceDelimited) == 0) {
			delimitor = " ";
		} else if(type.compareTo(TabDelimited) == 0) {
			delimitor = "\t";
		} else if(type.compareTo(Delimited) == 0) {
			delimitor = " ";
		} else if(type.compareTo(SpaceTabDelimited) == 0) {
			delimitor = " \t";
		}		
	}
	public String getType() {
		return type;
	}
	public void fill(DatabaseObject object) {
		super.fill(object);
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) object;
		input.localFill(input);
	}
	public void localFill(SpreadSheetInputInformation input) {
		this.type = input.getType();
		this.sourceType = input.getSourceType();
		this.delimitor = input.getDelimitor();
		this.source = input.getSource();
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
	
	public void setType(String type) {
		this.type = type;
	}


	public void setDelimitor(String delimitor) {
		this.delimitor = delimitor;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		if(type != null) {
			build.append(type);
		} else {
			build.append("unknown");
		}
		build.append(": " + sourceType + "  Delimitor(" + delimitor + ") ");
		if(isSourceType(STRINGSOURCE)) {
			build.append("Source as string");
			if(source != null) {
				build.append(":  " + source.length() + " chars");
			}
		} else {
			build.append(source);
		}
		return build.toString();
	}
}
