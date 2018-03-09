package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class PrimitiveReferenceInformation extends PrimitiveDataStructureInformation {

	@Index
	String title;
	@Index
	String referenceString;
	@Index
	String DOI;
	@Unindex
	ArrayList<PrimitiveParameterValueInformation> parameters;
	@Unindex
	ArrayList<PrimitivePersonNameInformation> names;
	
	public PrimitiveReferenceInformation() {
		super(new DatabaseObject(),"Reference","DOI");
		this.title = "Title of Reference";
		this.referenceString = "Reference String";
		this.names = new ArrayList<PrimitivePersonNameInformation>();
		this.parameters = new ArrayList<PrimitiveParameterValueInformation>();
	}
	public PrimitiveReferenceInformation(PrimitiveDataStructureInformation info,
			String title, String referenceString,
			ArrayList<PrimitivePersonNameInformation> names,
			ArrayList<PrimitiveParameterValueInformation> parameters) {
		super(info);
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
