package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationMatrixValues extends ChemConnectCompoundDataStructure {
	@Index
	String observationRowValue;
	
	public ObservationMatrixValues() {
		super();
		this.observationRowValue = null;
	}
	public ObservationMatrixValues(ChemConnectCompoundDataStructure structure, 
			String observationRowValue) {
		super(structure);
		this.observationRowValue = observationRowValue;
	}

	public String getObservationRowValue() {
		return observationRowValue;
	}

	public void setObservationRowValue(String observationRowValue) {
		this.observationRowValue = observationRowValue;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + " Row Values: ");
		build.append(observationRowValue);
		build.append("\n");
		return build.toString();
	}	

}
