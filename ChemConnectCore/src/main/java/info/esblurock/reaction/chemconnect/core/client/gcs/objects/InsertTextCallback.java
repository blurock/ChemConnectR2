package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InsertTextCallback implements AsyncCallback<String> {

	InsertBlobTextContentInterface top;
	public InsertTextCallback(InsertBlobTextContentInterface top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(String content) {
		top.insertBlobContent(content);
	}

}
