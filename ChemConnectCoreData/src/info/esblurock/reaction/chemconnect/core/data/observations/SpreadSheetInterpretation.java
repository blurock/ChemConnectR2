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
	
	public SpreadSheetInterpretation() {
		super();
	}
	
	public SpreadSheetInterpretation(DatabaseObject obj, String parent, int beginRow, int endRow, int titleRow) {
		super(obj);
		this.parent = parent;
		this.beginRow = beginRow;
		this.endRow = endRow;
		this.titleRow = titleRow;
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
	
	
}
