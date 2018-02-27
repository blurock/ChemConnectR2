package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;



public interface UserImageServiceAsync {


	void getUploadedImageSet(ImageServiceInformation serviceInfo, AsyncCallback<ArrayList<UploadedImage>> callback);

	void getBlobstoreUploadUrl(String keywordName, boolean uploadService,
			AsyncCallback<ImageServiceInformation> callback);

	void getUploadedImageSetFromKeywordAndUser(String keyword, AsyncCallback<ArrayList<UploadedImage>> callback);

	void deleteFromStorage(String blobkey, AsyncCallback<String> callback);

	void updateImages(ArrayList<UploadedImage> images, AsyncCallback<String> callback);

}
