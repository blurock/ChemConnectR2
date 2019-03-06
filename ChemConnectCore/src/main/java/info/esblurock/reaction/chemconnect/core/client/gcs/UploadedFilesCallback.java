package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.UploadedFilesInterface;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadedFilesCallback implements AsyncCallback<ArrayList<GCSBlobFileInformation>> {

	UploadedFilesInterface top;
	boolean rows;
	
	public UploadedFilesCallback(UploadedFilesInterface top, boolean rows) {
		this.top = top;
		this.rows = rows;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable ex) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Uploaded files\n" + ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<GCSBlobFileInformation> results) {
		MaterialLoader.loading(false);
		for(GCSBlobFileInformation gcsinfo: results) {
			if(!rows) {
				String urlS = getBlobURL(gcsinfo);	
				GCSBlobContent content = new GCSBlobContent(urlS, gcsinfo);
				UploadedElementCollapsible coll = new UploadedElementCollapsible(content,top.getModalPanel());
				top.addCollapsible(coll);
			} else {
				Window.alert("Rows not implemented yet");
			}
		}
	}

	private String getBlobURL(GCSBlobFileInformation gcsinfo) {
		String bucket = "blurock-chemconnect.appspot.com";
		String host = Window.Location.getHostName();
		if(host.startsWith("localhost")) {
			bucket = "blurock-chemconnect-localhost";
		}
		String urlS = "https://storage.googleapis.com/" + bucket + "/" + gcsinfo.getGSFilename();		
		Window.alert("URL: " + urlS);
		return urlS;
	}
}
