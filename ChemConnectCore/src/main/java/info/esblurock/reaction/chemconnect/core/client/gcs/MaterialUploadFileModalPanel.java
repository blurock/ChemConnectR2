package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.client.events.DragOverEvent;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import info.esblurock.reaction.chemconnect.core.data.base.GoogleCloudStorageConstants;

public class MaterialUploadFileModalPanel extends Composite {

	private static MaterialUploadFileModalPanelUiBinder uiBinder = GWT
			.create(MaterialUploadFileModalPanelUiBinder.class);

	interface MaterialUploadFileModalPanelUiBinder extends UiBinder<Widget, MaterialUploadFileModalPanel> {
	}

	public MaterialUploadFileModalPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialFileUploader uploader;
	@UiField
	MaterialDialog modal;
	
	String rootID;
	MaterialPanel modalpanel;
	DetermineBlobTargetInterface top;

	public MaterialUploadFileModalPanel(String rootID,MaterialPanel modalpanel,DetermineBlobTargetInterface top) {
		initWidget(uiBinder.createAndBindUi(this));
		this.top = top;
		this.modalpanel = modalpanel;
		this.rootID = rootID;
		
		uploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				String name = event.getTarget().getName();
				String type = event.getTarget().getType();
				String bucket = GoogleCloudStorageConstants.storageBucket;
				String path =  GoogleCloudStorageConstants.observationsPathPrefix + "/" + type + "/" + rootID;
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
		modal.close();
		DetermineBlobTargetModal blob = new DetermineBlobTargetModal(top, rootID, type, bucket, path, name);
		modalpanel.clear();
		modalpanel.add(blob);
		blob.openModal();	
	}

	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}

}
