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
	MaterialLink save;
	@UiField
	MaterialFileUploader uploader;
	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialLink lblName;
	@UiField
	MaterialLink lblType;
	@UiField
	MaterialLink lblSize;
	@UiField
	MaterialLink uploadButton;
	@UiField
	MaterialLink identifier;
	@UiField
	MaterialTooltip identifiertooltip;
	@UiField
	MaterialTooltip nametooltip;
	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialTooltip sizetooltip;
	@UiField
	MaterialTooltip uploadtooltip;
	
	String rootID;
	MaterialPanel modalpanel;
	
	public UploadFileToGCS(	MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		init();
		uploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				String name = event.getTarget().getName();
				String type = event.getTarget().getType();
				
				String sizeS = String.valueOf(event.getTarget().getFileSize());
				
				lblName.setText(name);
				lblType.setText(type);
				lblSize.setText(sizeS);
				
				String bucket = GoogleCloudStorageConstants.storageBucket;
				String path =  GoogleCloudStorageConstants.observationsPathPrefix + "/" + type + "/" + identifier.getText();
				storeTarget(type,bucket,path,name);
				
				}
			 });		
		
		uploader.addDragOverHandler(new DragOverEvent.DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				MaterialAnimation animate = new MaterialAnimation(uploader);
				animate.animate();
				}
			});
	}
	
	void storeTarget(String type, String bucket, String path, String name) {
		DetermineBlobTargetModal blob = new DetermineBlobTargetModal(this, identifier.getText(), type, bucket, path, name);
		modalpanel.clear();
		modalpanel.add(blob);
		blob.openModal();	
	}
	
	void init() {
		resetForm();
		specification.setText("Supplementary Material");
		identifiertooltip.setText("Root identifier");
		nametooltip.setText("Uploaded Filename");
		typetooltip.setText("Uploaded file type");
		sizetooltip.setText("Uploaded file size (MB)");
		uploadtooltip.setText("Click to save supplementary information");
	}
	
	void resetForm() {
		uploadButton.setText("Upload");
		lblName.setText("No file yet");
		lblType.setText("filetype");
		lblSize.setText("0");
	}
	
	@UiHandler("uploadButton")
	void onClickUploadButton(ClickEvent event) {
		/*
		String name = lblName.getText();
		String type = lblType.getText();
		String bucket = GoogleCloudStorageConstants.storageBucket;
		String path =  GoogleCloudStorageConstants.observationsPathPrefix + "/" + type + "/" + identifier.getText();
		
		DetermineBlobTargetModal blob = new DetermineBlobTargetModal(this, identifier.getText(), type, bucket, path, name);
		modalpanel.clear();
		modalpanel.add(blob);
		blob.openModal();
		*/
	}
	
	
	public void setIdentifier(String id) {
		rootID = id;
		identifier.setText(id + "-suppinfo");
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
		UploadedElementCollapsible coll = new UploadedElementCollapsible(insert);
		collapsible.add(coll);
		coll.setIdentifier(identifier.getText());
	}
	
}
