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
		StringBuilder build = new StringBuilder();
		build.append(propertyType);
		build.append(" (");
		build.append(chemconnectcompound);
		build.append(")\n");
		build.append("Primitive\n");
		for(PrimitiveDataStructureInformation info : primitiveelements) {
			build.append(info.toString());
			build.append("\n");
		}
		build.append("Compound Structures\n");
		for(CompoundDataStructureInformation info : compoundelements) {
			build.append(info.toString());
			build.append("\n");
		}
		
		return build.toString();
	}
}
