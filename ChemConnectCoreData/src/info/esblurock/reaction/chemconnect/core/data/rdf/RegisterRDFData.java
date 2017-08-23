package info.esblurock.reaction.chemconnect.core.data.rdf;

import com.googlecode.objectify.ObjectifyService;


public class RegisterRDFData {
	public static void register() {
		ObjectifyService.register(KeywordRDF.class);
	}
}
