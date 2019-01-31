package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class ContentUploadCallback implements AsyncCallback<GCSBlobFileInformation> {

	UploadFileToBlobStorage top;
	
	public ContentUploadCallback(UploadFileToBlobStorage top) {
		this.top = top;
		MaterialLoader.loading(true);
		}

	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		MaterialToast.fireToast("Unable to upload file");
	}

	@Override
	public void onSuccess(GCSBlobFileInformation arg0) {
		MaterialLoader.loading(false);
		top.refresh();
	}

}
