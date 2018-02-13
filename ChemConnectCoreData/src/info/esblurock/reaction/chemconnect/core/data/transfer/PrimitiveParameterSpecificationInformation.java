package info.esblurock.reaction.chemconnect.core.data.transfer;


/*
 * 
 * The observableType is the value from the PrimitiveDataStructureInformation
 */
@SuppressWarnings("serial")
public class PrimitiveParameterSpecificationInformation extends PrimitiveParameterValueInformation {

	boolean dimension;
	
	public PrimitiveParameterSpecificationInformation() {
		super();
	}

	public PrimitiveParameterSpecificationInformation(String identifier, String label, String observableType, 
			String unit, String unitclass, 
			String purpose, String concept, 
			String uncertaintyValue, String uncertaintyType) {
		super(identifier, label, observableType, unit, unitclass, purpose, concept, uncertaintyValue, uncertaintyType);
	}

	public boolean isDimension() {
		return dimension;
	}

	public void setDimension(boolean dimension) {
		this.dimension = dimension;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		String newprefix = prefix + ": (Measure) ";
		if(dimension) {
			newprefix = prefix + ": (Dimension) ";
		}
		builder.append(super.toString(newprefix));
		return builder.toString();
	}
}
