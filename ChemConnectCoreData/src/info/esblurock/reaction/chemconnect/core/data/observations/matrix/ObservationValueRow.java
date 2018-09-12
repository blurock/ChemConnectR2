package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import java.util.ArrayList;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationValueRow extends ChemConnectCompoundDataStructure {

	@Index
	String rowNumber;
	@Unindex
	ArrayList<String> row;

	public ObservationValueRow() {
		row = new ArrayList<String>();
		rowNumber = "0";
	}

	public ObservationValueRow(ChemConnectCompoundDataStructure obj, String rowNumber, ArrayList<String> row) {
		super(obj);
		this.rowNumber = rowNumber;
		this.row = row;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setRow(ArrayList<String> row) {
		this.row = row;
	}

	public ArrayList<String> getRow() {
		return row;
	}
	
	public void addValue(String value) {
		row.add(value);
	}
	public String get(int count) {
		return row.get(count);
	}

	public void add(String column) {
		row.add(column);
	}

	public int size() {
		return row.size();
	}

	public 	ArrayList<String> get() {
		return row;
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
