package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.contact.RegisterContactData;
import info.esblurock.reaction.chemconnect.core.data.dataset.RegistrerDataset;
import info.esblurock.reaction.chemconnect.core.data.description.RegisterDescriptionData;
import info.esblurock.reaction.chemconnect.core.data.gcs.RegisterGCSClasses;
import info.esblurock.reaction.chemconnect.core.data.image.RegisterImageInformation;
import info.esblurock.reaction.chemconnect.core.data.initialization.RegisterInitializationData;
import info.esblurock.reaction.chemconnect.core.data.login.RegisterUserLoginData;
import info.esblurock.reaction.chemconnect.core.data.rdf.RegisterRDFData;
import info.esblurock.reaction.chemconnect.core.data.transaction.RegisterTransactionData;

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
		RegisterRDFData.reset();
		RegisterTransactionData.reset();
		RegistrerDataset.reset();
		RegisterUserLoginData.reset();
		RegisterImageInformation.reset();
		RegisterGCSClasses.reset();
	}
}
