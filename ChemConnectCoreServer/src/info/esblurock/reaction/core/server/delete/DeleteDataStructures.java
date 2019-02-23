package info.esblurock.reaction.core.server.delete;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;

import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.data.image.ImageInformation;

public enum DeleteDataStructures {

	DatasetImage {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			DatasetImage image = (DatasetImage) getEntity(sourceClass,ID);
			String imageinfoid = image.getImageInformation();
			ImageInformation imageinfo = (ImageInformation) QueryBase
					.getDatabaseObjectFromIdentifier(ImageInformation.class.getCanonicalName(), imageinfoid);
			String imageurl = imageinfo.getImageURL();
			WriteReadDatabaseObjects.deleteBlobFromURL(imageurl);
			return null;
		}

	},
	SpreadSheetInputInformation {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) getEntity(sourceClass,ID);
			String sourceID = spread.getSourceID();

			List<ObservationValueRow> entities = ObjectifyService.ofy().load().type(ObservationValueRow.class)
					.filter("sourceID", sourceID).list();

			ObjectifyService.ofy().delete().entities(entities);
			return null;
		}

	},
	GCSBlobFileInformation {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			GCSBlobFileInformation gcsinfo = (GCSBlobFileInformation) getEntity(sourceClass,ID);
			UserImageServiceImpl.deleteBlob(gcsinfo);
			return null;
		}

	},
	GCSInputFileInterpretation {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			return null;
		}

	},
	ObservationCorrespondenceSpecification {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			return null;
		}

	},
	DatasetCatalogHierarchy {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			return null;
		}

	},
	ObservationBlockFromSpreadSheet {

		@Override
		public String deleteStructure(String sourceClass, String ID) throws IOException {
			return null;
		}

	};

	public abstract String deleteStructure(String sourceClass, String ID) throws IOException;

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

	public static String deleteObject(String datatype, String ID) throws IOException {
		DataElementInformation element = DatasetOntologyParsing.getSubElementStructureFromIDObject(datatype);
		String chemconnecttype = element.getChemconnectStructure();
		DeleteDataStructures deletedata = valueOf(chemconnecttype);
		String ans = "No special delete";
		if (deletedata != null) {
			DatabaseObject entity = getEntity(element.getChemconnectStructure(), ID);
			ans = deletedata.deleteStructure(chemconnecttype,ID);
		}
		return ans;
	}

	public static String deleteObject(DatabaseObject entity) throws IOException {
		String root = findKeyRoot(entity.getClass().getCanonicalName());
		DeleteDataStructures deletedata = valueOf(root);
		String ans = "No special delete";
		if (deletedata != null) {
			ans = valueOf(root).deleteStructure(entity.getClass().getCanonicalName(),entity.getIdentifier());
		}
		return ans;
	}

	public static DatabaseObject getEntity(String sourceClass, String ID) throws IOException {
		DatabaseObject entity = null;
		Class<?> typeclass;
		InterpretData interpret = InterpretData.valueOf(sourceClass);
		if (interpret != null) {
			interpret.canonicalClassName();
			try {
				typeclass = Class.forName(interpret.canonicalClassName());
			} catch (ClassNotFoundException e) {
				throw new IOException("Delete: Can't resolve source class: " + sourceClass);
			}
			entity = (DatabaseObject) ObjectifyService.ofy().load().type(typeclass)
					.filter("identifier", ID).first().now();
			if (entity == null) {
				throw new IOException("Entity not found: sourceClass: " + sourceClass + "  ID=" + ID);
			}
		} else {
			throw new IOException("No object associated with: " + sourceClass);
		}
		return entity;
	}

}
