package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.methodology.ProtocolObservationSource;

@SuppressWarnings("serial")
@Entity
public class ObservationCorrespondenceSpecification extends ProtocolObservationSource {
	@Index
	String observationSpecification;
	@Index
	String matrixSpecificationCorrespondenceSet;

	public ObservationCorrespondenceSpecification() {
		init();
	}
	
	public ObservationCorrespondenceSpecification(ProtocolObservationSource structure) {
		super(structure);
		init();
	}
	
	public ObservationCorrespondenceSpecification(ProtocolObservationSource structure, 
			String observationSpecification, 
			String matrixSpecificationCorrespondenceSet) {
		super(structure);
		init();
		this.observationSpecification = observationSpecification;
		this.matrixSpecificationCorrespondenceSet = matrixSpecificationCorrespondenceSet;
	}
	
	void init() {
		matrixSpecificationCorrespondenceSet = "";
		observationSpecification = "";
	}
	
	public String getMatrixSpecificationCorrespondenceSet() {
		return matrixSpecificationCorrespondenceSet;
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
		build.append(prefix + "SpecificationCorrSet:       " + matrixSpecificationCorrespondenceSet + "\n");
		return build.toString();
	}
	
}
