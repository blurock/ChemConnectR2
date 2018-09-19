package info.esblurock.reaction.chemconnect.core.data.gcs;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class GCSInputFileInterpretation  extends DatabaseObject {
	@Index
	String bucket;
	@Index
	String fullBlobName;
	@Index
	String interpretingClass;
	
	public GCSInputFileInterpretation(DatabaseObject obj, 
			String bucket, String fullBlobName, String interpretingClass) {
		super(obj);
		this.bucket = bucket;
		this.fullBlobName = fullBlobName;
		this.interpretingClass = interpretingClass;
	}
	public String getBucket() {
		return bucket;
	}
	public String getFullBlobName() {
		return fullBlobName;
	}
	public String getInterpretingClass() {
		return interpretingClass;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Bucket: " + getBucket() + " FullBlobPath: " + fullBlobName +"\n");
		build.append(prefix + "Classname: " + interpretingClass + "\n");
		return build.toString();
	}

}
