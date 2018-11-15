package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;
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

	GCSBlobContent blobContent;
	ArrayList<String> totaltext;
	int showlines;
	int totalNumberOfLines;
	MaterialPanel modalpanel;
	
	public UploadedTextObject() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public UploadedTextObject(GCSBlobContent content,MaterialPanel modalpanel) {
		this.modalpanel = modalpanel;
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.blobContent = content;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		InsertTextCallback callback = new InsertTextCallback(this);
		async.getBlobAsLines(content, callback);
	}

	void init() {
		showlines = 10;
	}
	@Override
	public void insertBlobContent(ArrayList<String> text) {
		totalNumberOfLines = text.size();
		totaltext = text;
		setText();
	}
	void setText() {
		StringBuilder build = new StringBuilder();
		for(int i=0 ; i< totalNumberOfLines; i++) {
			build.append(totaltext.get(i));
			build.append("\n");
		}
		textFile.setText(build.toString());
	}
	public void updateData() {
		blobContent.setBytes(textFile.getText());
	}
}
