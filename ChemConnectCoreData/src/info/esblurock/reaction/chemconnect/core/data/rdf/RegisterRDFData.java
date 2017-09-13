package info.esblurock.reaction.chemconnect.core.data.rdf;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;


public class RegisterRDFData {
	public static void register() {
		ObjectifyService.register(KeywordRDF.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(KeywordRDF.class);
	}
}
