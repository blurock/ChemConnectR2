package info.esblurock.reaction.chemconnect.core.client.pages.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WriteDatasetObjectHierarchyCallback  implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(Void arg0) {
		Window.alert("Successfull Write");
	}

}
