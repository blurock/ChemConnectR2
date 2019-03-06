package info.esblurock.reaction.chemconnect.core.data.methodology;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterProtocol {
	public static void register() {
		ObjectifyService.register(ProtocolObservationSource.class);
		ObjectifyService.register(ChemConnectProtocol.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(ProtocolObservationSource.class);
		ResetDatabaseObjects.resetClass(ChemConnectProtocol.class);
	}
}
