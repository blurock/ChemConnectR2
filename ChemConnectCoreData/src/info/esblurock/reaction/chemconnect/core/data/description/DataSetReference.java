package info.esblurock.reaction.chemconnect.core.data.description;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DataSetReference extends ChemConnectCompoundDataStructure {

	/** DOI reference for article
	 * 
	 */
	@Index
	String DOI;
	
	// The full title of the paper
	@Unindex
	String Title;
	
	// The bibliographic reference
	@Unindex
	String ReferenceString;
		
	@Index
	String authors;
	
	public DataSetReference() {
		init();
		
	}

	void init() {
		DOI = "";
		Title = "";
		ReferenceString = "";
		this.authors = "";
	}
	
	/**
	 * @param id The identifer of the 
	 * @param dOI The DOI number
	 * @param title The title of the reference
	 * @param referenceString The full reference string
	 * @param authors The list of authors
	 * @param authorLastNames The list of author's last names
	 */
	public DataSetReference(ChemConnectCompoundDataStructure compound,
			String dOI, String title, String referenceString,
			String authors) {
		this.fill(compound,dOI,title,referenceString,authors);
	}
	
	public void fill(ChemConnectCompoundDataStructure compound,
			String dOI, String title, String referenceString,
			String authors) {
		super.fill(compound);
		this.DOI = dOI;
		this.Title = title;
		this.ReferenceString = referenceString;
		this.authors = authors;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		DataSetReference ref = (DataSetReference) object;
		this.DOI = ref.getDOI();
		this.Title = ref.getTitle();
		this.ReferenceString = ref.getReferenceString();
		this.authors = ref.getAuthors();
	}

	
	public String getDOI() {
		return DOI;
	}

	public String getTitle() {
		return Title;
	}

	public String getReferenceString() {
		return ReferenceString;
	}

	public void setDOI(String dOI) {
		DOI = dOI;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public void setReferenceString(String referenceString) {
		ReferenceString = referenceString;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getAuthors() {
		return authors;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Title: " + Title);
		builder.append("\n");
		builder.append(prefix);
		builder.append("Reference: " + ReferenceString);
		builder.append("\n");
		builder.append(prefix);
		builder.append("DOI: " + DOI);
		builder.append("\n");
		builder.append(prefix + "Authors: " + authors);
		builder.append("\n");
		return builder.toString();
	}
	
	
}
