package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class ObervationsFromUserInterface extends ProtocolObservationSource {

	public ObervationsFromUserInterface() {
		super();
	}

	public ObervationsFromUserInterface(ProtocolObservationSource source) {
		super(source);
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
