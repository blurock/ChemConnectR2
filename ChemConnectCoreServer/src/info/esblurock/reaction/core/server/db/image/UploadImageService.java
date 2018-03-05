package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UploadImageService  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Your upload handle would look like
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("UploadImageService do Post");
		try {
	    // Get the image representation
	    //ServletFileUpload upload = new ServletFileUpload();
	    //FileItemIterator iter = upload.getItemIterator(req);
	    //FileItemStream imageItem = iter.next();
	    //InputStream imgStream = imageItem.openStream();

	    
	    // construct our entity objects
	    //Blob imageBlob = new Blob(IOUtils.toByteArray(imgStream));
	    //BlobKeyCorrespondence myImage = new BlobKeyCorrespondence(imageItem.getName(), imageBlob);
	    //DatabaseWriteBase.writeDatabaseObject(myImage);
	    // respond to query
	    res.setContentType("text/plain");
	    res.getOutputStream().write("OK!".getBytes());
		} catch(IOException ex) {
			
		}
	}
}
