package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;

public class ResetDatabaseObjects {
	@SuppressWarnings("rawtypes")
	public static void resetClass(Class cls) {
		@SuppressWarnings("unchecked")
		List<Key<?>> keys = ObjectifyService.ofy().load().type(cls).keys().list();
		ObjectifyService.ofy().delete().keys(keys).now();
	}
	public static void clearDatabase() {
		RegisterContactData.reset();
		RegisterDescriptionData.reset();
		RegisterInitializationData.reset();
		RegisterRDFData.reset();
	}
}
