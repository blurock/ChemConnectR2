package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.ArrayList;

public class CompoundDataStructureInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	String propertyType;
	String chemconnectcompound;
	
	ArrayList<CompoundDataStructureInformation> compoundelements;
	ArrayList<PrimitiveDataStructureInformation> primitiveelements;
	
	public CompoundDataStructureInformation() {
		init();
	}
	
	public CompoundDataStructureInformation(String chemconnectcompound, String propertyType) {
		this.propertyType = propertyType;
		this.chemconnectcompound = chemconnectcompound;
		init();
	}
	public void init() {
		compoundelements = new ArrayList<CompoundDataStructureInformation>();
		primitiveelements = new ArrayList<PrimitiveDataStructureInformation>();
	}
	public void addPrimitive(PrimitiveDataStructureInformation primitive) {
		primitiveelements.add(primitive);
	}
	public void addCompound(CompoundDataStructureInformation compound) {
		compoundelements.add(compound);
	}

	public String getPropertyType() {
		return propertyType;
	}

	public String getChemconnectcompound() {
		return chemconnectcompound;
	}

	public ArrayList<CompoundDataStructureInformation> getCompoundelements() {
		return compoundelements;
	}

	public ArrayList<PrimitiveDataStructureInformation> getPrimitiveelements() {
		return primitiveelements;
	}
	public String toString() {
		return toString("");
	}
		public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + propertyType);
		build.append(" (");
		build.append(chemconnectcompound);
		build.append(")\n");
		build.append(prefix + "Primitive\n");
		String newprefix = prefix + "\t  ";
		for(PrimitiveDataStructureInformation info : primitiveelements) {
			build.append(info.toString(newprefix));
			build.append("\n");
		}
		build.append(prefix + "Compound Structures\n");
		for(CompoundDataStructureInformation info : compoundelements) {
			build.append(info.toString(newprefix));
			build.append("\n");
		}
		
		return build.toString();
	}
}
