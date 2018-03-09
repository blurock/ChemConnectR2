package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.client.events.DragOverEvent;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import gwt.material.design.client.ui.animate.MaterialAnimation;;


public class UploadFileToGCS extends Composite implements DetermineBlobTargetInterface, InsertBlobContentInterface {

	private static UploadFileToGCSUiBinder uiBinder = GWT.create(UploadFileToGCSUiBinder.class);

	interface UploadFileToGCSUiBinder extends UiBinder<Widget, UploadFileToGCS> {
	}

	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialLink specification;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink upload;
	@UiField
	MaterialCollapsible collapsible;
	
	String rootID;
	String identifier;
	MaterialPanel modalpanel;
	
	public UploadFileToGCS(	MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		init();
	}
	void init() {
		specification.setText("Supplementary Material");
	}
	
	@UiHandler("upload")
	void onClickCloudUploadButton(ClickEvent event) {
		MaterialUploadFileModalPanel modal = new MaterialUploadFileModalPanel(identifier, modalpanel, this);
		modalpanel.clear();
		modalpanel.add(modal);
		modal.open();
	}
	@UiHandler("delete")
	void onClickDelete(ClickEvent event) {
		
	}
	public void setIdentifier(String id) {
		rootID = id;
		identifier= id + "-suppinfo";
	}

	@Override
	public void handleTargetBlob(GCSBlobFileInformation fileinfo) {
		Window.alert(fileinfo.toString());
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		GCSContentCallback callback = new GCSContentCallback(this);
		async.moveBlobFromUpload(fileinfo,callback);
		
	}

	@Override
	public void insertBlobInformation(GCSBlobContent insert) {
		UploadedElementCollapsible coll = new UploadedElementCollapsible(insert,modalpanel);
		collapsible.add(coll);
		coll.setIdentifier(identifier);
	}
	
}
