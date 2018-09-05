package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;



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
	
	DatabaseObjectHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException;
	
	DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj, String id, 
			String onelinedescription, String catagorytype) throws IOException;

	DatabaseObjectHierarchy getDevice(DatabaseObject obj, String devicename, DataCatalogID catid);
	
	DatabaseObjectHierarchy getSetOfObservations(DatabaseObject obj, String observation, String title, DataCatalogID catid);
	
	DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException;
	
	public DatabaseObjectHierarchy getMethodology(DatabaseObject obj, String methodology, String title, DataCatalogID catid);
	
	HierarchyNode getUploadedFilesHiearchy(ArrayList<String> fileTypes) throws IOException;
	
	ArrayList<DatabaseObjectHierarchy> getSetOfDatabaseObjectHierarchyForUser(String classType) throws IOException;
	
	DatabaseObjectHierarchy createDatabasePerson(DatabaseObject obj, String userClassification, NameOfPerson name, DataCatalogID catid);
	
	DatabaseObjectHierarchy createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid);
	
	DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj, String dataType);
	
	HierarchyNode getIDsFromConceptLink(String concept) throws IOException;
	
	HierarchyNode getIDHierarchyFromDataCatalogID(String basecatalog, String catalog) throws IOException;
	
	DatabaseObjectHierarchy getCatalogObject(String id, String dataType);
	
	void writeYamlObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException;
	
	DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple);
}
