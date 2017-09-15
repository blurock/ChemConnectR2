package info.esblurock.reaction.chemconnect.core.data.transaction.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DataStructureToDatabase extends DatabaseObject {
	
	@Index
	String fileName;
	
	@Unindex
	HashSet<String> dataObjects;

	public DataStructureToDatabase(String fileName, HashSet<String> dataObjects) {
		super();
		this.fileName = fileName;
		this.dataObjects = dataObjects;
	}

	public String getFileName() {
		return fileName;
	}

	public HashSet<String> getDataObjects() {
		return dataObjects;
	}	

}
