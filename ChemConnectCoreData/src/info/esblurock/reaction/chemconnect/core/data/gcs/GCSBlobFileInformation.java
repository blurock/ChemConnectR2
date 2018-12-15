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
	public GCSBlobFileInformation(GCSBlobFileInformation info) {
		super(info);
		this.bucket = info.getBucket();
		this.path = info.getPath();
		this.filename = info.getFilename();
		this.filetype = info.getFiletype();
		this.description = info.getDescription();		
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

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public void setDescription(String description) {
		this.description = description;
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
		build.append(prefix + bucket + ":   " + getGSFilename() + "\n");
		build.append("    (Type: " + filetype + "        " + description + ")\n");
		return build.toString();
	}
	
}
