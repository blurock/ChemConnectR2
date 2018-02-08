package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;

public class ChemConnectElementStructure {

	MapToChemConnectCompoundDataStructure elementMapping;
	ChemConnectCompoundDataStructure structure;
	
	
	public ChemConnectElementStructure(ChemConnectCompoundDataStructure structure, 
			MapToChemConnectCompoundDataStructure mapping) {
		
		elementMapping = new MapToChemConnectCompoundDataStructure();
		this.structure = structure;
		
		for(DataElementInformation element : structure) {
			String type = element.getDataElementName();
			ChemConnectCompoundDataStructure elementstructure = mapping.getStructure(type);
			elementMapping.addStructure(elementstructure);
		}
		
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(structure.toString(prefix));
		build.append(elementMapping.toString(prefix));
		return build.toString();
	}
}
