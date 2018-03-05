package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTextArea;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;

public class UploadedTextObject extends Composite implements InsertBlobTextContentInterface {

	private static UploadedTextObjectUiBinder uiBinder = GWT.create(UploadedTextObjectUiBinder.class);

	interface UploadedTextObjectUiBinder extends UiBinder<Widget, UploadedTextObject> {
	}


	@UiField
	MaterialTextArea textFile;
	@UiField
	MaterialTextArea description;

	GCSBlobContent blobContent;
	
	public UploadedTextObject() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public UploadedTextObject(GCSBlobContent content) {
		initWidget(uiBinder.createAndBindUi(this));
		this.blobContent = content;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		InsertTextCallback callback = new InsertTextCallback(this);
		async.getBlobContent(content, callback);
	}

	@Override
	public void insertBlobContent(String text) {
		Window.alert("insertBlobContent: \n");
		Window.alert("insertBlobContent: \n" + text.substring(0, 200));
		textFile.setText(text);
		
	}
}
