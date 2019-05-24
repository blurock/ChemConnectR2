package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.data.login.UserDTO;
import info.esblurock.reaction.core.server.db.gcs.StoreObject;
import info.esblurock.reaction.core.server.services.util.ContextAndSessionUtilities;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;

/**
 * @author Ikai Lan
 * 
 *         This is the servlet that handles the callback after the blobstore
 *         upload has completed. After the blobstore handler completes, it POSTs
 *         to the callback URL, which must return a redirect. We redirect to the
 *         GET portion of this servlet which sends back a key. GWT needs this
 *         Key to make another request to get the image serving URL. This adds
 *         an extra request, but the reason we do this is so that GWT has a Key
 *         to work with to manage the Image object. Note the content-type. We
 *         *need* to set this to get this to work. On the GWT side, we'll take
 *         this and show the image that was uploaded.
 * 
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(UploadServlet.class.getName());

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("UploadServlet: doPost");
		System.out.println("doPost: source =" + req.getParameter(UserImageService.sourceFileParameter));
		System.out.println("doPost: keyword=" + req.getParameter(UserImageService.keywordNameParameter));
		System.out.println("doPost: URI: " + req.getRequestURI());
		
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKey = blobs.get("image");
		Map<String,List<FileInfo>> mapinfos = blobstoreService.getFileInfos(req);
		List<FileInfo> infos = mapinfos.get("image");
		
		String fileCode = req.getParameter(UserImageService.sourceFileParameter);
		String keyword = req.getParameter(UserImageService.keywordNameParameter);
		
		System.out.println("Source: " + fileCode + ", KeywordName: " + keyword);
		
		HttpSession session = req.getSession();
		ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), session);
		UserDTO user = util.getUserInfo();
		
		if (blobKey == null) {
			System.out.println("blobkey is null");
		} else {

			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Iterator<FileInfo> infoiter = infos.iterator();
			StoreObject store = new StoreObject(keyword,"Public",user.getName(),fileCode);
			for (BlobKey key : blobKey) {
				FileInfo fileinfo = infoiter.next();
				ServingUrlOptions options = ServingUrlOptions.Builder.withGoogleStorageFileName(fileinfo.getGsObjectName());
				String imageUrl = imagesService.getServingUrl(options);

				System.out.println("KeywordName: "+ keyword);
				
				String description = keyword + "\n" + fileinfo.getFilename() + "\n" + user.getName() + "\n";
				UploadedImage uploadedImage = new UploadedImage(user.getName(),fileCode,keyword, 
						key.getKeyString(), imageUrl,fileinfo.getFilename(), description);
				store.store(uploadedImage);
				BlobKeyCorrespondence corr = new BlobKeyCorrespondence(key.getKeyString(),key);
				store.store(corr);
				String url = "/upload?imageUrl=" + imageUrl;
				res.setHeader("Content-Type", "text/html");
				res.sendRedirect(url);
			}
			store.finish();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("UploadServlet doGet: ");
		String imageUrl = req.getParameter("imageUrl");
    	resp.setHeader("Content-Type", "text/html");
    	System.out.println("doGet  : " + imageUrl);
    	
    	// This is a bit hacky, but it'll work. We'll use this key in an Async service to
    	// fetch the image and image information
    	resp.getWriter().println(imageUrl);
		
        //BlobKey blobKey = new BlobKey(imageUrl);
        //blobstoreService.serve(blobKey, resp);
	}
}