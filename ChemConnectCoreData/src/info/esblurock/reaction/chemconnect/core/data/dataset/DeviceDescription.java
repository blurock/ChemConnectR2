package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class DeviceDescription extends ChemConnectDataStructure {
	@Index
	String dataSetSpecification;
	@Index
	String parameterDescriptionSet;
	
	public DeviceDescription() {
	}

	public DeviceDescription(ChemConnectDataStructure structure, String dataSetSpecification, String parameterDescriptionSet) {
		fill(structure,dataSetSpecification,parameterDescriptionSet);
	}
	
	public void fill(ChemConnectDataStructure structure, String dataSetSpecification, String parameterDescriptionSet) {
		super.fill(structure);
		this.dataSetSpecification = dataSetSpecification;
		this.parameterDescriptionSet = parameterDescriptionSet;
	}

	public String getDataSetSpecification() {
		return dataSetSpecification;
	}

	public String getParameterDescriptionSet() {
		return parameterDescriptionSet;
	}

	public String toString() {
		return toString("");
	};
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Specification: ");
		builder.append(dataSetSpecification);
		builder.append(", Parameter: ");
		builder.append(parameterDescriptionSet);
		builder.append("\n");
		return builder.toString();		
	}
}
