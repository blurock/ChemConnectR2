package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class MatrixSpecificationCorrespondence extends ChemConnectCompoundDataStructure {
	
	@Index
	int columnNumber;
	@Index
	String matrixColumn;
	@Index
	String specificationLabel;
	@Index
	boolean includesUncertaintyParameter;
	
	public MatrixSpecificationCorrespondence() {
		this.matrixColumn = "0";
		this.specificationLabel = "label";
		this.includesUncertaintyParameter = false;
	}
	public MatrixSpecificationCorrespondence(ChemConnectCompoundDataStructure structure,
			String matrixColumn, String specificationLabel, boolean includesUncertaintyParameter) {
		super(structure);
		this.matrixColumn = matrixColumn;
		this.specificationLabel = specificationLabel;
		this.includesUncertaintyParameter = includesUncertaintyParameter;
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
	public boolean isIncludesUncertaintyParameter() {
		return includesUncertaintyParameter;
	}
	public void setIncludesUncertaintyParameter(boolean includesUncertaintyParameter) {
		this.includesUncertaintyParameter = includesUncertaintyParameter;
	}
	
	
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append("(");
		build.append(columnNumber + ": ");
		build.append(matrixColumn);
		build.append(", ");
		build.append(specificationLabel);
		if(includesUncertaintyParameter) {
			build.append(", with uncertainty");
		}
		build.append(")\n");
		return build.toString();
	}
	

}
