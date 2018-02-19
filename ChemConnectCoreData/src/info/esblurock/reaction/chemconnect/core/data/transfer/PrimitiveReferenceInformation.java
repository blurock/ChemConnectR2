package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PrimitiveReferenceInformation extends PrimitiveDataStructureInformation {

	String title;
	String referenceString;
	String DOI;
	ArrayList<PrimitiveParameterValueInformation> parameters;
	ArrayList<PrimitivePersonNameInformation> names;
	
	public PrimitiveReferenceInformation() {
		super("Reference", "id", "DOI");
		this.title = "Title of Reference";
		this.referenceString = "Reference String";
		this.names = new ArrayList<PrimitivePersonNameInformation>();
		this.parameters = new ArrayList<PrimitiveParameterValueInformation>();
	}
	public PrimitiveReferenceInformation(String identifier, String propertyClass,
			String doi, String title, String referenceString,
			ArrayList<PrimitivePersonNameInformation> names,
			ArrayList<PrimitiveParameterValueInformation> parameters) {
		super(propertyClass, identifier, doi);
		this.title = title;
		this.referenceString = referenceString;
		this.names = names;
		this.parameters = parameters;
	}
	public String getTitle() {
		return title;
	}
	public String getReferenceString() {
		return referenceString;
	}
	public String getDOI() {
		return DOI;
	}
	public ArrayList<PrimitiveParameterValueInformation> getParameters() {
		return parameters;
	}
	public ArrayList<PrimitivePersonNameInformation> getNames() {
		return names;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		super.toString(prefix);
		String newprefix = prefix + "  \t";
		builder.append("\n" + newprefix);
		builder.append(title);
		builder.append("\n" + newprefix);
		builder.append(referenceString);
		builder.append("\n");
		for(PrimitivePersonNameInformation name : names) {
			builder.append(name.toString(newprefix));
		}
		for(PrimitiveParameterValueInformation value : parameters) {
			builder.append(value.toString(newprefix));
		}
		return builder.toString();
		
	}
	
}
