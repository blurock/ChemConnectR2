package info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class InterpretInputDataString extends InterpretInputDataObject {
	@Unindex
	String stringObject;

	public InterpretInputDataString() {
		super();
		stringObject = null;
	}
	public InterpretInputDataString(DatabaseObject obj) {
		super(obj);
		stringObject = null;
	}
	public InterpretInputDataString(DatabaseObject obj, String stringObject) {
		super();
		this.stringObject = stringObject;
	}

	public String getStringObject() {
		return stringObject;
	}

	public void setStringObject(String stringObject) {
		this.stringObject = stringObject;
	}

}
