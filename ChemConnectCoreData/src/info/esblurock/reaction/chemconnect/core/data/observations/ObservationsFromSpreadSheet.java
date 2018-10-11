package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationsFromSpreadSheet extends ChemConnectDataStructure {
	
	@Index
	String observationMatrixValues;
	@Index
	String spreadSheetInputInformation;
	

	public ObservationsFromSpreadSheet() {
		super();
		this.observationMatrixValues = "";
		this.spreadSheetInputInformation = "";
	}
	public ObservationsFromSpreadSheet(ChemConnectDataStructure source, 
			String observationMatrixValues,
			String spreadSheetInputInformation) {
		super(source);
		this.observationMatrixValues = observationMatrixValues;
		this.spreadSheetInputInformation = spreadSheetInputInformation;
	}
	
	public String getObservationMatrixValues() {
		return observationMatrixValues;
	}
	public String getSpreadSheetInputInformation() {
		return spreadSheetInputInformation;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		build.append(super.toString(prefix));
		build.append(prefix + "ObservationMatrixValues:      " + observationMatrixValues + "\n");
		build.append(prefix + "SpreadSheetInputInformation: " + spreadSheetInputInformation + "\n");
		return build.toString();
	}

}
