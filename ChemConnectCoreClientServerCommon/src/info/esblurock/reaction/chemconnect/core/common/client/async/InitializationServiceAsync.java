package info.esblurock.reaction.chemconnect.core.common.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InitializationServiceAsync {

	void initializeDatabaseObjects(AsyncCallback<Void> callback);

	void clearDatabaseObjects(AsyncCallback<Void> callback);

}
