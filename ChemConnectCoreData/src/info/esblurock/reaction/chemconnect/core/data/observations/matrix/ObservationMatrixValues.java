package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationMatrixValues extends ChemConnectCompoundDataStructure {
	@Index
	String observationRowValue;
	@Index
	String numberOfColumns;
	
	public ObservationMatrixValues() {
		super();
		this.observationRowValue = null;
		this.numberOfColumns = "0";
	}
	public ObservationMatrixValues(ChemConnectCompoundDataStructure structure, 
			String observationRowValue,String numberOfColumns) {
		super(structure);
		this.observationRowValue = observationRowValue;
		this.numberOfColumns = numberOfColumns;
	}

	public String getObservationRowValue() {
		return observationRowValue;
	}

	public void setObservationRowValue(String observationRowValue) {
		this.observationRowValue = observationRowValue;
	}
	
	public String getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(String numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Row Values:      " + numberOfColumns + "\n");
		build.append(prefix + "Observation rows: " + observationRowValue);
		build.append("\n");
		return build.toString();
	}	

}
