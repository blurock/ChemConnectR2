package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ObservationSpecification extends ChemConnectCompoundDataStructure {
	@Index
	String observationLabel;
	@Index
	String observationParameterType;
	@Index
	String measureSpecifications;
	@Index
	String dimensionSpecifications;
	
	public ObservationSpecification() {
		this.observationParameterType = "";
		this.measureSpecifications = "";
		this.dimensionSpecifications = "";
		this.observationLabel = "";
	}
		public ObservationSpecification(ChemConnectCompoundDataStructure structure, 
			String observationLabel, String observationParameterType, 
			String dimensionSpecifications, String measureSpecifications) {
		super(structure);
		this.observationParameterType = observationParameterType;
		this.measureSpecifications = measureSpecifications;
		this.dimensionSpecifications = dimensionSpecifications;
		this.observationLabel = observationLabel;
	}

	public String getObservationParameterType() {
		return observationParameterType;
	}

	public void setObservationParameterType(String observationParameterType) {
		this.observationParameterType = observationParameterType;
	}

	
	public String getMeasureSpecifications() {
		return measureSpecifications;
	}
	public void setMeasureSpecifications(String measureSpecifications) {
		this.measureSpecifications = measureSpecifications;
	}
	public String getDimensionSpecifications() {
		return dimensionSpecifications;
	}
	public void setDimensionSpecifications(String dimensionSpecifications) {
		this.dimensionSpecifications = dimensionSpecifications;
	}
	public String toString() {
		return toString("");
	}
	
	public String getObservationLabel() {
		return observationLabel;
	}
	public void setObservationLabel(String observationLabel) {
		this.observationLabel = observationLabel;
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Parameter Label: " + observationLabel + "\n");
		build.append(prefix + "Type:            " + observationParameterType + "\n");
		build.append(prefix + "Measure Spec:    " + measureSpecifications + "\n");
		build.append(prefix + "Dimension Spec:  " + dimensionSpecifications + "\n");
		return build.toString();
	}
	
}
