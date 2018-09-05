package info.esblurock.reaction.chemconnect.core.data.gcs;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class GCSBlobFileInformation extends DatabaseObject {

	@Index
	String bucket;
	@Index
	String path;
	@Index
	String filename;
	@Index
	String filetype;
	@Index
	String description;
	
	public GCSBlobFileInformation() {
	}
	public GCSBlobFileInformation(DatabaseObject obj, 
			String bucket, String path, String filename, String filetype, 
			String description) {
		super(obj);
		this.bucket = bucket;
		this.path = path;
		this.filename = filename;
		this.filetype = filetype;
		this.description = description;
}

	public String getBucket() {
		return bucket;
	}
	public String getPath() {
		return path;
	}
	public String getFilename() {
		return filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public String getDescription() {
		return description;
	}
	
	public String getGSFilename() {
		String name = filename;
		if(path != null) {
			if(path.length() > 0) {
				name =  path + "/" + filename;
			}
		}
		return name;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + getGSFilename() + "\n");
		build.append(prefix + "Type: " + filetype + "\n" + description + "\n");
		return build.toString();
	}
	
}
