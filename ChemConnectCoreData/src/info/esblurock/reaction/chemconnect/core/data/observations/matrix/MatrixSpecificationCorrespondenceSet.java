package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class MatrixSpecificationCorrespondenceSet extends ChemConnectCompoundDataStructure {
	
	@Index
	String matrixSpecificationCorrespondence;
	
	public MatrixSpecificationCorrespondenceSet() {
		super();
		this.matrixSpecificationCorrespondence = null;
	}
	public MatrixSpecificationCorrespondenceSet(ChemConnectCompoundDataStructure structure, 
			String matrixSpecificationCorrespondence) {
		super(structure);
		this.matrixSpecificationCorrespondence = matrixSpecificationCorrespondence;
	}
	public String getMatrixSpecificationCorrespondence() {
		return matrixSpecificationCorrespondence;
	}
	public void setMatrixSpecificationCorrespondence(String matrixSpecificationCorrespondence) {
		this.matrixSpecificationCorrespondence = matrixSpecificationCorrespondence;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		build.append(prefix + " SpecificationCorrespondence: ");
		build.append(matrixSpecificationCorrespondence);
		build.append("\n");
		return build.toString();
	}
}
