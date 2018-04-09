package info.esblurock.reaction.core.server.services.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.ParsedFilename;

public class ParseUtilities {

	public static ParsedFilename getURLInformation(DatabaseObject obj, String url) throws IOException {
		ParsedFilename parsed = null;
		URL urlU = null;
		try {
			urlU = new URL(url);
			String filetype = urlU.openConnection().getContentType();
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
}
