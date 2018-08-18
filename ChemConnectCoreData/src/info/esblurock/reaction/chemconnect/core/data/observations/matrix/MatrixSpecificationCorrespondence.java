package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class MatrixSpecificationCorrespondence extends ChemConnectCompoundDataStructure {
	
	@Index
	String matrixColumn;
	@Index
	String specificationLabel;
	
	public MatrixSpecificationCorrespondence() {
		this.matrixColumn = "";
		this.specificationLabel = "";		
	}
	public MatrixSpecificationCorrespondence(ChemConnectCompoundDataStructure structure,
			String matrixColumn, String specificationLabel) {
		super(structure);
		this.matrixColumn = matrixColumn;
		this.specificationLabel = specificationLabel;
	}
	public String getMatrixColumn() {
		return matrixColumn;
	}
	public void setMatrixColumn(String matrixColumn) {
		this.matrixColumn = matrixColumn;
	}
	public String getSpecificationLabel() {
		return specificationLabel;
	}
	public void setSpecificationLabel(String specificationLabel) {
		this.specificationLabel = specificationLabel;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append("(");
		build.append(matrixColumn);
		build.append(", ");
		build.append(specificationLabel);
		build.append(")");
		return build.toString();
	}
	

}
