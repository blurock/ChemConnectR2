package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class ChemConnectMethodology extends ChemConnectDataStructure  {
	
	@Index
	String methodologyType;
	@Index
	String parameterValues;
	@Index
	String observationSpecs;

	public ChemConnectMethodology() {
		super();
		this.methodologyType = null;
		this.parameterValues = null;
		this.observationSpecs = null;
		}

	public ChemConnectMethodology(ChemConnectMethodology methodology) {
		super(methodology);
		this.parameterValues = methodology.getParameterValues();
		this.observationSpecs = methodology.getParameterValues();
	}
	
	public ChemConnectMethodology(ChemConnectDataStructure datastructure, String methodology,
			String observationSpecs, String parameters) {
		super(datastructure);
		this.methodologyType = methodology;
		this.parameterValues = parameters;
		this.observationSpecs = observationSpecs;
	}

	public String getMethodologyType() {
		return methodologyType;
	}

	public void setMethodologyType(String methodologyType) {
		this.methodologyType = methodologyType;
	}

	public String getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}
	
	public String getObservationSpecs() {
		return observationSpecs;
	}

	public void setObservationSpecs(String observationSpecs) {
		this.observationSpecs = observationSpecs;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append("Methodology:               " + methodologyType + "\n"); 
		build.append("Parameter Values:          " + parameterValues + "\n"); 
		build.append("Observation Specification: " + observationSpecs + "\n"); 
		return build.toString();
	}
}
