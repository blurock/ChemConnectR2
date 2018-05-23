package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationSpecification extends ChemConnectCompoundDataStructure {
	@Index
	String observationParameterType;
	@Index
	String parameterSpecifications;
	
	public ObservationSpecification(ChemConnectCompoundDataStructure structure, 
			String observationParameterType, String parameterSpecifications) {
		super(structure);
		this.observationParameterType = observationParameterType;
		this.parameterSpecifications = parameterSpecifications;
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
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Type:          " + observationParameterType + "\n");
		build.append(prefix + "Specification: " + parameterSpecifications + "\n");
		return build.toString();
	}
	
}
