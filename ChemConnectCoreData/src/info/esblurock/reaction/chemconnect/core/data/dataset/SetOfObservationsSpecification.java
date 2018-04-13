package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class SetOfObservationsSpecification extends ChemConnectDataStructure {
	
	@Index
	ArrayList<String> observationSpecifications;
	
	public SetOfObservationsSpecification(ChemConnectDataStructure structure, ArrayList<String> observationSpecifications) {
		super(structure);
		this.observationSpecifications = observationSpecifications;
	}

	public ArrayList<String> getObservationSpecifications() {
		return observationSpecifications;
	}

	public void addObservationSpecification(String spec) {
		observationSpecifications.add(spec);
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		
		build.append("Observation Specifications:\n" + observationSpecifications + "\n");
		
		return build.toString();
	}
	
}
