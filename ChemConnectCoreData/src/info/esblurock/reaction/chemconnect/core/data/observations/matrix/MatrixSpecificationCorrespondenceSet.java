package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class MatrixSpecificationCorrespondenceSet extends ChemConnectCompoundDataStructure {
	
	@Index
	String matrixBlockDefinition;
	@Index
	String matrixSpecificationCorrespondence;
	
	public MatrixSpecificationCorrespondenceSet(ChemConnectCompoundDataStructure structure, 
			String matrixBlockDefinition, String matrixSpecificationCorrespondence) {
		super(structure);
		this.matrixBlockDefinition = matrixBlockDefinition;
		this.matrixSpecificationCorrespondence = matrixSpecificationCorrespondence;
	}
	public String getMatrixBlockDefinition() {
		return matrixBlockDefinition;
	}
	public void setMatrixBlockDefinition(String matrixBlockDefinition) {
		this.matrixBlockDefinition = matrixBlockDefinition;
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
		build.append(" Block Definition: ");
		build.append(matrixBlockDefinition);
		build.append("\n");
		build.append(prefix + " SpecificationCorrespondence: ");
		build.append(matrixSpecificationCorrespondence);
		build.append("\n");
		return build.toString();
	}
}
