package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.CopyRequest;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.ontology.QueryBase;

public class GCSServiceRoutines {
	public static Storage storage = null;
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	static Long longGeneration = (long) 42.0;
	public static String createUploadPath(String username) {
		String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + username;
		return path;
	}

	public static GCSBlobFileInformation createInitialUploadInfo(String path, String filename, String contentType,
			String uploadDescriptionText, String ip, String username) {

		String id = ip + ":" + username;
		String access = username;
		String owner = username;
		String sourceID = QueryBase.getDataSourceIdentification(username);
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		GCSBlobFileInformation source = new GCSBlobFileInformation(obj, path,
				filename, contentType, uploadDescriptionText);
		return source;
	}
	public static void uploadFileBlob(String id, String bucket, 
			String ip, String username,
			String path, String filename, String contentType, String description, String contentS)
			throws IOException {
		GCSBlobFileInformation info = createInitialUploadInfo(
				path, filename, contentType, description, ip, username);
		System.out.println("uploadFileBlob: \n" + info.toString());
		String url = null;
		GCSBlobContent gcs = new GCSBlobContent(url, info);
		gcs.setBytes(contentS);
		writeBlobContent(gcs);
	}
	
	public static void writeBlobContent(GCSBlobContent gcs) throws IOException {
		GCSBlobFileInformation info = gcs.getInfo();
		String contentS = gcs.getBytes();
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(),info.getGSFilename());

		byte[] content = contentS.getBytes(StandardCharsets.UTF_8);
		
		BlobInfo blobInfo = BlobInfo
        .newBuilder(blobId)
        .setContentType(info.getFiletype())
        // Modify access list to allow all users with link to read file
        .setAcl(new ArrayList<>(Arrays.asList(
        		Acl.of(User.ofAllUsers(), Acl.Role.READER)
        		//,Acl.of(User.ofAllAuthenticatedUsers(), Acl.Role.OWNER)
        		))).build();

		//Acl.of(User.ofAllAuthenticatedUsers(), Acl.Role.OWNER)
	
		try (WriteChannel writer = storage.writer(blobInfo)) {
			writer.write(ByteBuffer.wrap(content, 0, content.length));
		} catch (Exception ex) {
			
			System.out.println("writeBlobContent: " + ex.toString());
			
			throw new IOException("Failure to write blob: " + info.getGSFilename()
					+ " with size " + contentS.length() + "bytes");
		}
		DatabaseWriteBase.writeObjectWithTransaction(gcs.getInfo());
	}

	public static GCSBlobContent moveBlob(GCSBlobFileInformation target, GCSBlobFileInformation source) throws IOException {
	    CopyRequest request = CopyRequest.newBuilder()
	        .setSource(BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), source.getGSFilename()))
	        .setTarget(BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), target.getGSFilename()))
	        .build();
	    Blob blob = storage.copy(request).getResult();
	    blob.createAcl(Acl.of(User.ofAllUsers(), Acl.Role.READER));

	    String url = "https://storage.googleapis.com/" + GCSServiceRoutines.getGCSStorageBucket() + "/" + target.getGSFilename();
		GCSBlobContent gcscontent = new GCSBlobContent(url, target);
		String sourceID = QueryBase.getDataSourceIdentification(target.getOwner());
		target.setSourceID(sourceID);
		WriteReadDatabaseObjects.deletePreviousBlobStorageMoves(target);
		DatabaseWriteBase.writeObjectWithTransaction(target);
		return gcscontent;
	}

public static String getGCSStorageBucket() throws IOException  {
	String bucket = "blurock-chemconnect.appspot.com";
	String hostname;
	try {
		hostname = InetAddress.getLocalHost().getHostName();
		System.out.println("getGCSStorageBucket(): '" + hostname + "'");
		if(!hostname.startsWith("blurock-chemconnect.appspot.com")) {
			bucket = "blurock-chemconnect-localhost";
		}
	} catch (UnknownHostException e) {
		throw new IOException("Cannot retrieve hostname for bucket assignment.. using localhost bucket");
	}
	return bucket;
	}
}
