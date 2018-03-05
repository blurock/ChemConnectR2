package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;

/**
 * Servlet implementation class DeleteBlobInformationServlet
 */
@WebServlet("/deleteblob")
public class DeleteBlobInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBlobInformationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost: keyword=" + req.getParameter(UserImageService.keywordNameParameter));
		/*
		String blobkey = req.getParameter(UserImageService.keywordNameParameter);
		String classS = BlobKeyCorrespondence.class.getCanonicalName();
		BlobKeyCorrespondence corr = (BlobKeyCorrespondence) QueryBase.getFirstDatabaseObjectsFromSingleProperty(classS, "blobKey", blobkey);
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		BlobKey key = corr.getBlobKey();
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
