package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
@SuppressWarnings("serial")
public class ChemConnectProtocol extends ProtocolObservationSource  {
	
	@Index
	String parameterValues;
	@Index
	String observationSources;

	public ChemConnectProtocol() {
		super();
		this.parameterValues = null;
		this.numberOfObservations = null;
		}

	public ChemConnectProtocol(ChemConnectProtocol methodology) {
		super(methodology);
		this.parameterValues = methodology.getParameterValues();
		this.observationSources = methodology.getNumberOfObservations();
	}
	
	public ChemConnectProtocol(ProtocolObservationSource source, 
			String parameters, String observationSources) {
		super(source);
		this.parameterValues = parameters;
		this.observationSources = observationSources;
	}

	public String getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}
	
	public String getObservationSources() {
		return observationSources;
	}

	public void setObservationSources(String observationSources) {
		this.observationSources = observationSources;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parameter Values:     " + parameterValues + "\n"); 
		build.append(prefix + "Observation Sources:  " + observationSources + "\n"); 
		return build.toString();
	}
}
