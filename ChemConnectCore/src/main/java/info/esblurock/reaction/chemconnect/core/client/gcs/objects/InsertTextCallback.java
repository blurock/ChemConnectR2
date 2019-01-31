package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;

public class InsertTextCallback implements AsyncCallback<ArrayList<String>> {

	InsertBlobTextContentInterface top;
	public InsertTextCallback(InsertBlobTextContentInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: insert text" + arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<String> content) {
		MaterialLoader.loading(false);
		top.insertBlobContent(content);
	}

}
