package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class RecordInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	DatabaseObject object;
	String chemconnectStructure;
	String identifier;
	ElementsOfASetOfMainStructure subelements;
	
	public RecordInformation() {
	}
	
	public RecordInformation(DatabaseObject object, String chemconnectStructure, String identifier,
			ElementsOfASetOfMainStructure subelements) {
		super();
		this.object = object;
		this.chemconnectStructure = chemconnectStructure;
		this.identifier = identifier;
		this.subelements = subelements;
	}

	public DatabaseObject getObject() {
		return object;
	}

	public String getChemconnectStructure() {
		return chemconnectStructure;
	}

	public String getIdentifier() {
		return identifier;
	}

	public ElementsOfASetOfMainStructure getSubelements() {
		return subelements;
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("RecordInformation\n");
		build.append("Object-->" + object.getClass().getCanonicalName() + "\n");
		build.append(object.toString("Object-->"));
		build.append("\n");
		build.append("RecordInformation Elements\n");
		build.append("Elements-->" + chemconnectStructure);
		build.append("(");
		build.append(identifier);
		build.append(")\n");
		build.append(subelements.toString("Elements--->"));
		return build.toString();
	}
	
}
