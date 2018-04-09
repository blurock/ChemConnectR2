package info.esblurock.reaction.chemconnect.core.data.gcs;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ParsedFilename extends DatabaseObject {
	
	@Index
	boolean isURL;
	@Index
	String hostname;
	
	@Index
	String filetype; 
	
	@Index
	String original;
	@Unindex
	ArrayList<String> path;
	@Index
	String filename;
	@Index
	String extension;

	public ParsedFilename(DatabaseObject obj, String original, String filetype, ArrayList<String> path, String filename, String extension) {
		super(obj);
		isURL = false;
		this.original = original;
		this.path = path;
		this.filename = filename;
		this.extension = extension;
		this.filetype = filetype;
	}
	
	public ParsedFilename(DatabaseObject obj, String hostname, ParsedFilename info) {
		super(obj);
		isURL = true;
		this.hostname = hostname;
		this.original = info.getOriginal();
		this.path = info.getPath();
		this.filename = info.getFilename();
		this.extension = info.getExtension();
		this.filetype = info.getFiletype();
	}

	public boolean isURL() {
		return isURL;
	}

	public String getHostname() {
		return hostname;
	}

	public String getOriginal() {
		return original;
	}

	public ArrayList<String> getPath() {
		return path;
	}

	public String getFilename() {
		return filename;
	}

	public String getExtension() {
		return extension;
	}
	
	public String getFiletype() {
		return filetype;
	}

	public String getFullFilename() {
		return filename + "." + extension;
	}
	public String getFullFilenameWithPath() {
		StringBuilder build = new StringBuilder();
		for(String dir : path) {
			build.append("/");
			build.append(dir);
		}
		build.append("/");
		build.append(filename + "." + extension);
		
		return build.toString();
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Original:  " + original + "\n");
		if(isURL) {
			build.append(prefix + "URL Host: " + hostname + "\n");
		}
		
		build.append(prefix);
		build.append("Path: " + path);
		build.append("\n");
		
		build.append(prefix);
		build.append("Filetype: " + filetype);
		build.append(" \tFilebase: " + filename);
		build.append(" \tExt: " + extension);
		build.append("\n");
		return build.toString();
	}
	
}
