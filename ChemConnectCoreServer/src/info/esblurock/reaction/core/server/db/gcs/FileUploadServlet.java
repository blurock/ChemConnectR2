package info.esblurock.reaction.core.server.db.gcs;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobId;

import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a multipart request");
			return;
		}

		ServletFileUpload upload = new ServletFileUpload(); // from Commons

		try {
			FileItemIterator iter = upload.getItemIterator(request);

			if (iter.hasNext()) {
				FileItemStream fileItem = iter.next();

				// ServletOutputStream out = response.getOutputStream();
				// response.setBufferSize(32768);

				InputStream in = fileItem.openStream();
				String filename = fileItem.getName().trim();
				Storage storage = StorageOptions.getDefaultInstance().getService();
				String uploadDescriptionText = "Uploaded File from FileUploadServlet";
				GCSBlobFileInformation source = new GCSBlobFileInformation(GoogleCloudStorageConstants.uploadBucket,
						GoogleCloudStorageConstants.uploadPathPrefix, fileItem.getName(), fileItem.getContentType(),
						uploadDescriptionText);

				System.out.println("Filename:  " + filename);
				System.out.println("Filename:  " + fileItem.getName());
				System.out.println("Filename:  " + fileItem.getContentType());

				BlobInfo info = BlobInfo.newBuilder(source.getBucket(), source.getGSFilename())
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();
				/*
				 * BlobId blobId = BlobId.of(source.getBucket(), source.getGSFilename()); byte[]
				 * content = "Hello, World!".getBytes(UTF_8); BlobInfo blobInfo =
				 * BlobInfo.newBuilder(blobId).setContentType("text/plain").build(); try
				 * (WriteChannel writer = storage.writer(blobInfo)) { try { writer.write(src)
				 * writer.write(ByteBuffer.wrap(content, 0, content.length)); } catch (Exception
				 * ex) { // handle exception }
				 */
				BlobInfo blobInfo = storage.create(info, in);
				String url = blobInfo.getMediaLink();
				System.out.println("Blob url:  " + url);
				DatabaseWriteBase.writeObjectWithTransaction(source);
			}
		} catch (Exception caught) {
			throw new RuntimeException(caught);
		}
	}
}