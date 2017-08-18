package info.esblurock.reaction.chemconnect.core.data.initialization;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
public class InitializationFile extends DatabaseObject {

    @Index
	String fileName;

	public InitializationFile() {
		
	}
		public InitializationFile(String fileName) {
		super();
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

}
