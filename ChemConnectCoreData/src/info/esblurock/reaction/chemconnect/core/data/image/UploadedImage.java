package info.esblurock.reaction.chemconnect.core.data.image;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class UploadedImage extends DatabaseObject {
	
	@Index
    String user;
  
    @Index
    String keyWord;
    
    @Index
    String blobKey;

    @Index
    String imageUrl;

    @Index
    String filename;

    @Index
    String description;

    public UploadedImage() {
    }
    
	public UploadedImage(String user, String fileCode, String keyWord, String blobKey, 
			String imageUrl, String filename, String description) {
		super(null,fileCode);
		this.user = user;
		this.keyWord = keyWord;
		this.blobKey = blobKey;
		this.imageUrl = imageUrl;
		this.filename = filename;
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public String getKeyWord() {
		return keyWord;
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
    
    
}
