package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.ArrayList;

public class ElementsOfASetOfMainStructure implements Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<CompoundDataStructureInformation> elements;

	public ElementsOfASetOfMainStructure() {
		init();
	}
	
	public ElementsOfASetOfMainStructure(String idName, String identifier, String dataType) {
		init();
	}
	
	public void init() {
		elements = new ArrayList<CompoundDataStructureInformation>();
	}
	public void addCompoundStructure(CompoundDataStructureInformation compound) {
		elements.add(compound);
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("CompoundDataStructureInformation\n");
		for(CompoundDataStructureInformation compound : elements) {
			build.append(compound);
		}
		return build.toString();
	}

	public ArrayList<CompoundDataStructureInformation> getElements() {
		return elements;
	}
	
}
