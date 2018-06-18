package info.esblurock.reaction.chemconnect.core.data.dataset.device;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class SubSystemDescription extends ChemConnectDataStructure {
	
	@Index
	String subSystemType;
	@Index
	String observationSpecs;
	@Index
	String parameterValues;
	@Index
	String subSystems;
	
	public SubSystemDescription() {
	}

	
	public SubSystemDescription(ChemConnectDataStructure structure, 
			String subSystemType,
			String observationSpecs, String parameterValues, String subSystems) {
		super(structure);
		this.fill(structure,subSystemType,observationSpecs,parameterValues,subSystems);
	}


	public void fill(ChemConnectDataStructure structure,
			String subSystemType,
			String observationSpecs, String parameterValues, String subSystems) {
		super.fill(structure);
		this.observationSpecs = observationSpecs;
		this.parameterValues = parameterValues;
		this.subSystems = subSystems;
		this.subSystemType = subSystemType;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		SubSystemDescription sys = (SubSystemDescription) object;
		this.observationSpecs = sys.getObservationSpecs();
		this.parameterValues = sys.getParameterValues();
		this.subSystems = sys.getSubSystems();
		this.subSystemType = sys.getSubSystemType();
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


	public String getSubSystemType() {
		return subSystemType;
	}


	public void setSubSystemType(String subSystemType) {
		this.subSystemType = subSystemType;
	}


	public String toString() {
		return toString("");
	};
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Device Type     : " + subSystemType + "\n");
		builder.append(prefix + "ObservationSpecs: " + observationSpecs + "\n");
		builder.append(prefix + "parameterValues : " + parameterValues + "\n");
		builder.append(prefix + "subSystems:       " + subSystems + "\n");
		return builder.toString();		
	}
}
