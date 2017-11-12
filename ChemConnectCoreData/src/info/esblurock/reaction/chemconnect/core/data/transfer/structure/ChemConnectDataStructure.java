package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.ArrayList;

public class ChemConnectDataStructure implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String record = "dcat:record";
	private static String linkedTo = "org:linkedTo";

	String elementType;
	ArrayList<ChemConnectDataStructureElement> records;
	ArrayList<ChemConnectDataStructureElement> linkedTos;
	ArrayList<ChemConnectDataStructureElement> other;
	MapToChemConnectCompoundDataStructure mapping;
	
	public ChemConnectDataStructure() {
		records = new ArrayList<ChemConnectDataStructureElement>();
		linkedTos = new ArrayList<ChemConnectDataStructureElement>();
		other = new ArrayList<ChemConnectDataStructureElement>();
		mapping = new MapToChemConnectCompoundDataStructure();
	}

	public ChemConnectDataStructure(String elementType) {
		this.elementType = elementType;
		records = new ArrayList<ChemConnectDataStructureElement>();
		linkedTos = new ArrayList<ChemConnectDataStructureElement>();
		other = new ArrayList<ChemConnectDataStructureElement>();
		mapping = new MapToChemConnectCompoundDataStructure();
	}

	public void addElement(ChemConnectDataStructureElement element, ChemConnectCompoundDataStructure structure) {
		if (element.getLinktype().compareTo(record) == 0) {
			records.add(element);
		} else if (element.getLinktype().compareTo(linkedTo) == 0) {
			linkedTos.add(element);
		} else {
			other.add(element);
		}
		if(structure != null) {
		mapping.addStructure(structure);
		}
	}
	public void addToMapping(ChemConnectCompoundDataStructure structure) {
		mapping.addStructure(structure);
	}
	
	public String getElementType() {
		return elementType;
	}

	public ArrayList<ChemConnectDataStructureElement> getRecords() {
		return records;
	}

	public ArrayList<ChemConnectDataStructureElement> getLinkedTos() {
		return linkedTos;
	}

	public ArrayList<ChemConnectDataStructureElement> getOther() {
		return other;
	}
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix + "------------ SetOfChemConnectDataStructureElements ------------\n");
		builder.append(prefix + "DataType: " + elementType + "\n");
		String newprefix = prefix + "\t";
		String newprefix2 = newprefix + "\t";
		builder.append(newprefix + "Records\n");
		for (ChemConnectDataStructureElement element : records) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append(newprefix + "LinkedTo\n");
		for (ChemConnectDataStructureElement element : linkedTos) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append(newprefix + "Other\n");
		for (ChemConnectDataStructureElement element : other) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append("\n");
		builder.append(mapping.toString(newprefix));
		return builder.toString();
	}
}
