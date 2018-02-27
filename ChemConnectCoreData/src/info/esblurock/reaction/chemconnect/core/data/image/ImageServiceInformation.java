package info.esblurock.reaction.chemconnect.core.data.image;

import java.io.Serializable;

public class ImageServiceInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;

	String user;

	String fileCode;
	
    String keyWord;
    
	String bucketName;
	
	String uploadUrl;
	
	public ImageServiceInformation() {
	}
	
	public ImageServiceInformation(String user, String fileCode, String keyWord, 
			String bucketName, String uploadUrl) {
		super();
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
		this.bucketName = bucketName;
		this.uploadUrl = uploadUrl;
	}

	public String getUser() {
		return user;
	}

	public String getFileCode() {
		return fileCode;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public String getBucketName() {
		return bucketName;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	
}
