package info.esblurock.reaction.core.server.db.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transaction.TransactionInfo;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.core.server.services.ServerBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;
import info.esblurock.reaction.io.dataset.ReadWriteDatabaseCatalog;
import info.esblurock.reaction.io.db.QueryBase;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends ServerBase implements UserImageService {
	
	private static Storage storage = null;
	static {
	    storage = StorageOptions.getDefaultInstance().getService();
	  }

	/*
	private static final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
		      .initialRetryDelayMillis(10)
		      .retryMaxAttempts(10)
		      .totalRetryPeriodMillis(15000)
		      .build());
		      */
	//private static final int BUFFER_SIZE = 2 * 1024 * 1024;
	
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
		System.out.println("User: " + user);

		String outputSourceCode = null;
		if (uploadService) {
			outputSourceCode = QueryBase.getDataSourceIdentification(user.getName());
		}
		UploadOptions options = UploadOptions.Builder.withGoogleStorageBucketName(bucketName);
		String baseurl = uploadRoot + "?" + sourceFileParameter + "=" + outputSourceCode + "&" + keywordNameParameter
				+ "=" + keywordName;
		System.out.println("BaseURL: " + baseurl);
		String uploadUrl = blobstoreService.createUploadUrl(baseurl, options);
		System.out.println("uploadURL" + uploadUrl);

		if (uploadService) {
			System.out.println("uploadService: write");
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
		QuerySetupBase querybase = new QuerySetupBase(classname, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				UploadedImage uploaded = (UploadedImage) obj;
				imagelst.add(uploaded);
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}
		System.out.println("getUploadedImageSet 2 size: " + imagelst.size());
		return imagelst;
	}

	@Override
	public ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException {
		ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();

		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user.getName());

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
					System.out.println(imagelst);
				} else {
					System.out.println("getUploadedImageSetFromKeywordAndUser: not a UploadedImage: "
							+ obj.getClass().getCanonicalName());
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("Class not found: " + classname);
		}

		System.out.println("getUploadedImageSet Keyword: " + keyword + " size: " + imagelst.size());
		return imagelst;
	}

	@Override
	public String deleteFromStorage(String blobkey) throws IOException {
		String ans = "Successful";
/*
		System.out.println("deleteFromStorage: (" + blobkeyParameter + "): " + blobkey);
		QueryBase.deleteUsingPropertyValue(UploadedImage.class, blobkeyParameter, blobkey);
		try {
			String classS = BlobKeyCorrespondence.class.getCanonicalName();
			BlobKeyCorrespondence corr = (BlobKeyCorrespondence) QueryBase
					.getFirstDatabaseObjectsFromSingleProperty(classS, keyAsStringParameter, blobkey);
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey key = corr.getBlobKey();
			BlobInfoFactory infofactory = new BlobInfoFactory();
			BlobInfo info = infofactory.loadBlobInfo(key);
			QueryBase.deleteObject(info);
			blobstoreService.delete(key);
			QueryBase.deleteObject(corr);
		} catch (BlobstoreFailureException ex) {
			throw new IOException("Error in deleting: " + blobkey);
		}
*/
		return ans;
	}

	@Override
	public String updateImages(ArrayList<UploadedImage> images) throws IOException {
		String ans = "Successfully updated " + images.size() + " images ";
		DatabaseWriteBase.writeListOfDatabaseObjects(images);
		return ans;
	}

	public GCSBlobContent moveBlobFromUpload(GCSBlobFileInformation fileinfo) {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user);

		String path = createUploadPath(util);
		
		String id = fileinfo.getIdentifier();
		String access = user.getName();
		String owner = user.getName();
		String sourceID = QueryBase.getDataSourceIdentification(user.getName());
		DatabaseObject obj = new DatabaseObject(id, access,owner,sourceID);
		
		GCSBlobFileInformation source = new GCSBlobFileInformation(obj,
				GoogleCloudStorageConstants.uploadBucket, path,
				fileinfo.getFilename(), fileinfo.getFiletype(), fileinfo.getDescription());
		
		fileinfo.setSourceID(source.getSourceID());
		
		System.out.println("moveBlobFromUpload: " + source.toString());
		System.out.println("moveBlobFromUpload: " + fileinfo.toString());
		
		return moveBlob(fileinfo, source);
	}

	public GCSBlobContent moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        
        
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user);

		String sourcefilename =   source.getGSFilename();
		String sourcebucket = source.getBucket();
		String targetfilename = fileinfo.getGSFilename();
		String targetbucket = fileinfo.getBucket();

		System.out.println("moveBlob: " + sourcebucket);
		System.out.println("moveBlob: " + sourcefilename);
		System.out.println("moveBlob: " + targetbucket);
		System.out.println("moveBlob: " + targetfilename);
		
 		BlobId blobId = BlobId.of(sourcebucket, sourcefilename);
		System.out.println("moveBlob: " + blobId);
		
		
		Blob blob = storage.get(blobId);
		
		CopyWriter copyWriter = blob.copyTo(BlobId.of(targetbucket, targetfilename));
		
		
		Blob copiedBlob = copyWriter.getResult();
		GCSBlobContent content = new GCSBlobContent(copiedBlob.getMediaLink(),fileinfo);
		DatabaseWriteBase.writeObjectWithTransaction(fileinfo);
		return content;
	}

	
	public GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo) {
		return getContent(gcsinfo);
	}
	
	public ArrayList<String> getBlobAsLines(GCSBlobContent info) {
		GCSBlobContent gcs = getBlobContent(info.getInfo());
		String text = gcs.getBytes();
		ArrayList<String> lines = new ArrayList<String>();
		Scanner tok = new Scanner(text);
		while(tok.hasNextLine()) {
			lines.add(tok.nextLine());
		}
		tok.close();
		return lines;
	}

	public void uploadFileBlob(String id, String filename, String contentType, String description, String contentS) throws IOException {
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		GCSBlobFileInformation info = createInitialUploadInfo(filename,contentType,description,util);
		String url = null;
		GCSBlobContent gcs = new GCSBlobContent(url, info);
		writeBlobContent(gcs);
	}
	
	public void writeBlobContent(GCSBlobContent gcs) throws IOException {
		GCSBlobFileInformation info = gcs.getInfo();
		String contentS = gcs.getBytes();
		 BlobId blobId = BlobId.of(info.getBucket(), info.getGSFilename());
		 
		 byte[] content = contentS.getBytes(StandardCharsets.UTF_8);
		 BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		 try (WriteChannel writer = storage.writer(blobInfo)) {
		     writer.write(ByteBuffer.wrap(content, 0, content.length));
		   } catch (Exception ex) {
		     throw new IOException("Failure to write blob: " + info.getBucket() + ": " + info.getGSFilename()
		    		 + " with size " + contentS.length() + "bytes");
		   }
			DatabaseWriteBase.writeObjectWithTransaction(gcs.getInfo());
		 }

	public void deleteUploadedFile(GCSBlobFileInformation gcsinfo) {
		deleteBlob(gcsinfo);
	}
	
	public void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset) {
		for(GCSBlobFileInformation info : fileset) {
			deleteUploadedFile(info);
		}
	}
	
	public ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException {
		SingleQueryResult result = null;
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		ContextAndSessionUtilities context = getUtilities();
		UserDTO user = context.getUserInfo();
		String username = user.getName();
		values.add("owner",username);
		values.add("bucket",GoogleCloudStorageConstants.uploadBucket);
		System.out.println("getUploadedFiles()");
		QuerySetupBase query = new QuerySetupBase(GCSBlobFileInformation.class.getCanonicalName(),values);
		try {
			result = QueryBase.StandardQueryResult(query);
		} catch (ClassNotFoundException e) {
			throw new IOException("Class Not found: " + GCSBlobFileInformation.class.getCanonicalName());
		}
		ArrayList<GCSBlobFileInformation> fileset = new ArrayList<GCSBlobFileInformation>();
		for(DatabaseObject obj : result.getResults()) {
			fileset.add((GCSBlobFileInformation) obj);
		}
		return fileset;
	}

	public GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException {
		ContextAndSessionUtilities context = getUtilities();
		String path = createUploadPath(context);
		System.out.println("Path: " + path);
		//Storage storage = StorageOptions.getDefaultInstance().getService();
		String uploadDescriptionText = "Uploaded File from URL";
		
		URL urlconnect = new URL(requestUrl);
		URLConnection c = urlconnect.openConnection();
		String contentType = c.getContentType();

		URL urlstream = new URL(requestUrl);
		InputStream in = urlstream.openStream();
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);

		
		
		GCSBlobFileInformation source = createInitialUploadInfo(requestUrl, contentType, uploadDescriptionText, util);
		retrieveContentFromStream(in,source);
		
		return source;
	}

	public GCSBlobFileInformation retrieveBlobFromContent(String filename, String content) throws IOException {
		String contentType = "text/plain";
		String uploadDescriptionText = "Uploaded File from text input";
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		GCSBlobFileInformation source = createInitialUploadInfo(filename, contentType, uploadDescriptionText, util);
		
		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		retrieveContentFromStream(in,source);
		return source;
	}


	
	public void deleteTransaction(String sourceID) throws IOException {
		deleteTransactionFromSourceID(sourceID);
	}

	
	private void retrieveContentFromStream(InputStream in, GCSBlobFileInformation source) {
		BlobInfo info = BlobInfo.newBuilder(source.getBucket(), source.getGSFilename())
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
				.setContentType(source.getFiletype())
				.build();

		@SuppressWarnings("deprecation")
		BlobInfo blobInfo = storage.create(info, in);
		System.out.println("Blob content type:  " + blobInfo.getContentType());
		DatabaseWriteBase.writeObjectWithTransaction(source);
		
	}
	
	public TransferDatabaseCatalogHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException {
		return ReadWriteDatabaseCatalog.getUserDatasetCatalogHierarchy(username);
	}
	
	public DatabaseObjectHierarchy getNewCatalogHierarchy(DatabaseObject obj, String id, String onelinedescription) throws IOException {
		System.out.println("getNewCatalogHierarchy: " + obj.toString());
		String classname = DatasetCatalogHierarchy.class.getCanonicalName();
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) 
				QueryBase.getDatabaseObjectFromIdentifier(classname, obj.getIdentifier());
		DatabaseObjectHierarchy subs = CreateDefaultObjectsFactory.createCatalogHierarchy(catalog,obj,id,onelinedescription);
		WriteReadDatabaseObjects.writeDatabaseObjectHierarchy(subs);	
		DatabaseWriteBase.writeDatabaseObject(catalog);
		return subs;
	}
	
	public HierarchyNode getFileInterpretionChoices(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = parseFilename(info);
		return ParseUtilities.getFileInterpretionChoices(parsed);
	}
	
	public static ParsedFilename parseFilename(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = ParseUtilities.fillFileInformation(info,info.getFilename(),info.getFiletype());
		return parsed;
	}

	public static void deleteTransactionFromSourceID(String sourceID) throws IOException {
		TransactionInfo info = (TransactionInfo) 
				QueryBase.getFirstDatabaseObjectsFromSingleProperty(TransactionInfo.class.getCanonicalName(), 
				"sourceID", sourceID);		
		DatabaseWriteBase.deleteTransactionInfo(info);
	}

	
	public static void deleteBlob(GCSBlobFileInformation gcsinfo) {
		System.out.println("deleteBlob: " + gcsinfo.toString());
 		BlobId blobId = BlobId.of(gcsinfo.getBucket(), gcsinfo.getGSFilename()); 		
		storage.delete(blobId);
	}
	
	public static String createUploadPath(ContextAndSessionUtilities util) {
		String username = util.getUserName();
		String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + username;
		return path;
	}
	public static GCSBlobFileInformation createInitialUploadInfo(String filename, String contentType, String uploadDescriptionText, ContextAndSessionUtilities util) {
		UserDTO user = util.getUserInfo();
		System.out.println("User: " + user);
		
		String path = createUploadPath(util);

		String id = util.getUserInfo().getIP() + ":" + user.getName();
		String access = user.getName();
		String owner = user.getName();
		String sourceID = QueryBase.getDataSourceIdentification(user.getName());
		DatabaseObject obj = new DatabaseObject(id, access,owner,sourceID);
		GCSBlobFileInformation source = new GCSBlobFileInformation(obj,
				GoogleCloudStorageConstants.uploadBucket,path,
				filename, contentType,
				uploadDescriptionText);
		return source;
	}
	
	
	public static InputStream getInputStream(GCSBlobFileInformation info) {
		System.out.println("getInputStream from string" + info.getBucket() + ":  " + info.getGSFilename());
		GCSBlobContent content = getContent(info);
		String contentstring = content.getBytes();
		System.out.println("getInputStream: " + contentstring.substring(0, 100));
		InputStream inputstream = new ByteArrayInputStream(contentstring.getBytes(StandardCharsets.UTF_8));
/*
		BlobId id = BlobId.of(info.getBucket(), info.getGSFilename());
		System.out.println("getInputStream blobId info" + id.toString());
		GcsFilename fileName = new GcsFilename(id.getBucket(), id.getName());
		
		
		//GcsFilename fileName = new GcsFilename(info.getBucket(), info.getGSFilename());
	    GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
	    InputStream inputstream = Channels.newInputStream(readChannel);
	    */
		return inputstream;
	}
	
	public static GCSBlobContent getContent(GCSBlobFileInformation gcsinfo) {
 		BlobId blobId = BlobId.of(gcsinfo.getBucket(), gcsinfo.getGSFilename()); 		
		Blob blob = storage.get(blobId);
		byte[] bytes = blob.getContent(BlobSourceOption.generationMatch());
		String bytesS = new String(bytes);
		System.out.println("getContent : " + bytesS.length());
		BlobInfo info = BlobInfo.newBuilder(gcsinfo.getBucket(), gcsinfo.getGSFilename())
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();
		String urlS = info.getMediaLink();
		//URL url = storage.signUrl(info, 2, TimeUnit.DAYS, SignUrlOption.);
		GCSBlobContent gcs = new GCSBlobContent(urlS,gcsinfo);
		gcs.setBytes(bytesS);
		return gcs;
	}
	

	
}