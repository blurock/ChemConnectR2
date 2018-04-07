package info.esblurock.reaction.core.server.delete;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public enum DeleteDataStructures {

	SpreadSheetInputInformation {

		@Override
		public String deleteStructure(DatabaseObject info) throws IOException {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) info;
			String sourceID = spread.getSourceID();

			List<SpreadSheetRow> entities = ObjectifyService.ofy().load().type(SpreadSheetRow.class).filter("sourceID",sourceID).list();
			
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
		
	}
	;
	
	
	
	public abstract String deleteStructure(DatabaseObject info) throws IOException;

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
		System.out.println("DeleteDataStructure: " + root);
		String ans = valueOf(root).deleteStructure(entity);
		return ans;
	}
}
