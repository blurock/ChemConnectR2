package info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class InterpretInputDataVector extends InterpretInputDataObject {
	@Unindex
	ArrayList<String> vector;

	public InterpretInputDataVector() {
		super();
		vector = null;
	}
	public InterpretInputDataVector(DatabaseObject obj) {
		super(obj);
		this.vector = null;
	}
	public InterpretInputDataVector(DatabaseObject obj, ArrayList<String> vector) {
		super(obj);
		this.vector = vector;
	}

	public ArrayList<String> getVector() {
		return vector;
	}

	public void setVector(ArrayList<String> vector) {
		this.vector = vector;
	}

}
