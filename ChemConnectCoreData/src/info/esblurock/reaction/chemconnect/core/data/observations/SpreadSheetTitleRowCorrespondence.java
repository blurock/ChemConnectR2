package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class SpreadSheetTitleRowCorrespondence extends DatabaseObject {

	@Index
	String parent;
	@Index
	int columnNumber;
	@Index
	String originalColumnTitle;
	@Index
	String correspondingParameternName;
	
	
	public SpreadSheetTitleRowCorrespondence() {
		super();
	}
	
	public SpreadSheetTitleRowCorrespondence(DatabaseObject obj, String parent, int columnNumber, String originalColumnTitle,
			String correspondingParameternName) {
		super(obj);
		this.parent = parent;
		this.columnNumber = columnNumber;
		this.originalColumnTitle = originalColumnTitle;
		this.correspondingParameternName = correspondingParameternName;
	}
	
	public String getParent() {
		return parent;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public String getOriginalColumnTitle() {
		return originalColumnTitle;
	}
	public String getCorrespondingParameternName() {
		return correspondingParameternName;
	}
}
