package info.esblurock.reaction.chemconnect.core.data.transaction;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;
import info.esblurock.reaction.chemconnect.core.data.transaction.dataset.DataStructureToDatabase;

public class RegisterTransactionData {
	public static void register() {
		ObjectifyService.register(TransactionInfo.class);
		ObjectifyService.register(DataSourceIdentification.class);
		ObjectifyService.register(DataStructureToDatabase.class);
		ObjectifyService.register(SessionEvent.class);
		ObjectifyService.register(EventCount.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(TransactionInfo.class);
		ResetDatabaseObjects.resetClass(DataSourceIdentification.class);
		ResetDatabaseObjects.resetClass(DataStructureToDatabase.class);
		ResetDatabaseObjects.resetClass(SessionEvent.class);
		ResetDatabaseObjects.resetClass(EventCount.class);
	}	
}
