package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterMethodology {
	public static void register() {
		ObjectifyService.register(ChemConnectMethodology.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(ChemConnectMethodology.class);
	}
}
