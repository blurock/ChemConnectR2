package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class ChemConnectProtocol extends ChemConnectDataStructure  {
	
	@Index
	String parameterValues;

	public ChemConnectProtocol() {
		super();
		this.parameterValues = null;
		}

	public ChemConnectProtocol(ChemConnectProtocol methodology) {
		super(methodology);
		this.parameterValues = methodology.getParameterValues();
	}
	
	public ChemConnectProtocol(ChemConnectDataStructure datastructure, String parameters) {
		super(datastructure);
		this.parameterValues = parameters;
	}

	public String getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parameter Values:          " + parameterValues + "\n"); 
		return build.toString();
	}
}
