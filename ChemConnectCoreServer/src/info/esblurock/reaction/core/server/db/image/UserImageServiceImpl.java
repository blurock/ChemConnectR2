package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.apphosting.api.ApiProxy;
import com.google.auth.Credentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.BlobInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends RemoteServiceServlet implements UserImageService {

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
		GCSBlobFileInformation source = new GCSBlobFileInformation(
				GoogleCloudStorageConstants.uploadBucket,
				GoogleCloudStorageConstants.uploadPathPrefix,
				fileinfo.getFilename(), fileinfo.getFiletype(), fileinfo.getDescription());
		return moveBlob(fileinfo, source);
	}

	public GCSBlobContent moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source) {
        Storage storage = StorageOptions.getDefaultInstance().getService();

		String sourcefilename = source.getGSFilename();
		String sourcebucket = source.getBucket();
		String targetfilename = fileinfo.getGSFilename();
		String targetbucket = fileinfo.getBucket();

 		BlobId blobId = BlobId.of(sourcebucket, sourcefilename);
 		
		Blob blob = storage.get(blobId);
		CopyWriter copyWriter = blob.copyTo(BlobId.of(targetbucket, targetfilename));
		Blob copiedBlob = copyWriter.getResult();
		GCSBlobContent content = new GCSBlobContent(copiedBlob.getMediaLink(),fileinfo);
		DatabaseWriteBase.writeObjectWithTransaction(fileinfo);
		return content;
	}

}