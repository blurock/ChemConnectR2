package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationSpecification extends ChemConnectCompoundDataStructure {
	@Index
	String observationLabel;
	@Index
	String observationParameterType;
	@Index
	String parameterSpecifications;
	
	public ObservationSpecification() {
		this.observationParameterType = "";
		this.parameterSpecifications = "";
		this.observationLabel = observationLabel;
	}
		public ObservationSpecification(ChemConnectCompoundDataStructure structure, 
			String observationLabel, String observationParameterType, String parameterSpecifications) {
		super(structure);
		this.observationParameterType = observationParameterType;
		this.parameterSpecifications = parameterSpecifications;
		this.observationLabel = observationLabel;
	}

	public String getObservationParameterType() {
		return observationParameterType;
	}

	public void setObservationParameterType(String observationParameterType) {
		this.observationParameterType = observationParameterType;
	}

	public String getParameterSpecifications() {
		return parameterSpecifications;
	}

	public void setParameterSpecifications(String parameterSpecifications) {
		this.parameterSpecifications = parameterSpecifications;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String getObservationLabel() {
		return observationLabel;
	}
	public void setObservationLabel(String observationLabel) {
		this.observationLabel = observationLabel;
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parameter Label:" + observationLabel + "\n");
		build.append(prefix + "Type:           " + observationParameterType + "\n");
		build.append(prefix + "Specification:  " + parameterSpecifications + "\n");
		return build.toString();
	}
	
}
