package info.esblurock.reaction.chemconnect.core.data.dataset;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DataSpecification extends DatabaseObject {
	@Index
	String purposeandconcept;
	
	public DataSpecification() {
		this.purposeandconcept = null;;
	}
	public DataSpecification(DataSpecification spec) {
		this.fill(spec);
	}
	public DataSpecification(DatabaseObject obj,
			String purposeandconcept) {
		this.fill(obj,purposeandconcept);
	}
	public void fill(DatabaseObject obj,
			String purposeandconcept) {
		super.fill(obj);
		this.purposeandconcept = purposeandconcept;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		DataSpecification spec = (DataSpecification)  object;
		this.purposeandconcept = spec.getPurposeandconcept();
	}
	
	public String getPurposeandconcept() {
		return purposeandconcept;
	}

	public void setPurposeandconcept(String purposeandconcept) {
		this.purposeandconcept = purposeandconcept;
	}
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "PurposeAndConcept: " + purposeandconcept + "\n");
		return builder.toString();
	}	
	
	
}
