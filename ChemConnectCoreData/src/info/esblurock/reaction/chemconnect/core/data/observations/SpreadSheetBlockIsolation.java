package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class SpreadSheetBlockIsolation extends ChemConnectCompoundDataStructure  {

	@Index
	String spreadSheetBlockIsolationType;
	
	String startRowType;
	String endRowType;
	String startColumnType;
	String endColumnType;
	String startRowInfo;
	String endRowInfo;
	String startColumnInfo;
	String endColumnInfo;
	String titleIncluded;
	
	public SpreadSheetBlockIsolation() {
		super();
	}
	
	public SpreadSheetBlockIsolation(ChemConnectCompoundDataStructure structure,
			String spreadSheetBlockIsolationType,
			String startRowType, String endRowType, String startColumnType, String endColumnType,
			String startRowInfo, String endRowInfo, String startColumnInfo, String endColumnInfo,
			String titleIncluded) {
		super(structure);
		this.spreadSheetBlockIsolationType = spreadSheetBlockIsolationType;
		this.startRowType = startRowType;
		this.endRowType = endRowType;
		this.startColumnType = startColumnType;
		this.endColumnType = endColumnType;
		this.startRowInfo = startRowInfo;
		this.endRowInfo = endRowInfo;
		this.startColumnInfo = startColumnInfo;
		this.endColumnInfo = endColumnInfo;
		this.titleIncluded = titleIncluded;
	}
	public SpreadSheetBlockIsolation(ChemConnectCompoundDataStructure structure,
			String spreadSheetBlockIsolationType,
			String startRowType, String endRowType, String startColumnType, String endColumnType,
			String titleIncluded) {
		super(structure);
		this.spreadSheetBlockIsolationType = spreadSheetBlockIsolationType;
		this.startRowType = startRowType;
		this.endRowType = endRowType;
		this.startColumnType = startColumnType;
		this.endColumnType = endColumnType;
		this.startRowInfo = null;
		this.endRowInfo = null;
		this.startColumnInfo = null;
		this.endColumnInfo = null;
		this.titleIncluded = titleIncluded;
	}


	public String getSpreadSheetBlockIsolationType() {
		return spreadSheetBlockIsolationType;
	}

	public void setSpreadSheetBlockIsolationType(String spreadSheetBlockIsolationType) {
		this.spreadSheetBlockIsolationType = spreadSheetBlockIsolationType;
	}

	public String getStartRowType() {
		return startRowType;
	}

	public void setStartRowType(String startRowType) {
		this.startRowType = startRowType;
	}

	public String getEndRowType() {
		return endRowType;
	}

	public void setEndRowType(String endRowType) {
		this.endRowType = endRowType;
	}

	public String getStartColumnType() {
		return startColumnType;
	}

	public void setStartColumnType(String startColumnType) {
		this.startColumnType = startColumnType;
	}

	public String getEndColumnType() {
		return endColumnType;
	}

	public void setEndColumnType(String endColumnType) {
		this.endColumnType = endColumnType;
	}

	public String getStartRowInfo() {
		return startRowInfo;
	}

	public void setStartRowInfo(String startRowInfo) {
		this.startRowInfo = startRowInfo;
	}

	public String getEndRowInfo() {
		return endRowInfo;
	}

	public void setEndRowInfo(String endRowInfo) {
		this.endRowInfo = endRowInfo;
	}

	public String getStartColumnInfo() {
		return startColumnInfo;
	}

	public void setStartColumnInfo(String startColumnInfo) {
		this.startColumnInfo = startColumnInfo;
	}

	public String getEndColumnInfo() {
		return endColumnInfo;
	}

	public void setEndColumnInfo(String endColumnInfo) {
		this.endColumnInfo = endColumnInfo;
	}

	public String getTitleIncluded() {
		return titleIncluded;
	}

	public void setTitleIncluded(String titleIncluded) {
		this.titleIncluded = titleIncluded;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix) + "\n");
		build.append(prefix + "    Row[");
		build.append(startRowType);
		build.append(" (");
		build.append(startRowInfo);
		build.append(")");
		
		build.append(" - ");
		build.append(endRowType);
		build.append(" (");
		build.append(endRowInfo);
		build.append(")]\n");

		build.append(prefix + " Column[");
		build.append(startColumnType);
		build.append(" (");
		build.append(startColumnInfo);
		build.append(")");
		
		build.append(" - ");
		build.append(endColumnType);
		build.append(" (");
		build.append(endColumnInfo);
		build.append(")]\n");
		
		build.append(prefix + " Title: " +  titleIncluded);
		
		build.append("\n");
		return build.toString();
	}
	
	
}
