package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class CompoundDataStructureInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	DatabaseObject object;
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
		object = null;
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
	
	
	public DatabaseObject getObject() {
		return object;
	}

	public void setObject(DatabaseObject object) {
		this.object = object;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "CompoundDataStructureInformation:\n");
		if(object != null) {
		String opr = prefix + "Object: ";
		build.append(opr + object.getClass().getCanonicalName());
		build.append(object.toString(opr));
		build.append("\n");
		} else {
			build.append("no object\n");
		}
		build.append(prefix + propertyType);
		build.append(" (");
		build.append(chemconnectcompound);
		build.append(")\n");
		build.append(prefix + "Primitive\n");
		String newprefix = prefix + "\t  ";
		int count = 0;
		for(PrimitiveDataStructureInformation info : primitiveelements) {
			String pr = newprefix + count++ + ": ";
			build.append(info.toString(pr));
			build.append("\n");
		}
		build.append(prefix + "Compound Structures\n");
		count = 0;
		for(CompoundDataStructureInformation info : compoundelements) {
			String pr = newprefix + count++ + ": ";
			build.append(info.toString(pr));
			build.append("\n");
		}
		
		return build.toString();
	}
}
