package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationMatrixValues extends ChemConnectCompoundDataStructure {
	@Index
	String observationRowValueTitles;
	@Index
	String observationRowValue;
	
	public ObservationMatrixValues() {
		super();
		this.observationRowValueTitles = null;
		this.observationRowValue = null;
	}
	public ObservationMatrixValues(ChemConnectCompoundDataStructure structure, 
			String observationRowValueTitles, String observationRowValue) {
		super(structure);
		this.observationRowValueTitles = observationRowValueTitles;
		this.observationRowValue = observationRowValue;
	}

	public String getObservationRowValueTitles() {
		return observationRowValueTitles;
	}

	public void setObservationRowValueTitles(String observationRowValueTitles) {
		this.observationRowValueTitles = observationRowValueTitles;
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
		build.append(prefix);
		build.append(" Column Titles: ");
		build.append(observationRowValueTitles);
		build.append("\n");
		build.append(prefix + " Row Values: ");
		build.append(observationRowValue);
		build.append("\n");
		return build.toString();
	}	

}
