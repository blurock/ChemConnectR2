package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;

public class ChemConnectDataStructureObject implements Serializable {
	private static final long serialVersionUID = 1L;

	DatabaseObjectHierarchy objecthierarchy;
	ChemConnectDataStructure chemconnect;
	public ChemConnectDataStructureObject() {
		this.objecthierarchy = null;
		this.chemconnect = null;
	}
	
	public ChemConnectDataStructureObject(ChemConnectDataStructure chemconnect,
			DatabaseObjectHierarchy objecthierarchy) {
		super();
		this.objecthierarchy = objecthierarchy;
		this.chemconnect = chemconnect;
	}

	public DatabaseObjectHierarchy getObjecthierarchy() {
		return objecthierarchy;
	}

	public void setObjecthierarchy(DatabaseObjectHierarchy objecthierarchy) {
		this.objecthierarchy = objecthierarchy;
	}

	public ChemConnectDataStructure getChemconnect() {
		return chemconnect;
	}

	public void setChemconnect(ChemConnectDataStructure chemconnect) {
		this.chemconnect = chemconnect;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(chemconnect != null) {
		builder.append(chemconnect.toString());
		}
		if(objecthierarchy != null) {
		builder.append(objecthierarchy.toString());
		}
		return builder.toString();
	}
}
