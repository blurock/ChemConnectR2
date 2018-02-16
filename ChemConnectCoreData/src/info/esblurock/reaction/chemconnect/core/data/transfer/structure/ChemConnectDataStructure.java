package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;

public class ChemConnectDataStructure implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String record = "dcat:record";
	private static String linkedTo = "org:linkedTo";

	String elementType;
	ClassificationInformation classification; 
	
	ArrayList<DataElementInformation> records;
	ArrayList<DataElementInformation> linkedTos;
	ArrayList<DataElementInformation> other;
	MapToChemConnectCompoundDataStructure mapping;
	
	public ChemConnectDataStructure() {
		records = new ArrayList<DataElementInformation>();
		linkedTos = new ArrayList<DataElementInformation>();
		other = new ArrayList<DataElementInformation>();
		mapping = new MapToChemConnectCompoundDataStructure();
		classification = null;
	}

	public ChemConnectDataStructure(ClassificationInformation classification) {
		if(classification != null) {
			this.elementType = classification.getDataType();
		} else {
			this.elementType = null;
		}
		this.classification = classification;
		this.records = new ArrayList<DataElementInformation>();
		this.linkedTos = new ArrayList<DataElementInformation>();
		this.other = new ArrayList<DataElementInformation>();
		this.mapping = new MapToChemConnectCompoundDataStructure();
	}

	public void addElement(DataElementInformation element, ChemConnectCompoundDataStructure structure) {
		if (element.getLink().compareTo(record) == 0) {
			records.add(element);
		} else if (element.getLink().compareTo(linkedTo) == 0) {
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

	public ArrayList<DataElementInformation> getRecords() {
		return records;
	}

	public ArrayList<DataElementInformation> getLinkedTos() {
		return linkedTos;
	}

	public ArrayList<DataElementInformation> getOther() {
		return other;
	}
	
	public MapToChemConnectCompoundDataStructure getMapping() {
		return mapping;
	}
	
	
	
	public ClassificationInformation getClassification() {
		return classification;
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix + "------------ SetOfChemConnectDataStructureElements ------------\n");
		builder.append(prefix + "DataType: " + elementType + "\n");
		if(classification != null) {
			builder.append(prefix + "Classification: " + classification.toString() + "\n");
		}
		String newprefix = prefix + "\t";
		String newprefix2 = newprefix + "\t";
		builder.append(newprefix + "Records\n");
		for (DataElementInformation element : records) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append(newprefix + "LinkedTo\n");
		for (DataElementInformation element : linkedTos) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append(newprefix + "Other\n");
		for (DataElementInformation element : other) {
			builder.append(element.toString(newprefix2));
			builder.append("\n");
		}
		builder.append("\n");
		builder.append(mapping.toString(newprefix));
		return builder.toString();
	}
}
