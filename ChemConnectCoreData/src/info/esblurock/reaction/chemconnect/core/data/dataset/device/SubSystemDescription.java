package info.esblurock.reaction.chemconnect.core.data.dataset.device;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class SubSystemDescription extends ChemConnectDataStructure {
	
	@Index
	String observationSpecs;
	@Index
	String parameterValues;
	@Index
	String subSystems;
	
	public SubSystemDescription() {
	}

	
	public SubSystemDescription(ChemConnectDataStructure structure, String observationSpecs, String parameterValues, String subSystems) {
		super(structure);
		fill(structure,observationSpecs,parameterValues,subSystems);
	}


	public void fill(ChemConnectDataStructure structure,
			String observationSpecs, String parameterValues, String subSystems) {
		super.fill(structure);
		this.observationSpecs = observationSpecs;
		this.parameterValues = parameterValues;
		this.subSystems = subSystems;
	}

	
	
	public String getObservationSpecs() {
		return observationSpecs;
	}


	public void setObservationSpecs(String observationSpecs) {
		this.observationSpecs = observationSpecs;
	}


	public String getParameterValues() {
		return parameterValues;
	}


	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}


	public String getSubSystems() {
		return subSystems;
	}


	public void setSubSystems(String subSystems) {
		this.subSystems = subSystems;
	}


	public String toString() {
		return toString("");
	};
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "ObservationSpecs: " + observationSpecs + "\n");
		builder.append(prefix + "parameterValues:  " + parameterValues + "\n");
		builder.append(prefix + "subSystems:       " + subSystems + "\n");
		return builder.toString();		
	}
}
