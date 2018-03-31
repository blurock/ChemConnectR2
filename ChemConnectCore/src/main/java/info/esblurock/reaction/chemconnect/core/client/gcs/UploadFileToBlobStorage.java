package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.UploadedFilesInterface;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadFileToBlobStorage extends Composite implements DetermineBlobTargetInterface, 
			InsertBlobContentInterface, UploadedFilesInterface {

	private static UploadFileToBlobStorageUiBinder uiBinder = GWT.create(UploadFileToBlobStorageUiBinder.class);

	interface UploadFileToBlobStorageUiBinder extends UiBinder<Widget, UploadFileToBlobStorage> {
	}

	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialLink textareaupload;
	@UiField
	MaterialTextArea textarea;
	@UiField
	MaterialTextBox httpaddress;
	@UiField
	MaterialLink httpupload;
	@UiField
	MaterialTooltip httptip;
	@UiField
	MaterialFileUploader uploader;
	
	DatabaseObject obj;
	
	public UploadFileToBlobStorage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	void init() {
		httpaddress.setText("http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls");
		httptip.setText("Click to upload from HTTP address");
		httpupload.setText("HTTP Upload");
		textareaupload.setText("Click to upload text area");
		title.setTitle("Upload Files");
		obj = new DatabaseObject();
		getUploadedFiles();
		
		uploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				collapsible.clear();
				getUploadedFiles();
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
	
	private void getUploadedFiles() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		UploadedFilesCallback callback = new UploadedFilesCallback(this,false);
		async.getUploadedFiles(callback);
		
	}

	public void setIdentifier(DatabaseObject obj) {
		this.obj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-suppinfo";
		this.obj.setIdentifier(id);
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
		coll.setIdentifier(obj.getIdentifier());
	}

	@Override
	public void addCollapsible(UploadedElementCollapsible coll) {
		collapsible.add(coll);
		
	}

	@Override
	public MaterialPanel getModalPanel() {
		return modalpanel;
	}

}
