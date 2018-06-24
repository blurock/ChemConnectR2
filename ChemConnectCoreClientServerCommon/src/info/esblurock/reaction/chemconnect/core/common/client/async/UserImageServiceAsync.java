package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

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

	void deleteTransaction(String sourceID, AsyncCallback<Void> callback);

	void getFileInterpretionChoices(GCSBlobFileInformation info, AsyncCallback<HierarchyNode> callback);

	void getUserDatasetCatalogHierarchy(String username, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getNewCatalogHierarchy(DatabaseObject obj, String id, String onelinedescription, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getDevice(DatabaseObject obj, String devicename, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getSetOfObservations(DatabaseObject obj, String observation, String title,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getMethodology(DatabaseObject obj, String methodology, String title,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getUploadedFilesHiearchy(ArrayList<String> fileTypes,AsyncCallback<HierarchyNode> callback);

}
