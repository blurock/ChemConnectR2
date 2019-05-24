package info.esblurock.reaction.core.server.db.gcs;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.image.GCSServiceRoutines;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gwt.user.client.Cookies;

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
				
				InputStream in = fileItem.openStream();
				Storage storage = StorageOptions.getDefaultInstance().getService();
				String uploadDescriptionText = "Uploaded File from FileUploadServlet";
				ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), request.getSession());
				String path = GCSServiceRoutines.createUploadPath(util.getUserName());
				UserDTO user = util.getUserInfo();
				System.out.println("FileUploadServlet\n" + user);
				System.out.println("Name:        " + util.getUserName());
				System.out.println("ID:          " + util.getId());
				GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
						path, fileItem.getName(), 
						fileItem.getContentType(), 
						uploadDescriptionText,
						util.getId(),util.getUserName());
				
				BlobInfo info = BlobInfo.newBuilder(GCSServiceRoutines.getGCSStorageBucket(), source.getGSFilename())
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
						.setContentType(fileItem.getContentType())
						.build();
				@SuppressWarnings("deprecation")
				BlobInfo blobInfo = storage.create(info, in);
				System.out.println("FileUploadServlet: " + blobInfo.getMediaLink());
				DatabaseWriteBase.writeObjectWithTransaction(source);
			}
		} catch (Exception caught) {
			throw new RuntimeException(caught);
		}
	}
}