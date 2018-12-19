package info.esblurock.reaction.core.server.db.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.extract.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.db.extract.ExtractLinkObjectFromStructure;
import info.esblurock.reaction.core.server.db.protocol.ProtocolSetupUtilities;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.read.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.services.ServerBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends ServerBase implements UserImageService {

	public static Storage storage = null;
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	/*
	 * private static final GcsService gcsService =
	 * GcsServiceFactory.createGcsService(new RetryParams.Builder()
	 * .initialRetryDelayMillis(10) .retryMaxAttempts(10)
	 * .totalRetryPeriodMillis(15000) .build());
	 */
	// private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	public static String fileCodeParameter = "fileCode";
	public static String userParameter = "user";
	public static String keywordParameter = "identifier";
	public static String blobkeyParameter = "blobKey";
	public static String keyAsStringParameter = "keyAsString";

	@Override
	public ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService) {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();

		String outputSourceCode = null;
		if (uploadService) {
			outputSourceCode = QueryBase.getDataSourceIdentification(user.getName());
		}
		UploadOptions options = UploadOptions.Builder.withGoogleStorageBucketName(bucketName);
		String baseurl = uploadRoot + "?" + sourceFileParameter + "=" + outputSourceCode + "&" + keywordNameParameter
				+ "=" + keywordName;
		String uploadUrl = blobstoreService.createUploadUrl(baseurl, options);

		if (uploadService) {
			ImageUploadTransaction imageinfo = new ImageUploadTransaction(user.getName(), outputSourceCode, keywordName,
					bucketName, uploadUrl);
			DatabaseWriteBase.writeObjectWithTransaction(imageinfo);
		} else {
			System.out.println("uploadService: no write");
		}
		ImageServiceInformation returninfo = new ImageServiceInformation(user.getName(), outputSourceCode, keywordName,
				bucketName, uploadUrl);

		return returninfo;
	}

	@Override
	public ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException {
		ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();

		String keyword = serviceInfo.getKeyWord();
		String user = serviceInfo.getUser();

		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		values.add(fileCodeParameter, keyword);
		values.add(userParameter, user);
		String classname = UploadedImage.class.getName();
		QuerySetupBase querybase = new QuerySetupBase(user,classname, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				UploadedImage uploaded = (UploadedImage) obj;
				imagelst.add(uploaded);
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("getUploadedImageSet Class not found: " + classname);
		}
		return imagelst;
	}

	@Override
	public ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException {
		ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();

		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		values.add(keywordParameter, keyword);
		values.add(userParameter, user.getName());
		String classname = UploadedImage.class.getName();
		QuerySetupBase querybase = new QuerySetupBase(classname, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				if (obj instanceof UploadedImage) {
					UploadedImage uploaded = (UploadedImage) obj;
					imagelst.add(uploaded);
				} else {
					System.out.println("getUploadedImageSetFromKeywordAndUser: not a UploadedImage: "
							+ obj.getClass().getCanonicalName());
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("getUploadedImageSetFromKeywordAndUser Class not found: " + classname);
		}
		return imagelst;
	}

	@Override
	public String updateImages(ArrayList<UploadedImage> images) throws IOException {
		String ans = "Successfully updated " + images.size() + " images ";
		DatabaseWriteBase.writeListOfDatabaseObjects(images);
		return ans;
	}

	public GCSBlobContent moveBlobFromUpload(GCSBlobFileInformation target) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();

		String path = GCSServiceRoutines.createUploadPath(util.getUserName());

		String id = target.getIdentifier();
		String access = user.getName();
		String owner = user.getName();
		String sourceID = QueryBase.getDataSourceIdentification(user.getName());
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);

		GCSBlobFileInformation source = new GCSBlobFileInformation(obj, GoogleCloudStorageConstants.uploadBucket, path,
				target.getFilename(), target.getFiletype(), target.getDescription());

		target.setSourceID(source.getSourceID());

		return moveBlob(target, source);
	}

	public GCSBlobContent moveBlob(GCSBlobFileInformation target, GCSBlobFileInformation source) throws IOException {
		return GCSServiceRoutines.moveBlob(target, source);
	}

	public GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo) {
		return getContent(gcsinfo);
	}
	public void writeBlobContent(GCSBlobContent gcs) throws IOException {
		GCSServiceRoutines.writeBlobContent(gcs);
	}

	public ArrayList<String> getBlobAsLines(GCSBlobContent info) {
		GCSBlobContent gcs = getBlobContent(info.getInfo());
		String text = gcs.getBytes();
		ArrayList<String> lines = new ArrayList<String>();
		Scanner tok = new Scanner(text);
		while (tok.hasNextLine()) {
			lines.add(tok.nextLine());
		}
		tok.close();
		return lines;
	}

	public void deleteUploadedFile(GCSBlobFileInformation gcsinfo) {
		deleteBlob(gcsinfo);
	}

	public void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset) {
		for (GCSBlobFileInformation info : fileset) {
			deleteUploadedFile(info);
		}
	}

	public ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException {
		SingleQueryResult result = null;
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		ContextAndSessionUtilities context = getUtilities();
		UserDTO user = context.getUserInfo();
		if(user == null) {
			throw new IOException("User information not available: try logging in again");
		}
		String username = user.getName();
		values.add("owner", username);
		values.add("bucket", GoogleCloudStorageConstants.uploadBucket);
		
		QuerySetupBase query = new QuerySetupBase(GCSBlobFileInformation.class.getCanonicalName(), values);
		query.setAccess(username);
		try {
			result = QueryBase.StandardQueryResult(query);
		} catch (ClassNotFoundException e) {
			throw new IOException("Class Not found: " + GCSBlobFileInformation.class.getCanonicalName());
		}
		ArrayList<GCSBlobFileInformation> fileset = new ArrayList<GCSBlobFileInformation>();
		for (DatabaseObject obj : result.getResults()) {
			fileset.add((GCSBlobFileInformation) obj);
		}
		return fileset;
	}

	public HierarchyNode getUploadedFilesHiearchy(ArrayList<String> fileTypes) throws IOException {
		ArrayList<GCSBlobFileInformation> lst = getUploadedFiles();
		return getBlobHierarchy(lst,fileTypes);
	}
	public HierarchyNode getBlobHierarchy(ArrayList<GCSBlobFileInformation> lst, ArrayList<String> types)  {
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("Blob Files");
		HierarchyNode hierarchy = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		return hierarchy;
	}
	public HierarchyNode getIDsFromConceptLink(String concept) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		String classType = ConceptParsing.findObjectTypeFromLinkConcept(concept);
		Set<String> ids = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects(user.getName(),classType);
		HierarchyNode topnode = ParseUtilities.parseIDsToHierarchyNode(concept,ids,false);
		return topnode;
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogID(String basecatalog, String catalog) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogID(user.getName(), basecatalog, catalog);
		
	}

	public GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException {
		String uploadDescriptionText = "Uploaded File from URL";

		URL urlconnect = new URL(requestUrl);
		URLConnection c = urlconnect.openConnection();
		String contentType = c.getContentType();

		URL urlstream = new URL(requestUrl);
		InputStream in = urlstream.openStream();
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		String path = GCSServiceRoutines.createUploadPath(util.getUserName());
		GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
				GoogleCloudStorageConstants.uploadBucket,
				path, requestUrl, contentType, uploadDescriptionText,
				util.getId(),util.getUserName());
		retrieveContentFromStream(in, source);

		return source;
	}

	public GCSBlobFileInformation retrieveBlobFromContent(String filename, String content) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		String path = GCSServiceRoutines.createUploadPath(util.getUserName());
		String contentType = "text/plain";
		String uploadDescriptionText = "Uploaded File from text input";
		GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
				GoogleCloudStorageConstants.uploadBucket, 
				path, filename, contentType, uploadDescriptionText,
				util.getId(),util.getUserName());

		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		retrieveContentFromStream(in, source);
		return source;
	}

	public void deleteTransaction(String sourceID) throws IOException {
		deleteTransactionFromSourceID(sourceID);
	}

	private void retrieveContentFromStream(InputStream in, GCSBlobFileInformation source) {
		BlobInfo info = BlobInfo.newBuilder(source.getBucket(), source.getGSFilename())
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
				.setContentType(source.getFiletype()).build();

		@SuppressWarnings({ "deprecation", "unused" })
		BlobInfo blobInfo = storage.create(info, in);
		DatabaseWriteBase.writeObjectWithTransaction(source);

	}

	public ArrayList<DatabaseObjectHierarchy> getSetOfDatabaseObjectHierarchyForUser(String classType) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		if(user == null) {
			throw new IOException("User information not available: try logging in again");
		}
		ArrayList<DatabaseObjectHierarchy> objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(user.getName(), classType);
/*
		for(DatabaseObjectHierarchy hierarchy : objects) {
			System.out.println(classType + ": -----------------------------------------------------------");
			System.out.println(hierarchy.toString());
			System.out.println(classType + ": -----------------------------------------------------------");
		}
		*/
		return objects;
	}
	
	public DatabaseObjectHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException {
		String uid = CreateDefaultObjectsFactory.userCatalogHierarchyID(username);
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getDatabaseObjectHierarchy(uid);
		return hierarchy;
	}

	public DatabaseObjectHierarchy getDevice(DatabaseObject obj, String devicename,DataCatalogID datid) {
		DatabaseObjectHierarchy devicehier = null;
		String classname = SubSystemDescription.class.getCanonicalName();
		try {
			QueryBase.getDatabaseObjectFromIdentifier(classname, obj.getIdentifier());
			devicehier = ExtractCatalogInformation.getCatalogObject(obj.getIdentifier(),
					MetaDataKeywords.subSystemDescription);
		} catch (IOException e) {
			devicehier = CreateDefaultObjectsFactory.fillSubSystemDescription(obj, devicename, datid);
		}
		return devicehier;
	}

	public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj, String dataType) {
		DatabaseObjectHierarchy hierarchy = null;
		boolean isSimple = ConceptParsing.isAChemConnectPrimitiveDataStructure(dataType);
		if(isSimple) {
			
			hierarchy = new DatabaseObjectHierarchy();
		} else {
			ClassificationInformation info = DatasetOntologyParsing.getIdentificationInformation(dataType);
			String structureName = info.getDataType();
			InterpretData interpret = InterpretData.valueOf(structureName);
			hierarchy = interpret.createEmptyObject(obj);
		}
		return hierarchy;
	}
	public DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple) {
		return CreateDefaultObjectsFactory.createEmptyMultipleObject(multiple);
	}
	
	public DatabaseObjectHierarchy createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid) {
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillOrganization(obj, shortname, organizationname,catid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createDatabasePerson(DatabaseObject obj, String userClassification, NameOfPerson name, DataCatalogID catid) {
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj, 
				MetaDataKeywords.accessTypeStandardUser, userClassification, name,catid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createDatasetImage(DatabaseObject obj,DataCatalogID catid,
			String imageType, GCSBlobFileInformation info) throws IOException {
		String path = catid.getFullPath("/");
		System.out.println(info.toString("createDatasetImage  info: "));
		System.out.println(catid.toString("createDatasetImage catid: "));
		String extension = ConceptParsing.getFileExtension(imageType);
		System.out.println("createDatasetImage extension: " + imageType + ":" + extension);
		
		String filename = null;
		if(extension != null) {
			filename = catid.getSimpleCatalogName() + "." + extension;
		} else {
			filename = info.getFilename();
		}
		
		GCSBlobFileInformation target = new GCSBlobFileInformation(obj, GoogleCloudStorageConstants.storageBucket, path,
				filename, info.getFiletype(), info.getDescription());
		System.out.println(target.toString("createDatasetImage  target: "));
		info.setSourceID(target.getSourceID());

		GCSBlobContent content = moveBlob(target,info);
		System.out.println(content.toString("createDatasetImage  content: "));

		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillDatasetImage(obj, catid, imageType, content.getUrl());
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy getSetOfObservations(DatabaseObject obj, String observation, String title,DataCatalogID datid) {
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		obj.setSourceID(sourceID);
		obj.nullKey();
	
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillSetOfObservations(obj, observation, title,datid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid) {
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillObservationBlockFromSpreadSheet(obj,
				blocktype,datid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy getCatalogObject(String id, String dataType) {
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject(id,dataType);
		return readhierarchy;
	}
	
	/** fillMatrixSpecificationCorrespondence
	 * 
	 * Fill in matrix correspondence from matrix and specification
	 *   
	 *   MatrixSpecificationCorrespondence hierarchy
	 * 
	 * @param corrspechier Hierarchy for MatrixSpecificationCorrespondenceSet
	 */
	public DatabaseObjectHierarchy fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy corrspechier,ArrayList<String> coltitles) {
		CreateDefaultObjectsFactory.fillMatrixSpecificationCorrespondence(corrspechier, coltitles);
		return corrspechier;
	}

	public DatabaseObjectHierarchy getInitialProtocol(DatabaseObject obj, String title, DataCatalogID catid) {
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		obj.setSourceID(sourceID);
		obj.nullKey();
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.protocolDefinitionSetup(obj, title, catid);
		return hierarchy;
	}
	public ProtocolSetupTransfer protocolDefinitionSetup(String protocolS, String user) throws IOException {
		ProtocolSetupTransfer transfer = ProtocolSetupUtilities.observationsForProtocol(protocolS, user);
		return transfer;
	}
	public DatabaseObjectHierarchy fillProtocolDefinition(DatabaseObjectHierarchy hierarchy, ArrayList<String> obsid) {
		CreateDefaultObjectsFactory.fillProtocolDefinition(hierarchy, obsid);
		return hierarchy;
	}
	public DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj, String id, 
			String onelinedescription, String catagorytype)
			throws IOException {
		
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		DatabaseObjectHierarchy subs = ExtractCatalogInformation.createNewCatalogHierarchy(obj, id, onelinedescription, sourceID, catagorytype);
		return subs;
	}

	public DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException {
		DatabaseObjectHierarchy hier = null;
		try {
			WriteReadDatabaseObjects.updateSourceID(hierarchy);
			hier = WriteReadDatabaseObjects.writeDatabaseObjectHierarchyWithTransaction(hierarchy);
		} catch (Exception ex) {
			System.out.println("writeDatabaseObjectHierarchy  error in writing");
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new IOException("Error in writing objects");
		}
		return hier;
	}
	
	public DatabaseObjectHierarchy  extractLinkObjectFromStructure(DatabaseObjectHierarchy hierarchy, String linktypeid) throws IOException {
		return ExtractLinkObjectFromStructure.extract(hierarchy, linktypeid);
	}

	
	public void writeYamlObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException {
		try {
			ContextAndSessionUtilities util = getUtilities();
			String username = util.getUserName();
			String sessionid = util.getId();
			ReadWriteYamlDatabaseObjectHierarchy.writeAsYamlToGCS(hierarchy,username,sessionid);
		} catch (Exception ex) {
			System.out.println("writeYamlObjectHierarchy  error in writing");
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new IOException("Error in writing objects");
		}

	}
	
	
	public ArrayList<NameOfPerson> getIDHierarchyFromFamilyNameAndUser(String familyname) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		return WriteReadDatabaseObjects.getIDHierarchyFromFamilyNameAndUser(user,familyname);
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogAndUser(String datacatalog) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogAndUser(user,datacatalog,null);
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogIDAndClassType(String catalogbasename, String classtype) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogIDAndClassType(user, 
				catalogbasename,classtype);
	}

	public HierarchyNode getFileInterpretionChoices(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = parseFilename(info);
		return ParseUtilities.getFileInterpretionChoices(parsed);
	}
	
	public String getStructureFromFileType(String filetype) {
		return ConceptParsing.getStructureFromFileType(filetype);
	}

	public void deleteObject(String id, String type) throws IOException {
		WriteReadDatabaseObjects.deleteObject(id, type);
	}

	
	
	public static ParsedFilename parseFilename(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = ParseUtilities.fillFileInformation(info, info.getFilename(), info.getFiletype());
		return parsed;
	}

	public static void deleteTransactionFromSourceID(String sourceID) throws IOException {
		TransactionInfo info = (TransactionInfo) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
				TransactionInfo.class.getCanonicalName(), "sourceID", sourceID);
		DatabaseWriteBase.deleteTransactionInfo(info);
	}

	public static void deleteBlob(GCSBlobFileInformation gcsinfo) {
		BlobId blobId = BlobId.of(gcsinfo.getBucket(), gcsinfo.getGSFilename());
		storage.delete(blobId);
	}
	
	
/*
	public static String createUploadPath(ContextAndSessionUtilities util) {
		String username = util.getUserName();
		String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + username;
		return path;
	}
*/
	/*
	public static GCSBlobFileInformation createInitialUploadInfo(String bucket, String filename, String contentType,
			String uploadDescriptionText, ContextAndSessionUtilities util) {
		UserDTO user = util.getUserInfo();

		String path = GCSServiceRoutines.createUploadPath(util);

		String id = util.getUserInfo().getIP() + ":" + user.getName();
		String access = user.getName();
		String owner = user.getName();
		String sourceID = QueryBase.getDataSourceIdentification(user.getName());
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		GCSBlobFileInformation source = new GCSBlobFileInformation(obj, bucket, path,
				filename, contentType, uploadDescriptionText);
		return source;
	}
*/
	public static InputStream getInputStream(GCSBlobFileInformation info) {
		GCSBlobContent content = getContent(info);
		String contentstring = content.getBytes();
		InputStream inputstream = new ByteArrayInputStream(contentstring.getBytes(StandardCharsets.UTF_8));
		/*
		 * BlobId id = BlobId.of(info.getBucket(), info.getGSFilename());
		 * System.out.println("getInputStream blobId info" + id.toString()); GcsFilename
		 * fileName = new GcsFilename(id.getBucket(), id.getName());
		 * 
		 * 
		 * //GcsFilename fileName = new GcsFilename(info.getBucket(),
		 * info.getGSFilename()); GcsInputChannel readChannel =
		 * gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE); InputStream
		 * inputstream = Channels.newInputStream(readChannel);
		 */
		return inputstream;
	}

	public static GCSBlobContent getContent(GCSBlobFileInformation gcsinfo) {
		BlobId blobId = BlobId.of(gcsinfo.getBucket(), gcsinfo.getGSFilename());
		Blob blob = storage.get(blobId);
		byte[] bytes = blob.getContent(BlobSourceOption.generationMatch());
		String bytesS = new String(bytes);
		String urlS = "https://storage.googleapis.com/" + gcsinfo.getBucket() + "/" + gcsinfo.getGSFilename();
		GCSBlobContent gcs = new GCSBlobContent(urlS, gcsinfo);
		gcs.setBytes(bytesS);
		return gcs;
	}

	@Override
	public String deleteFromStorage(String blobkey) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}