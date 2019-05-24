package info.esblurock.reaction.core.server.db;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.core.server.db.image.BlobKeyCorrespondence;


public class RegisterServerDatabaseObjects {

	public static void register() {
		ObjectifyService.register(BlobKeyCorrespondence.class);
		
	}
}
