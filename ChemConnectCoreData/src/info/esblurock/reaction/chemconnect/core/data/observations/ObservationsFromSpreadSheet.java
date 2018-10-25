package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ObservationsFromSpreadSheet extends ObservationsFromSpreadSheetFull {
	
	@Index
	String observationValueRowTitle;
	
	public ObservationsFromSpreadSheet() {
		super();
		this.observationValueRowTitle = "";
	}
	public ObservationsFromSpreadSheet(ObservationsFromSpreadSheetFull full, 
			String observationValueRowTitle) {
		super(full);
		this.observationValueRowTitle = observationValueRowTitle;
	}
	
	public String getObservationValueRowTitle() {
		return observationValueRowTitle;
	}
	public void setObservationValueRowTitle(String observationValueRowTitle) {
		this.observationValueRowTitle = observationValueRowTitle;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		build.append(super.toString(prefix));
		build.append(prefix + "ObservationValueRowTitle:      " + observationValueRowTitle + "\n");
		return build.toString();
	}

}
