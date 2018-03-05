package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class DetermineBlobTargetModal extends Composite  {

	private static DetermineBlobTargetModalUiBinder uiBinder = GWT.create(DetermineBlobTargetModalUiBinder.class);

	interface DetermineBlobTargetModalUiBinder extends UiBinder<Widget, DetermineBlobTargetModal> {
	}


	@UiField
	MaterialModal modal;
	@UiField
	MaterialModalContent modalcontent;
	@UiField
	MaterialLink filetype;
	@UiField
	MaterialLink bucket;
	@UiField
	MaterialLink path;
	@UiField
	MaterialTextBox filename;
	@UiField
	MaterialTextArea textArea;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;

	DetermineBlobTargetInterface top;
	
	public DetermineBlobTargetModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public DetermineBlobTargetModal(DetermineBlobTargetInterface top, String filetype, String bucket, String path, String filename) {
		initWidget(uiBinder.createAndBindUi(this));
		this.top = top;
		init();
		this.filetype.setText(filetype);
		this.bucket.setText(bucket);
		this.path.setText(path);
		this.filename.setText(filename);
	}
	void init() {
		textArea.setLabel("Description of file");
		filename.setLabel("Filename");
		
	}
	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		Window.alert("onClickDone");
		modal.close();
		GCSBlobFileInformation fileinfo = new GCSBlobFileInformation(bucket.getText(), path.getText(), filename.getText(), 
				filetype.getText(), textArea.getText());
		Window.alert("onClickDone");
		top.handleTargetBlob(fileinfo);
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}

	public void openModal() {
		modal.open();
	}

}
