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
	String spreadSheetInterpretation;
	
	public ObservationBlockFromSpreadSheet() {
		this.observationValueRowTitle = null;
		this.spreadSheetInterpretation = null;		
	}
	
	public ObservationBlockFromSpreadSheet(ChemConnectDataStructure structure,
			String observationValueRowTitle, String spreadSheetInterpretation) {
		super();
		this.observationValueRowTitle = observationValueRowTitle;
		this.spreadSheetInterpretation = spreadSheetInterpretation;
	}

	public String getObservationValueRowTitle() {
		return observationValueRowTitle;
	}

	public void setObservationValueRowTitle(String observationValueRowTitle) {
		this.observationValueRowTitle = observationValueRowTitle;
	}

	public String getSpreadSheetInterpretation() {
		return spreadSheetInterpretation;
	}

	public void setSpreadSheetInterpretation(String spreadSheetInterpretation) {
		this.spreadSheetInterpretation = spreadSheetInterpretation;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		build.append(super.toString(prefix));
		build.append(prefix + "Titles        :      " + observationValueRowTitle + "\n");
		build.append(prefix + "Interpretation: " + spreadSheetInterpretation + "\n");
		return build.toString();
	}
	

}
