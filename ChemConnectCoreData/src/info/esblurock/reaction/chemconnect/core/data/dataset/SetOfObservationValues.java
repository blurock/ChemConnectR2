package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class SetOfObservationValues extends ChemConnectDataStructure {
	@Index
	String observationSpecification;
	@Index
	String observationMatrixValues;
	@Index
	String matrixSpecificationCorrespondenceSet;

	public SetOfObservationValues() {
		init();
	}
	
	public SetOfObservationValues(ChemConnectDataStructure structure) {
		super(structure);
		init();
	}
	
	public SetOfObservationValues(ChemConnectDataStructure structure, 
			String observationSpecification, String observationMatrixValues, String matrixSpecificationCorrespondenceSet) {
		super(structure);
		init();
		this.observationSpecification = observationSpecification;
		this.observationMatrixValues = observationMatrixValues;
		this.matrixSpecificationCorrespondenceSet = matrixSpecificationCorrespondenceSet;
	}
	
	void init() {
		observationMatrixValues = "";
		matrixSpecificationCorrespondenceSet = "";
		observationSpecification = "";
	}
	
	public String getObservationMatrixValues() {
		return observationMatrixValues;
	}

	public String getMatrixSpecificationCorrespondenceSet() {
		return matrixSpecificationCorrespondenceSet;
	}

	public void setObservationMatrixValues(String observationMatrixValues) {
		this.observationMatrixValues = observationMatrixValues;
	}

	public void setMatrixSpecificationCorrespondenceSet(String matrixSpecificationCorrespondenceSet) {
		this.matrixSpecificationCorrespondenceSet = matrixSpecificationCorrespondenceSet;
	}

	public String getObservationSpecification() {
		return observationSpecification;
	}

	public void setObservationSpecification(String observationSpecification) {
		this.observationSpecification = observationSpecification;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Specification:              " + observationSpecification + "\n");
		build.append(prefix + "MatrixValues:               " + observationMatrixValues + "\n");
		build.append(prefix + "SpecificationCorrSet:       " + matrixSpecificationCorrespondenceSet + "\n");
		return build.toString();
	}
	
}
