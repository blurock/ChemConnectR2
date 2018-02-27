package info.esblurock.reaction.core.server.db.image;

import java.io.IOException;
import java.util.ArrayList;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreFailureException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
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
	public static String keywordParameter = "keyWord";
	public static String blobkeyParameter = "blobKey";
	
    @Override
    public ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService) {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

        ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), null);
		UserDTO user = util.getUserInfo();
        System.out.println("User: " + user);

        String outputSourceCode = null;
        if(uploadService) {
			outputSourceCode = QueryBase.getDataSourceIdentification(user.getName()); 
        }		
        UploadOptions options = UploadOptions.Builder.withGoogleStorageBucketName(bucketName);
        String baseurl = uploadRoot + "?" + sourceFileParameter + "=" + outputSourceCode 
        		+ "&" + keywordNameParameter + "=" + keywordName;
        System.out.println("BaseURL: " + baseurl);
        String uploadUrl = blobstoreService.createUploadUrl(baseurl,options);
        System.out.println("uploadURL" + uploadUrl);

        if(uploadService) {
        		System.out.println("uploadService: write");
        		ImageUploadTransaction imageinfo = new ImageUploadTransaction(user.getName(),outputSourceCode, keywordName, bucketName, uploadUrl);
        		DatabaseWriteBase.writeObjectWithTransaction(imageinfo);
        } else {
        		System.out.println("uploadService: no write");
        }
		ImageServiceInformation returninfo = new ImageServiceInformation(user.getName(),outputSourceCode, keywordName, 
				bucketName, uploadUrl);
		
        return returninfo;
    }
 
    @Override
    public ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException {
    	ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();
    	
    	String keyword = serviceInfo.getKeyWord();
    	String user = serviceInfo.getUser();
    	
    	SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
    	values.add(fileCodeParameter,keyword);
    	values.add(userParameter,user);
    	String classname = UploadedImage.class.getName();
    	QuerySetupBase querybase = new QuerySetupBase(classname,values);
    	SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
    			ArrayList<DatabaseObject> objs = result.getResults();
    			for(DatabaseObject obj : objs) {
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
	values.add(keywordParameter,keyword);
	values.add(userParameter,user.getName());
    	String classname = UploadedImage.class.getName();
    	QuerySetupBase querybase = new QuerySetupBase(classname,values);
    	SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
		    ArrayList<DatabaseObject> objs = result.getResults();
	    	for(DatabaseObject obj : objs) {
	    		UploadedImage uploaded = (UploadedImage) obj;
	    		imagelst.add(uploaded);
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
    	
    	
    	QueryBase.deleteUsingPropertyValue(UploadedImage.class, blobkeyParameter, blobkey);
        try {
        	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        	BlobKey key = new BlobKey(blobkey);
        	blobstoreService.delete(key);
        } catch(BlobstoreFailureException ex) {
        	throw new IOException("Error in deleting: " + blobkey);
        }
    		
    	return ans;
    }
    @Override
    public String updateImages(ArrayList<UploadedImage> images) throws IOException {
    	String ans = "Successfully updated " + images.size() + " images ";
    	DatabaseWriteBase.writeListOfDatabaseObjects(images);
    	return ans;
    }
    
}