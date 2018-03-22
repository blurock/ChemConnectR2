package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class SpreadSheetInterpretation extends DatabaseObject  {

	@Index
	String parent;
	@Index
	int beginRow;
	@Index
	int endRow;
	@Index
	int titleRow;
	@Index
	String titleSearchKey;
	@Index
	boolean noBlanks;
	
	public SpreadSheetInterpretation() {
		super();
	}
	
	public SpreadSheetInterpretation(DatabaseObject obj, String parent, 
			int beginRow, int endRow, int titleRow,
			String titleSearchKey, boolean noBlanks) {
		super(obj);
		this.parent = parent;
		this.beginRow = beginRow;
		this.endRow = endRow;
		this.titleRow = titleRow;
		this.titleSearchKey = titleSearchKey;
		this.noBlanks = noBlanks;
	}
	public String getParent() {
		return parent;
	}
	public int getBeginRow() {
		return beginRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public int getTitleRow() {
		return titleRow;
	}

	public String getTitleSearchKey() {
		return titleSearchKey;
	}

	public boolean isNoBlanks() {
		return noBlanks;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix) + "\n");
		build.append(prefix + beginRow + " - " + endRow + "  ");
		if(noBlanks) {
			build.append("(No blank lines) ");
		}
		if(titleRow >= 0) {
			if(titleSearchKey.length() > 0) {
				build.append("Titles: (" + titleRow + "): " + titleSearchKey);
			} else {
				build.append("Titles: (" + titleRow + ")");			
			}
		} else {
			build.append("no column titles");
		}
		build.append("\n");
		return build.toString();
	}
	
	
}
