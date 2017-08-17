package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class DataSetReference extends DatabaseObject {
	@Index
	String DOI;
	
	@Unindex
	String Title;
	
	@Unindex
	String ReferenceString;
		
	@Unindex
	HashSet<String> authors;

	@Unindex
	HashSet<String> authorLastNames;
	
	public DataSetReference() {
		DOI = null;
		Title = null;
		ReferenceString = null;
		this.authors = null;
		this.authorLastNames = null;
	}


	/**
	 * @param id The identifer of the 
	 * @param dOI The DOI number
	 * @param title The title of the reference
	 * @param referenceString The full reference string
	 * @param authors The list of authors
	 * @param authorLastNames The list of author's last names
	 */
	public DataSetReference(String id, String dOI, String title, String referenceString,
			HashSet<String> authors, HashSet<String> authorLastNames) {
		super(id);
		DOI = dOI;
		Title = title;
		ReferenceString = referenceString;
		this.authors = authors;
		this.authorLastNames = authorLastNames;
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

	public HashSet<String> getAuthorLastNames() {
		return authorLastNames;
	}

	
}
