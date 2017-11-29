package info.esblurock.reaction.chemconnect.core.data.dataset;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DataSpecification extends DatabaseObject {
	@Index
	String deviceSensor;
	@Index
	String instrument;
	@Index
	String chemConnectActivity;
	@Index
	String dataPointConcept;
	
	public DataSpecification(DatabaseObject obj,
			String deviceSensor, String instrument, 
			String chemConnectActivity, String dataPointConcept) {
		super(obj);
		this.deviceSensor = deviceSensor;
		this.instrument = instrument;
		this.chemConnectActivity = chemConnectActivity;
		this.dataPointConcept = dataPointConcept;
	}
	
	public String getDeviceSensor() {
		return deviceSensor;
	}
	public String getInstrument() {
		return instrument;
	}
	public String getChemConnectActivity() {
		return chemConnectActivity;
	}
	public String getDataPointConcept() {
		return dataPointConcept;
	}	
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Sensor: ");
		builder.append(deviceSensor);
		builder.append(", Instrument: ");
		builder.append(instrument);
		builder.append("\n" + prefix + "Activity: ");
		builder.append(chemConnectActivity);
		builder.append(", Concept: ");
		builder.append(dataPointConcept);
		builder.append("\n");
		return builder.toString();
	}	
	
	
}
