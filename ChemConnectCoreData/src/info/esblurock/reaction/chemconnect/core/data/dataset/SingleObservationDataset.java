package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class SingleObservationDataset extends ChemConnectDataStructure {

	@Index
	String chemConnectRowUnit;
	@Index
	String observationValueRowTitle;
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
			String observationValueRowTitle, String chemConnectRowUnit, String observationValueRows) {
		super(structure);
		init();
		this.chemConnectRowUnit = chemConnectRowUnit;
		this.observationValueRowTitle = observationValueRowTitle;
		this.observationValueRows = observationValueRows;
		init();
	}
	
	public void init() {
		this.chemConnectRowUnit = null;
		this.observationValueRowTitle = null;
		this.observationValueRows = null;
	}
	

	public String getChemConnectRowUnit() {
		return chemConnectRowUnit;
	}

	public void setChemConnectRowUnit(String chemConnectRowUnit) {
		this.chemConnectRowUnit = chemConnectRowUnit;
	}

	public String getObservationValueRowTitle() {
		return observationValueRowTitle;
	}

	public void setObservationValueRowTitle(String observationValueRowTitle) {
		this.observationValueRowTitle = observationValueRowTitle;
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
		build.append(prefix + "Units    : " + chemConnectRowUnit + "\n");
		build.append(prefix + "Titles   : " + observationValueRowTitle + "\n");
		build.append(prefix + "ValueRows: " + observationValueRows + "\n");
		return build.toString();
	}

}
