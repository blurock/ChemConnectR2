package info.esblurock.reaction.core.server;

//import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.core.server.services.util.ParseUtilities;

public class URLTest {

	@Test
	public void test() {
		String url = "https://storage.cloud.google.com/chemconnect/upload/Administration/phi100.txt?_ga=2.47211054.-802839131.1514489990&_gac=1.83926763.1519827537.Cj0KCQiAw9nUBRCTARIsAG11eid0EkgS7Jer836dByHIuZsd2jbC8EIZDGZg7qQ5UYMbtI5ziY2Qpz8aArKDEALw_wcB";
		try {
			DatabaseObject obj = new DatabaseObject();
			obj.setIdentifier("identifier");
			ParsedFilename parsed = ParseUtilities.getURLInformation(obj, url);
			System.out.println(parsed.toString());
			
			System.out.println("Filename:      " + parsed.getFullFilename());
			System.out.println("Full Filename: " + parsed.getFullFilenameWithPath());
			
			String filename = "/storage.cloud.google.com/chemconnect/upload/Administration/phi100.txt";
			ParsedFilename fileparsed = ParseUtilities.fillFileInformation(obj, filename, "text/plain");
			System.out.println(fileparsed.toString());
			
			System.out.println("==========================================================");
			HierarchyNode hierarchy1 =  ParseUtilities.getFileInterpretionChoices(parsed);
			System.out.println(hierarchy1);
			
			System.out.println("==========================================================");
			HierarchyNode hierarchy2 =  ParseUtilities.getFileInterpretionChoices(fileparsed);
			System.out.println(hierarchy2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
