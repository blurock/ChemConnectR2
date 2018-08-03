package info.esblurock.reaction.core.server.db;

import java.util.Iterator;

import com.google.cloud.PageImpl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.BlobListOption;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.DatabaseObjectHierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;

public class GoogleCloudStorageBase {

	public static Storage storage = null;
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	public static HierarchyNode getBlobHierarchy(DatabaseObject obj, String bucketName, String directory) {
		HierarchyNode topnode = new HierarchyNode(directory);

		insertBlobHiearchyLevel(obj, topnode, bucketName, directory);
		
		return topnode;
	}
	public static void insertBlobHiearchyLevel(DatabaseObject obj, HierarchyNode node, String bucketName, String directory) {
		System.out.println("insertBlobHiearchyLevel: " + directory);
		PageImpl<Blob> blobs = (PageImpl<Blob>) UserImageServiceImpl.storage.list(bucketName,
				BlobListOption.currentDirectory(), BlobListOption.prefix(directory));
		Iterator<Blob> iter = blobs.iterateAll().iterator();
		while (iter.hasNext()) {
			Blob blob = iter.next();
			if(blob.getSize() > 0) {
				System.out.println("insertBlobHiearchyLevel: sub=" + blob.getName() + " with size=" + blob.getSize());
				GCSBlobFileInformation gcsinfo = ParseUtilities.blobInfoToGCSBlobFileInformation(obj, blob);
				DatabaseObjectHierarchyNode subnode = new DatabaseObjectHierarchyNode(gcsinfo,gcsinfo.getFilename());
				node.addSubNode(subnode);
			} else {
				System.out.println("insertBlobHiearchyLevel: sub=" + blob.getName() + " with size=" + blob.getSize());
				String subdir = directory;
				if(blob.getName().length() > directory.length()) {
					subdir = blob.getName().substring(directory.length());
				}
				DatabaseObjectHierarchyNode subnode = new DatabaseObjectHierarchyNode(null,subdir);				
				node.addSubNode(subnode);
				insertBlobHiearchyLevel(obj,subnode,bucketName,blob.getName());
			}
			
		}
		
	}
}
