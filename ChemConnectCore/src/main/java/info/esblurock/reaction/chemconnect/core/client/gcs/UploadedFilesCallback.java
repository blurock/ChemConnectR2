package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.UploadedFilesInterface;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadedFilesCallback implements AsyncCallback<ArrayList<GCSBlobFileInformation>> {

	UploadedFilesInterface top;
	boolean rows;
	
	public UploadedFilesCallback(UploadedFilesInterface top, boolean rows) {
		this.top = top;
		this.rows = rows;
	}
	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<GCSBlobFileInformation> results) {
		for(GCSBlobFileInformation gcsinfo: results) {
			if(!rows) {
				String urlS = "https://storage.googleapis.com/" + gcsinfo.getBucket() + "/" + gcsinfo.getGSFilename();				
				GCSBlobContent content = new GCSBlobContent(urlS, gcsinfo);
				UploadedElementCollapsible coll = new UploadedElementCollapsible(content,top.getModalPanel());
				top.addCollapsible(coll);
			} else {
				Window.alert("Rows not implemented yet");
			}
		}
	}

}
