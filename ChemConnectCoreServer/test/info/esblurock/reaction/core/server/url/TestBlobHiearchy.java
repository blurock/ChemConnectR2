package info.esblurock.reaction.core.server.url;

//import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;

public class TestBlobHiearchy {

	@Test
	public void test() {
		ArrayList<GCSBlobFileInformation> lst = new ArrayList<GCSBlobFileInformation>();
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("TopNode");
		GCSBlobFileInformation gcs1 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/upload/Administration/file1.jpg","image",
				"file1.jpg");
		lst.add(gcs1);
		GCSBlobFileInformation gcs2 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/upload/Administration/file2.xml","xml",
				"file1.xml");
		lst.add(gcs2);
		GCSBlobFileInformation gcs3 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/upload/Other/file1.jpg","image",
				"file1.jpg");
		lst.add(gcs3);
		GCSBlobFileInformation gcs4 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/upload/Other/file1.xsl","xsl",
				"file1.xsl");
		lst.add(gcs4);
		GCSBlobFileInformation gcs5 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/catalog/Administration/file1.jpg","image",
				"file1.jpg");
		lst.add(gcs5);
		GCSBlobFileInformation gcs6 = new GCSBlobFileInformation(obj,"","",
				"chemconnect/catalog/file1.xsl","xsl",
				"file1.jpg");
		lst.add(gcs6);
		ArrayList<String> types = null;
		HierarchyNode hierarchy = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		System.out.println(types);
		System.out.println(hierarchy);
		types = new ArrayList<String>();
		HierarchyNode hierarchy1 = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		System.out.println(types);
		System.out.println(hierarchy1);
		types.add("image");
		HierarchyNode hierarchy2 = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		System.out.println(types);
		System.out.println(hierarchy2);
		types.add("xsl");
		HierarchyNode hierarchy3 = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		System.out.println(types);
		System.out.println(hierarchy3);
	}

}
