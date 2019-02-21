package info.esblurock.reaction.core.server.delete;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;

import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.data.image.ImageInformation;

public enum DeleteDataStructures {

	DatasetImage {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			DatasetImage image = (DatasetImage) info;
			String imageinfoid = image.getImageInformation();
			ImageInformation imageinfo = (ImageInformation) QueryBase.getDatabaseObjectFromIdentifier(ImageInformation.class.getCanonicalName(), imageinfoid);
			String imageurl = imageinfo.getImageURL();
			WriteReadDatabaseObjects.deleteBlobFromURL(imageurl);
			return null;
		}
		
	}, SpreadSheetInputInformation {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) info;
			String sourceID = spread.getSourceID();

			List<ObservationValueRow> entities = ObjectifyService.ofy().load().type(ObservationValueRow.class).filter("sourceID",sourceID).list();
			
			ObjectifyService.ofy().delete().entities(entities);
			return null;
		}
		
	}, GCSBlobFileInformation {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			GCSBlobFileInformation gcsinfo = (GCSBlobFileInformation) info;
			UserImageServiceImpl.deleteBlob(gcsinfo);
			return null;
		}
		
	}, GCSInputFileInterpretation {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			return null;
		}
		
	}, ObservationCorrespondenceSpecification {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			return null;
		}
		
	}, DatasetCatalogHierarchy {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			return null;
		}
		
	}
	;
	
	
	
	public abstract String deleteStructure(DatabaseObject info) throws IOException;

	
	public void deleteFromBlobURL(String url) {
		
	}
	
	/**
	 * Find key root.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	protected static String findKeyRoot(String key) {
		StringTokenizer tok = new StringTokenizer(key, ".");
		String ans = "";
		while (tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}

	public static String deleteObject(DatabaseObject entity) throws IOException {
		String root = findKeyRoot(entity.getClass().getCanonicalName());
		DeleteDataStructures deletedata = valueOf(root);
		String ans = "No special delete";
		if(deletedata != null) {
			ans = valueOf(root).deleteStructure(entity);
		}
		return ans;
	}
	
}
