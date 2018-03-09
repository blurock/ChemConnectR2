package info.esblurock.reaction.io.spreadsheet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object.InterpretInputDataObject;

@SuppressWarnings("serial")
@Entity
public class ConvertInputDataBase extends DatabaseObject {
	@Index
	String inputType;
	@Index
	String outputType;
	
	public ConvertInputDataBase() {
		super();
		this.inputType = null;
		this.outputType = null;
	}
	public ConvertInputDataBase(DatabaseObject obj) {
		super(obj);
		this.inputType = null;
		this.outputType = null;
	}
	public ConvertInputDataBase(DatabaseObject obj, String inputType, String outputType) {
		super(obj);
		this.inputType = inputType;
		this.outputType = outputType;
	}
	public InterpretInputDataObject process(DatabaseObject obj, InterpretInputDataObject input) {
		return null;
	}
	public boolean applicable(InterpretInputDataObject input) {
		return false;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getOutputType() {
		return outputType;
	}
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

}
