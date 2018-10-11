package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class SpreadSheetInterpretation extends ChemConnectCompoundDataStructure  {

	@Index
	String startRow;
	@Index
	String endRow;
	@Index
	String startColumn;
	@Index
	String endColumn;
	@Index
	String titleSearchKey;
	@Index
	boolean noBlanks;
	
	public SpreadSheetInterpretation() {
		super();
	}
	
	public SpreadSheetInterpretation(ChemConnectCompoundDataStructure obj, 
			String startRow, String endRow, String startColumn, String endColumn,
			String titleSearchKey, String noBlanks) {
		super(obj);
		this.startRow = startRow;
		this.endRow = endRow;
		this.startColumn = startColumn;
		this.endColumn = endColumn;
		this.titleSearchKey = titleSearchKey;
		this.noBlanks = Boolean.parseBoolean(noBlanks);
	}

	public String getStartRow() {
		return startRow;
	}

	public String getEndRow() {
		return endRow;
	}

	public String getStartColumn() {
		return startColumn;
	}

	public String getEndColumn() {
		return endColumn;
	}

	public String getTitleSearchKey() {
		return titleSearchKey;
	}

	public boolean isNoBlanks() {
		return noBlanks;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}

	public void setStartColumn(String startColumn) {
		this.startColumn = startColumn;
	}

	public void setEndColumn(String endColumn) {
		this.endColumn = endColumn;
	}

	public void setTitleSearchKey(String titleSearchKey) {
		this.titleSearchKey = titleSearchKey;
	}

	public void setNoBlanks(boolean noBlanks) {
		this.noBlanks = noBlanks;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix) + "\n");
		build.append(prefix + " Row(" + startRow + " - " + endRow + ")  ");
		build.append("Column(" + startColumn + " - " + endColumn + ")   ");
		if(noBlanks) {
			build.append("(No blank lines) ");
		} else {
			build.append("(No blank lines) ");
		}
		build.append("\n");
		return build.toString();
	}
	
	
}
