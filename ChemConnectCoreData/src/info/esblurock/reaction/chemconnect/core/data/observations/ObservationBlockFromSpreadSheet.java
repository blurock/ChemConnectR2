package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationBlockFromSpreadSheet extends ChemConnectDataStructure {
	@Index
	String observationValueRowTitle;
	@Index
	String spreadBlockIsolation;
	
	public ObservationBlockFromSpreadSheet() {
		this.observationValueRowTitle = null;
		this.spreadBlockIsolation = null;		
	}
	
	public ObservationBlockFromSpreadSheet(ChemConnectDataStructure structure,
			String observationValueRowTitle, String spreadBlockIsolation) {
		super(structure);
		this.observationValueRowTitle = observationValueRowTitle;
		this.spreadBlockIsolation = spreadBlockIsolation;
	}

	public String getObservationValueRowTitle() {
		return observationValueRowTitle;
	}

	public void setObservationValueRowTitle(String observationValueRowTitle) {
		this.observationValueRowTitle = observationValueRowTitle;
	}

	public String getSpreadBlockIsolation() {
		return spreadBlockIsolation;
	}

	public void setSpreadBlockIsolation(String spreadBlockIsolation) {
		this.spreadBlockIsolation = spreadBlockIsolation;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		build.append(super.toString(prefix));
		build.append(prefix + "Titles      :      " + observationValueRowTitle + "\n");
		build.append(prefix + "Matrix Block: " + spreadBlockIsolation + "\n");
		return build.toString();
	}
	

}
