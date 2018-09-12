package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class SpreadSheetInterpretation extends ChemConnectCompoundDataStructure  {

	@Index
	int startRow;
	@Index
	int endRow;
	@Index
	int startColumn;
	@Index
	int endColumn;
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
		this.startRow = Integer.parseInt(startRow);
		this.endRow = Integer.parseInt(endRow);
		this.startColumn = Integer.parseInt(startColumn);
		this.endColumn = Integer.parseInt(endColumn);
		this.titleSearchKey = titleSearchKey;
		this.noBlanks = Boolean.parseBoolean(noBlanks);
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public String getTitleSearchKey() {
		return titleSearchKey;
	}

	public boolean isNoBlanks() {
		return noBlanks;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public void setEndColumn(int endColumn) {
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
