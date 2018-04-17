package info.esblurock.reaction.core.server.services.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class ParseUtilities {

	public static ParsedFilename getURLInformation(DatabaseObject obj, String url) throws IOException {
		ParsedFilename parsed = null;
		URL urlU = null;
		try {
			urlU = new URL(url);
			URLConnection connect = urlU.openConnection();
			String filetype = connect.getContentType();
			if(filetype == null) {
				filetype = URLConnection.guessContentTypeFromName(urlU.getPath());
			}
			int pos = filetype.indexOf(";");
			if(pos > 0) {
				filetype = filetype.substring(0,pos);
			}
			
			
			parsed = fillURLInformation(obj, urlU, filetype);
			
		} catch (MalformedURLException e) {
			throw new IOException("");
		}
		return parsed;
	}

	public static ParsedFilename fillURLInformation(DatabaseObject obj, URL url, String filetype) {

		String path = url.getPath();
		
		ParsedFilename parsed = fillFileInformation(obj, url.toString(), path, filetype);
		ParsedFilename urlparsed = new ParsedFilename(obj, url.getHost(), parsed);

		return urlparsed;
	}
	public static ParsedFilename fillFileInformation(DatabaseObject obj, String filename, String filetype) {

		ParsedFilename parsed = fillFileInformation(obj, filename, filename, filetype);

		return parsed;
	}

	public static ParsedFilename fillFileInformation(DatabaseObject obj, String original, String filepath,
			String filetype) {
		StringTokenizer tok = new StringTokenizer(filepath, "/");
		ArrayList<String> dir = new ArrayList<String>();
		String extension = "";

		String filename = null;
		while (tok.hasMoreTokens()) {
			filename = tok.nextToken();
			if(tok.hasMoreTokens()) {
				dir.add(filename);
			}
		}
		int pos = filename.lastIndexOf(".");
		String filenamebase = filename;
		if (pos > 0) {
			extension = filename.substring(pos + 1);
			filenamebase = filename.substring(0, pos);
		}
		ParsedFilename parsed = new ParsedFilename(obj, original, filetype, dir, filenamebase, extension);
		return parsed;
	}
	
	public static HierarchyNode getFileInterpretionChoices(ParsedFilename parsed) throws IOException {
		
		HierarchyNode top = new HierarchyNode("Interpret");
		if(parsed.getExtension() != null) {
			ArrayList<String> exttypes = DatasetOntologyParsing.typesWithExtension(parsed.getExtension());
			addHierachy(top, exttypes, "From Extension");
		}
		if(parsed.getFiletype() != null) {
			System.out.println("MIME types: " + parsed.getFiletype());
			ArrayList<String> filetypes = DatasetOntologyParsing.typesFromFileType(parsed.getFiletype());
			addHierachy(top, filetypes, "MIME Types");
		}
		return top;
	}
	
	static void  addHierachy(HierarchyNode top, ArrayList<String> lst, String name) {
		System.out.println("addHierachy: " + lst);
		HierarchyNode fromExtension = new HierarchyNode(name);
		boolean typefound = lst.size() > 0;
		for(String exttype : lst) {
			HierarchyNode hierarchy = DatasetOntologyParsing.findClassHierarchy(exttype);
			fromExtension.addSubNode(hierarchy);
		}
		if(typefound) {
			top.addSubNode(fromExtension);
		}
	}	
	
	
}
