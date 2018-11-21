package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationDatasetFromProtocol extends ChemConnectDataStructure {

	public ObservationDatasetFromProtocol() {
		super();
	}

	public ObservationDatasetFromProtocol(ChemConnectDataStructure datastructure) {
		super(datastructure);
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		return build.toString();
	}
	

}
