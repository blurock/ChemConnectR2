package info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class InterpretInputDataMatrix extends InterpretInputDataObject {
	@Unindex
	ArrayList< ArrayList<String> > matrix;

	public InterpretInputDataMatrix() {
		super();
		matrix = null;
	}
	public InterpretInputDataMatrix(DatabaseObject obj) {
		super();
		matrix = null;
	}
	public InterpretInputDataMatrix(DatabaseObject obj, ArrayList< ArrayList<String> > matrix) {
		super(obj);
		this.matrix = matrix;
	}

	public ArrayList<ArrayList<String>> getMatrix() {
		return matrix;
	}

	public void setMatrix(ArrayList<ArrayList<String>> matrix) {
		this.matrix = matrix;
	}

}
