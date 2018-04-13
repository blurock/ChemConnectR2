package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ParameterDescriptionSet extends ChemConnectCompoundDataStructure {

	@Unindex
	HashSet<String> parameterValues;

	public ParameterDescriptionSet() {
	}
	
	public ParameterDescriptionSet(ChemConnectCompoundDataStructure compound,
			HashSet<String> parameterValues) {
		super(compound);
		this.parameterValues = parameterValues;
	}
	
	public ParameterDescriptionSet(ChemConnectCompoundDataStructure compound) {
		super(compound);
		this.parameterValues = new HashSet<String>();
	}
	
	public void fill(ChemConnectCompoundDataStructure compound,
			HashSet<String> parameterValues) {
		super.fill(compound);
		this.parameterValues = parameterValues;
	}

	public HashSet<String> getParameterValues() {
		return parameterValues;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append("Parameters: " + parameterValues + "\n");
		return build.toString();
	}
	

}
