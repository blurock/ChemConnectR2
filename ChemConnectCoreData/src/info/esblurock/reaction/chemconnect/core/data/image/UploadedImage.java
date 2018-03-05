package info.esblurock.reaction.chemconnect.core.data.image;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UploadedImage extends DatabaseObject {
	
	@Index
    String user;
  
    @Index
    String blobKey;

    @Unindex
    String imageUrl;

    @Index
    String filename;

    @Index
    String description;

    public UploadedImage() {
    }
    
	public UploadedImage(String user, String fileCode, String keyWord, String blobKey, 
			String imageUrl, String filename, String description) {
		super(keyWord,fileCode);
		this.user = user;
		this.blobKey = blobKey;
		this.imageUrl = imageUrl;
		this.filename = filename;
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public String getKeyWord() {
		return super.getIdentifier();
	}

	public String getBlobKey() {
		return blobKey;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getFilename() {
		return filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(user + ":  ");
		build.append("'" + filename + "'   ");
		build.append("'" + description + "'\n");
		build.append(imageUrl + "\n");
		return build.toString();
	}
    
}
