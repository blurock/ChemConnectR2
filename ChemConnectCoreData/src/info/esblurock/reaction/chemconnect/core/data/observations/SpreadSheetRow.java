package info.esblurock.reaction.chemconnect.core.data.observations;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class SpreadSheetRow extends DatabaseObject {

	@Index
	int rowNumber;
	@Index
	String parent;
	@Unindex
	ArrayList<String> row;

	public SpreadSheetRow() {
		row = new ArrayList<String>();
	}

	public SpreadSheetRow(DatabaseObject obj, int rowNumber, String parent, ArrayList<String> row) {
		super(obj);
		this.rowNumber = rowNumber;
		this.parent = parent;
		this.row = row;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public String getParent() {
		return parent;
	}

	public ArrayList<String> getRow() {
		return row;
	}

	public void add(String column) {
		row.add(column);
	}

	public int size() {
		return row.size();
	}

	public String get(int number) {
		return row.get(number);
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + rowNumber + ": ");
		if (row != null) {
			for (String cell : row) {
				build.append("'" + cell + "' \t");
			}
		} else {
			build.append("empty line");
		}
		build.append("\n");
		return build.toString();
	}

}
