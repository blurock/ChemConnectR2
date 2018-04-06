package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;



public interface UserImageServiceAsync {


	void getUploadedImageSet(ImageServiceInformation serviceInfo, AsyncCallback<ArrayList<UploadedImage>> callback);

	void getBlobstoreUploadUrl(String keywordName, boolean uploadService,
			AsyncCallback<ImageServiceInformation> callback);

	void getUploadedImageSetFromKeywordAndUser(String keyword, AsyncCallback<ArrayList<UploadedImage>> callback);

	void deleteFromStorage(String blobkey, AsyncCallback<String> callback);

	void updateImages(ArrayList<UploadedImage> images, AsyncCallback<String> callback);

	void moveBlobFromUpload(GCSBlobFileInformation fileinfo, AsyncCallback<GCSBlobContent> callback);

	void moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source,
			AsyncCallback<GCSBlobContent> callback);

	void getBlobContent(GCSBlobFileInformation gcsinfo, AsyncCallback<GCSBlobContent> callback);

	void getBlobAsLines(GCSBlobContent info, AsyncCallback<ArrayList<String>> callback);

	void getUploadedFiles(AsyncCallback<ArrayList<GCSBlobFileInformation>> callback);

	void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset, AsyncCallback<Void> callback);

	void deleteUploadedFile(GCSBlobFileInformation gcsinfo, AsyncCallback<Void> callback);

	void retrieveBlobFromContent(String filename, String content, AsyncCallback<GCSBlobFileInformation> callback);

	void retrieveBlobFromURL(String requestUrl, AsyncCallback<GCSBlobFileInformation> callback);
	

}
