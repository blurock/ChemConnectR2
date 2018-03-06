package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InsertTextCallback implements AsyncCallback<ArrayList<String>> {

	InsertBlobTextContentInterface top;
	public InsertTextCallback(InsertBlobTextContentInterface top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<String> content) {
		top.insertBlobContent(content);
	}

}
