package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadedFileCollapsible extends Composite {

	private static UploadedFileCollapsibleUiBinder uiBinder = GWT.create(UploadedFileCollapsibleUiBinder.class);

	interface UploadedFileCollapsibleUiBinder extends UiBinder<Widget, UploadedFileCollapsible> {
	}

	public UploadedFileCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink filename;
	@UiField
	MaterialLink filetype;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink delete;

	GCSBlobFileInformation gcsinfo;
	
	public UploadedFileCollapsible(GCSBlobFileInformation gcsinfo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.gcsinfo = gcsinfo;
		filename.setText(gcsinfo.getFilename());
		filetype.setText(gcsinfo.getFiletype());
	}

	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete!");
	}
	@UiHandler("info")
	void onClickInfo(ClickEvent e) {
		Window.alert("Info!");
	}

}
