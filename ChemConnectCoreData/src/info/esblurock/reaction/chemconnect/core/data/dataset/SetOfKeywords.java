package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class SetOfKeywords extends DatabaseObject {

	@Index
	HashSet<String> keywords;

	
	public SetOfKeywords() {
		super();
		this.keywords = new HashSet<String>();
	}


	public SetOfKeywords(DatabaseObject obj) {
		super(obj);
		this.keywords = new HashSet<String>();
	}
	public SetOfKeywords(DatabaseObject obj, HashSet<String> keywords) {
		super(obj);
		this.keywords = keywords;
	}


	public HashSet<String> getKeywords() {
		return keywords;
	}
	
	

}
