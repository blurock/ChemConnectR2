package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;



@RemoteServiceRelativePath("images")
public interface UserImageService  extends RemoteService  {
	static public String uploadName = "image";
	static public String bucketName = "images";
	static public String uploadRoot = "/upload";
	static public String sourceFileParameter = "source";
	static public String keywordNameParameter = "keywordName";

	
	   public static class Util {
	       private static UserImageServiceAsync instance;

	       public static UserImageServiceAsync getInstance() {
	           if (instance == null) {
	               instance = GWT.create(UserImageService.class);
	           }
	           return instance;
	       }
	   }

	
	ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService);

	ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException;

	ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException;

	String deleteFromStorage(String blobkey) throws IOException;

	String updateImages(ArrayList<UploadedImage> images) throws IOException;
	
	GCSBlobContent moveBlobFromUpload(GCSBlobFileInformation fileinfo);
	
	GCSBlobContent moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source);
	
	GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo);
	
	ArrayList<String> getBlobAsLines(GCSBlobContent info);
	
	void deleteUploadedFile(GCSBlobFileInformation gcsinfo);
	
	void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset);
	
	ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException;
	
	GCSBlobFileInformation retrieveBlobFromContent(String filename, String content) throws IOException;
	
	GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException;
	
	void deleteTransaction(String sourceID) throws IOException;
	
	HierarchyNode getFileInterpretionChoices(GCSBlobFileInformation info) throws IOException;
}
