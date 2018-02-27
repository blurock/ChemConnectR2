package info.esblurock.reaction.chemconnect.core.data.image;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;


@SuppressWarnings("serial")
@Entity
public class ImageUploadTransaction extends DatabaseObject {

	@Index
    String user;

	@Index
    String keyWord;
    
	@Index
	String bucketName;
	
	@Index
	String uploadUrl;
	
	public ImageUploadTransaction() {
	}

	public ImageUploadTransaction(String user, String fileCode, String keyWord, String bucketName,String uploadUrl) {
		super(null,fileCode);
		this.user = user;
		this.keyWord = keyWord;
		this.bucketName = bucketName;
		this.uploadUrl = uploadUrl;
	}

	public String getUser() {
		return user;
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
