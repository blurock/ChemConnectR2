package info.esblurock.reaction.core.common.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InitializationServiceAsync {

	void initializeDatabaseObjects(AsyncCallback<Void> callback);

}
