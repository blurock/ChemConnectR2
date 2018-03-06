package info.esblurock.reaction.core.server.db.gcs;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.io.db.QueryBase;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
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
				//String filename = fileItem.getName().trim();
				Storage storage = StorageOptions.getDefaultInstance().getService();
				String uploadDescriptionText = "Uploaded File from FileUploadServlet";
				
				ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
				UserDTO user = util.getUserInfo();
				System.out.println("User: " + user);
				String outputSourceCode = QueryBase.getDataSourceIdentification(user.getName());
				String id = util.getUserInfo().getIP() + ":" + user.getName();
				String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + user.getName();

				GCSBlobFileInformation source = new GCSBlobFileInformation(id,outputSourceCode,
						GoogleCloudStorageConstants.uploadBucket,path,
						fileItem.getName(), fileItem.getContentType(),
						uploadDescriptionText);
				
				System.out.println("FileUploadServlet: " + source.toString());
				
				BlobInfo info = BlobInfo.newBuilder(source.getBucket(), source.getGSFilename())
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();

				System.out.println("FileUploadServlet: " + info.toString());
				@SuppressWarnings("deprecation")
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