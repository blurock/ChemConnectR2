package info.esblurock.reaction.chemconnect.core.common.client.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
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

	void getFileInterpretionChoices(GCSBlobFileInformation info, AsyncCallback<HierarchyNode> callback);

	void getUserDatasetCatalogHierarchy(String username, AsyncCallback<DatabaseObjectHierarchy> callback);

	void createNewCatalogHierarchy(DatabaseObject obj, String newSimpleName, 
			String id, String onelinedescription, String catagorytype, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getDevice(DatabaseObject obj, String devicename, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getSetOfObservations(DatabaseObject obj, String observation, String title, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getUploadedFilesHiearchy(ArrayList<String> fileTypes,AsyncCallback<HierarchyNode> callback);

	void getSetOfDatabaseObjectHierarchyForUser(String classType,
			AsyncCallback<ArrayList<DatabaseObjectHierarchy>> callback);

	void createDatabasePerson(DatabaseObject obj, String userClassification, NameOfPerson name, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void createEmptyObject(DatabaseObject obj, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDsFromConceptLink(String concept, AsyncCallback<HierarchyNode> callback);

	void getIDHierarchyFromDataCatalogID(String basecatalog, String catalog, AsyncCallback<HierarchyNode> callback);

	void getCatalogObject(String id, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeYamlObjectHierarchy(String id, String canonicalclass, AsyncCallback<Void> callback);

	void createEmptyMultipleObject(ChemConnectCompoundMultiple multiple,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getStructureFromFileType(String filetype, AsyncCallback<String> callback);

	void getIDHierarchyFromDataCatalogIDAndClassType(String catalogbasename, String classtype,
			AsyncCallback<HierarchyNode> callback);

	void fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy corrspechier,
			ArrayList<String> coltitles,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void createObservationBlockFromSpreadSheet(DatabaseObject obj, String blocktype, DataCatalogID datid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDHierarchyFromDataCatalogAndUser(String datacatalog, AsyncCallback<HierarchyNode> callback);

	void deleteObject(String id, String type, AsyncCallback<Void> callback);

	void extractLinkObjectFromStructure(DatabaseObjectHierarchy hierarchy, String linktypeid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeBlobContent(GCSBlobContent gcs, AsyncCallback<Void> callback);

	void protocolDefinitionSetup(String protocolS, String user, AsyncCallback<ProtocolSetupTransfer> callback);

	void fillProtocolDefinition(DatabaseObjectHierarchy hierarchy, ArrayList<String> obsid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getInitialProtocol(DatabaseObject obj, String title, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void createDatasetImage(DatabaseObject obj, DataCatalogID catid, String imageType, GCSBlobFileInformation info,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDHierarchyFromFamilyNameAndUser(String familyname, AsyncCallback<ArrayList<NameOfPerson>> callback);

	void getTopCatalogObject(String id, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);


}
