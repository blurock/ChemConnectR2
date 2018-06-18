package info.esblurock.reaction.chemconnect.core.data.description;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterDescriptionData {
	public static void register() {
		
		ObjectifyService.register(DataSetReference.class);
		ObjectifyService.register(DescriptionDataData.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(DataSetReference.class);
		ResetDatabaseObjects.resetClass(DescriptionDataData.class);
	}
}
