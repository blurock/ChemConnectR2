package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DataSetReference extends DatabaseObject {

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
		
	// The index keys of the authors
	@Unindex
	HashSet<String> authors;
	
	public DataSetReference() {
		DOI = null;
		Title = null;
		ReferenceString = null;
		this.authors = null;
	}


	/**
	 * @param id The identifer of the 
	 * @param dOI The DOI number
	 * @param title The title of the reference
	 * @param referenceString The full reference string
	 * @param authors The list of authors
	 * @param authorLastNames The list of author's last names
	 */
	public DataSetReference(String identifier, String access, String owner,
			String dOI, String title, String referenceString,
			HashSet<String> authors) {
		super(identifier,access,owner);
		DOI = dOI;
		Title = title;
		ReferenceString = referenceString;
		this.authors = authors;
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

	public HashSet<String> getAuthors() {
		return authors;
	}

}
