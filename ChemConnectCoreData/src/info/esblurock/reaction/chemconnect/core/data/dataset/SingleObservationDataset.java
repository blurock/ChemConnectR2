package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class SingleObservationDataset extends ChemConnectDataStructure {

	@Index
	String parameterValueComponents;
	@Index
	String observationValueRows;

	public SingleObservationDataset() {
		init();
	}
	
	public SingleObservationDataset(ChemConnectDataStructure structure) {
		super(structure);
		init();
	}
	
	public SingleObservationDataset(ChemConnectDataStructure structure, 
			String parameterValueComponents, String observationValueRows) {
		super(structure);
		init();
		this.parameterValueComponents = parameterValueComponents;
		this.observationValueRows = observationValueRows;
	}
	
	public void init() {
		this.parameterValueComponents = null;
		this.observationValueRows = null;
	}
	

	public String getParameterValueComponents() {
		return parameterValueComponents;
	}

	public void setParameterValueComponents(String parameterValueComponents) {
		this.parameterValueComponents = parameterValueComponents;
	}

	public String getObservationValueRows() {
		return observationValueRows;
	}

	public void setObservationValueRows(String observationValueRows) {
		this.observationValueRows = observationValueRows;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Components: " + parameterValueComponents + "\n");
		build.append(prefix + "ValueRows : " + observationValueRows + "\n");
		return build.toString();
	}

}
