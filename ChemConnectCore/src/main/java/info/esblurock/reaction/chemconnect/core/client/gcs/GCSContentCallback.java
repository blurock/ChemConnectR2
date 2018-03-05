package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;

public class GCSContentCallback implements AsyncCallback<GCSBlobContent> {

	InsertBlobContentInterface top;
	
	public GCSContentCallback(InsertBlobContentInterface top) {
		this.top = top;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(GCSBlobContent insert) {
		top.insertBlobInformation(insert);
	}

}
