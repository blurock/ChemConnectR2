package info.esblurock.reaction.chemconnect.core.data.dataset.device;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class DeviceSubsystemElement extends ChemConnectDataStructure {
	
	public DeviceSubsystemElement() {
	}

	public DeviceSubsystemElement(ChemConnectDataStructure structure) {
		fill(structure);
	}
	
	public void fill(ChemConnectDataStructure structure) {
		super.fill(structure);
	}

	public String toString() {
		return toString("");
	};
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("\n");
		return builder.toString();		
	}
}
