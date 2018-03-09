package info.esblurock.reaction.chemconnect.core.data.transfer;

import com.googlecode.objectify.annotation.Entity;


/*
 * 
 * The observableType is the value from the PrimitiveDataStructureInformation
 */
@SuppressWarnings("serial")
@Entity
public class PrimitiveParameterSpecificationInformation extends PrimitiveParameterValueInformation {

	boolean dimension;
	
	public PrimitiveParameterSpecificationInformation() {
		super();
	}

	public PrimitiveParameterSpecificationInformation(PrimitiveParameterValueInformation info, boolean dimension) {
		super(info);
		this.dimension = dimension;
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
