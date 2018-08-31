package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class MatrixBlockDefinition  extends ChemConnectCompoundDataStructure {
	
	@Index
	String startRowInMatrix;
	@Index
	String startColumnInMatrix;
	@Index
	String lastRowInMatrix;
	@Index
	String lastColumnInMatrix;
	
	public MatrixBlockDefinition() {
		this.startRowInMatrix = "0";
		this.startColumnInMatrix = "0";
		this.lastRowInMatrix = "0";
		this.lastColumnInMatrix = "0";
	}
	
	public MatrixBlockDefinition(ChemConnectCompoundDataStructure structure,
			String startRowInMatrix, String startColumnInMatrix, String lastRowInMatrix, String lastColumnInMatrix) {
		super(structure);
		this.startRowInMatrix = startRowInMatrix;
		this.startColumnInMatrix = startColumnInMatrix;
		this.lastRowInMatrix = lastRowInMatrix;
		this.lastColumnInMatrix = lastColumnInMatrix;
	}

	public String getStartRowInMatrix() {
		return startRowInMatrix;
	}

	public void setStartRowInMatrix(String startRowInMatrix) {
		this.startRowInMatrix = startRowInMatrix;
	}

	public String getStartColumnInMatrix() {
		return startColumnInMatrix;
	}

	public void setStartColumnInMatrix(String startColumnInMatrix) {
		this.startColumnInMatrix = startColumnInMatrix;
	}

	public String getLastRowInMatrix() {
		return lastRowInMatrix;
	}

	public void setLastRowInMatrix(String lastRowInMatrix) {
		this.lastRowInMatrix = lastRowInMatrix;
	}

	public String getLastColumnInMatrix() {
		return lastColumnInMatrix;
	}

	public void setLastColumnInMatrix(String lastColumnInMatrix) {
		this.lastColumnInMatrix = lastColumnInMatrix;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append(" Block[(");
		build.append(startRowInMatrix);
		build.append(", ");
		build.append(lastRowInMatrix);
		build.append("), (");
		build.append(startColumnInMatrix);
		build.append(", ");
		build.append(lastColumnInMatrix);
		build.append(")]\n");
		return build.toString();
	}

}
