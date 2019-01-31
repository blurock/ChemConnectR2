package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;

public class GCSContentCallback implements AsyncCallback<GCSBlobContent> {

	InsertBlobContentInterface top;
	
	public GCSContentCallback(InsertBlobContentInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: GCS content\n" + arg0.toString());
	}

	@Override
	public void onSuccess(GCSBlobContent insert) {
		MaterialLoader.loading(false);
		top.insertBlobInformation(insert);
	}

}
