package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;



@RemoteServiceRelativePath("images")
public interface UserImageService  extends RemoteService  {
	static public String uploadName = "image";
	static public String bucketName = "images";
	static public String uploadRoot = "/upload";
	static public String sourceFileParameter = "source";
	static public String keywordNameParameter = "keywordName";
	
	ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService);

	ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException;

	ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException;

	String deleteFromStorage(String blobkey) throws IOException;

	String updateImages(ArrayList<UploadedImage> images) throws IOException;
}
