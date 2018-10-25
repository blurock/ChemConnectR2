package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationBlockFromSpreadSheet extends ChemConnectDataStructure {
	@Index
	String spreadBlockIsolation;
	
	public ObservationBlockFromSpreadSheet() {
		this.spreadBlockIsolation = null;		
	}
	
	public ObservationBlockFromSpreadSheet(ChemConnectDataStructure structure,
			String spreadBlockIsolation) {
		super(structure);
		this.spreadBlockIsolation = spreadBlockIsolation;
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
		build.append(prefix + "Matrix Block: " + spreadBlockIsolation + "\n");
		return build.toString();
	}
	

}
