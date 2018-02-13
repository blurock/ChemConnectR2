package info.esblurock.reaction.chemconnect.core.data.transfer;


@SuppressWarnings("serial")
public class PrimitiveParameterValueInformation extends PrimitiveDataStructureInformation {

	String unit; 
	String unitclass; 
	String purpose;
	String concept;
	String uncertaintyValue;
	String uncertaintyType;
	
	public PrimitiveParameterValueInformation() {
		super("Label", "id", "value");
		unit = null; 
		unitclass = null; 
		purpose = null;
		concept = null;
		uncertaintyValue = "0";
		uncertaintyType = null;
	}

	public PrimitiveParameterValueInformation(String identifier, String label, String value, String unit, 
			String unitclass,
			String purpose, String concept, String uncertaintyValue, String uncertaintyType) {
		super(label, identifier, value);
		this.unit = unit; 
		this.unitclass = unitclass; 
		this.concept = concept;
		this.purpose = purpose;
		this.uncertaintyValue = uncertaintyValue;
		this.uncertaintyType = uncertaintyType;
	}

	public String getUnitclass() {
		return unitclass;
	}

	public void setUnitclass(String unitclass) {
		this.unitclass = unitclass;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public void setUncertaintyValue(String uncertaintyValue) {
		this.uncertaintyValue = uncertaintyValue;
	}

	public void setUncertaintyType(String uncertaintyType) {
		this.uncertaintyType = uncertaintyType;
	}


	public String getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getConcept() {
		return concept;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getUncertaintyValue() {
		return uncertaintyValue;
	}
	public String getUncertaintyType() {
		return uncertaintyType;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(" +/-");
		builder.append(uncertaintyValue);
		builder.append(" ");
		builder.append(unit);
		builder.append(" (");
		builder.append(unitclass);
		builder.append(")\n");
		builder.append(prefix+ "\t");
		builder.append(unit);
		builder.append("   Purpose: ");
		builder.append(purpose);
		builder.append(", Concept: ");
		builder.append(concept);
		builder.append(", Uncertainty: ");
		builder.append(uncertaintyType);
		builder.append("\n");
		return builder.toString();
	}

}
