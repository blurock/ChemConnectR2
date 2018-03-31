package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.resources.ImageResources;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;

public class UploadPhoto extends Composite implements HasText, BlobStorageUploadInterface {

	private static UploadPhotoUiBinder uiBinder = GWT.create(UploadPhotoUiBinder.class);

	interface UploadPhotoUiBinder extends UiBinder<Widget, UploadPhoto> {
	}

	UserImageServiceAsync userImageService = GWT.create(UserImageService.class);
	ImageResources images = GWT.create(ImageResources.class);

	@UiField
	Button uploadButton;
	@UiField
	FormPanel uploadForm;
	@UiField
	FileUpload uploadField;
	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink save;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialImage imgPreview;
	
	String imgPreviewS;
	String keywordName;
	ImageServiceInformation serviceInformation;
	
	public UploadPhoto() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public UploadPhoto(String keywordName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.keywordName = keywordName;
		init();
	}
	
	void init() {
		resetUpload();
		uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				resetUpload();
				Window.alert("onSubmitComplete: " + event.getResults());
				imgPreview.setUrl(event.getResults());
				refreshPictures();
				startNewBlobstoreSession(true);
			}
		});
	}
	
	void resetUpload() {
		uploadForm.reset();
		imgPreview.setResource(images.SetOfKeysSmall());
		imgPreviewS = images.SetOfKeysSmall().toString();
	}
	
	public void startNewBlobstoreSession(boolean uploadService) {
		ImageServiceCallback callback = new ImageServiceCallback(uploadService, this);
		userImageService.getBlobstoreUploadUrl(keywordName, uploadService, callback);
	}

	public void fillUpload(ImageServiceInformation result) {
		uploadButton.setText("Upload");
		uploadButton.setEnabled(true);
		uploadField.setName("image");
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		Window.alert(result.toString("fillUpload: "));
		serviceInformation = result;
		/*
		if(result.getFileCode() != null) {
			serviceInformation = result;
		} else {
			UploadPhotosCallback callback = new UploadPhotosCallback(this);
			userImageService.getUploadedImageSet(serviceInformation, callback);
		}
		*/
	}
	
	public void refreshPictures() {
		UploadPhotosCallback callback = new UploadPhotosCallback(this);
		userImageService.getUploadedImageSetFromKeywordAndUser(keywordName, callback);
		
	}
	
	public void addImage(UploadedImage imageinfo) {
		ImageColumn imagecollapse = new ImageColumn(imageinfo);
		collapsible.add(imagecollapse);
	}
	
	public void saveImages() {
		//String columnname = ImageColumn.class.getName();
		ArrayList<UploadedImage> lst = new ArrayList<UploadedImage>();
		Window.alert("saveImages: " + collapsible.getWidgetCount());
		for(int i=0; i < collapsible.getWidgetCount() ; i++) {
			Widget w = collapsible.getWidget(i);
			ImageColumn image = (ImageColumn) w;
			//String classname = w.getClass().getName();
			UploadedImage updated = image.getUpdatedImageInfo();
			Window.alert(updated.getDescription());
			lst.add(updated);
		}
		UpdateImageCallback callback = new UpdateImageCallback();
		userImageService.updateImages(lst, callback);
	}
	
	@UiHandler("uploadButton")
	void onSubmit(ClickEvent e) {
		Window.alert(serviceInformation.getUploadUrl());
		uploadForm.setAction(serviceInformation.getUploadUrl());
		uploadForm.submit();			
	}
	@UiHandler("save")
	void onSave(ClickEvent e) {
		saveImages();
	}

	public void setText(String text) {
		uploadButton.setText(text);
	}

	public String getText() {
		return uploadButton.getText();
	}
	public String getIdentifier() {
		return keywordName;
	}
	public void setIdentifier(String identifier) {
		this.keywordName = identifier + "-image";
		identifiertip.setText(this.keywordName);
		startNewBlobstoreSession(true);
	}
}
