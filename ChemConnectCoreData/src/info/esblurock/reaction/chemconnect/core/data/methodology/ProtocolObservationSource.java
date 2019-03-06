package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

@Entity
@SuppressWarnings("serial")
public class ProtocolObservationSource extends ChemConnectDataStructure   {

	@Index
	String numberOfObservations;

	public ProtocolObservationSource() {
		super();
		this.numberOfObservations = MetaDataKeywords.oneObservationSet;
	}

	public ProtocolObservationSource(ChemConnectDataStructure structure, String numberOfObservations) {
		super(structure);
		this.numberOfObservations = numberOfObservations;
	}
	public ProtocolObservationSource(ProtocolObservationSource source) {
		super(source);
		this.numberOfObservations = source.getNumberOfObservations();
	}
	
	
	public String getNumberOfObservations() {
		return numberOfObservations;
	}

	public void setNumberOfObservations(String numberOfObservations) {
		this.numberOfObservations = numberOfObservations;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "NumberOfObservationSets: " + numberOfObservations + "\n"); 
		return build.toString();
	}
	

	
	
}
