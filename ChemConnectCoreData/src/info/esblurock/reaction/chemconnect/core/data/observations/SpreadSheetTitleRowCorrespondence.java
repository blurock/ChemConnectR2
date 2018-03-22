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
	@Index
	Boolean errorParameter;
	
	
	public SpreadSheetTitleRowCorrespondence() {
		super();
	}
	
	public SpreadSheetTitleRowCorrespondence(DatabaseObject obj, String parent, 
			int columnNumber, String originalColumnTitle,
			String correspondingParameternName, boolean errorParameter) {
		super(obj);
		String id = parent + "-corr-" + correspondingParameternName;
		this.setIdentifier(id);
		this.parent = parent;
		this.columnNumber = columnNumber;
		this.originalColumnTitle = originalColumnTitle;
		this.correspondingParameternName = correspondingParameternName;
		this.errorParameter = new Boolean(errorParameter);
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
	public boolean isErrorParameter() {
		return errorParameter.booleanValue();
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		if(errorParameter.booleanValue()) {
			build.append("(Error)     ");
		} else {
			build.append("(Parameter) ");
		}
		build.append("Parent: " + parent + "\n");
		build.append(prefix);
		build.append("# " + columnNumber);
		build.append("  Column: " + originalColumnTitle);
		build.append("  Parameter:  " + correspondingParameternName);
		build.append("\n");

		return build.toString();
	}
	
}
