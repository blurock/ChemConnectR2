package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.client.events.DragOverEvent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.UploadedFilesInterface;
import info.esblurock.reaction.chemconnect.core.client.ui.view.UploadFileToBlobStorageView;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadFileToBlobStorage extends Composite implements DetermineBlobTargetInterface, 
			InsertBlobContentInterface, UploadedFilesInterface, SetLineContentInterface, UploadFileToBlobStorageView {

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
	MaterialButton textareaupload;
	@UiField
	MaterialTextArea textarea;
	@UiField
	MaterialButton httpupload;
	@UiField
	MaterialFileUploader uploader;
	@UiField
	MaterialLink textname;
	@UiField
	MaterialLink textlabel;
	
	Presenter listener;
	DatabaseObject obj;
	boolean filenamegiven;
	
	public UploadFileToBlobStorage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	void init() {		
		httpupload.setText("Upload as URL");
		textareaupload.setText("Upload text area");
		title.setTitle("File Upload Staging");
		textname.setText("click to set URL or text area filename");
		textlabel.setText("filename/URL");
		obj = new DatabaseObject();
		getUploadedFiles();
		
		uploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				refresh();
				}
			 });		
		uploader.addDragOverHandler(new DragOverEvent.DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				MaterialAnimation animate = new MaterialAnimation(uploader);
				animate.animate();
				}
			});
		filenamegiven = false;
		
		checkUserLoggedIn();
	}
	
	private void checkUserLoggedIn() {
		String account = Cookies.getCookie("account_name");
		boolean loggedin = true;
		if(account == null) {
			loggedin = false;
		}
		httpupload.setEnabled(loggedin);
		uploader.setEnabled(loggedin);
		textareaupload.setEnabled(loggedin);
	}
	
	@UiHandler("httpupload")
	public void onHttpUpload(ClickEvent event) {
		if(filenamegiven) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		ContentUploadCallback callback = new ContentUploadCallback(this);
		String url = textname.getText();
		if(uniqueFilename(url)) {
			async.retrieveBlobFromURL(url,callback);
		}
		} else {
			MaterialToast.fireToast("Please enter a URL");
		}
		filenamegiven = false;
	}
	
	@UiHandler("textareaupload")
	public void onTextFileUpload(ClickEvent event) {
		if(filenamegiven) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		ContentUploadCallback callback = new ContentUploadCallback(this);
		String filename = textname.getText();
		String content = textarea.getText();
		if(uniqueFilename(filename) ) {
			async.retrieveBlobFromContent(filename,content,callback);
			
		}
		} else {
			MaterialToast.fireToast("Please enter a filename for the text");
		}
		filenamegiven = false;
	}
	@UiHandler("textname")
	public void onClickFilename(ClickEvent event) {
		askForFilename();
	}
	
	public void askForFilename() {
		InputLineModal modal = new InputLineModal("Input Text file name", textname.getText(), this);
		modalpanel.add(modal);
		modal.openModal();		
	}
	
	@Override
	public void refresh() {
		checkUserLoggedIn();
		collapsible.clear();
		getUploadedFiles();		
	}
	
	private void getUploadedFiles() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		UploadedFilesCallback callback = new UploadedFilesCallback(this,false);
		async.getUploadedFiles(callback);
		
	}

	private boolean uniqueFilename(String filename) {
		boolean ans = true;
		for(Widget widget : collapsible) {
			UploadedElementCollapsible coll = (UploadedElementCollapsible) widget;
			String path = coll.getPath();
			if(path.compareTo(filename) == 0) {
				ans = false;
				MaterialToast.fireToast("Filename in use:  choose another");
			}
		}
		return ans;
	}
	
	public void setIdentifier(DatabaseObject obj) {
		this.obj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-suppinfo";
		this.obj.setIdentifier(id);
	}

	@Override
	public void handleTargetBlob(GCSBlobFileInformation fileinfo) {
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

	@Override
	public void setLineContent(String line) {
		if(line.length() > 0) {
		if(uniqueFilename(line)) {
			textname.setText(line);
			filenamegiven = true;
		}
		}
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
