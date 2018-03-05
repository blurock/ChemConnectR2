package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.gcs.objects.UploadedTextObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;

public class UploadedElementCollapsible extends Composite {

	private static UploadedElementCollapsibleUiBinder uiBinder = GWT.create(UploadedElementCollapsibleUiBinder.class);

	interface UploadedElementCollapsibleUiBinder extends UiBinder<Widget, UploadedElementCollapsible> {
	}

	public UploadedElementCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public static String imageClassS = "image";
	public static String textClassS = "text";
	@UiField
	MaterialLink type;
	@UiField
	MaterialLink path;
	@UiField
	MaterialLink delete;
	@UiField
	HTMLPanel imagepanel;
	@UiField
	MaterialTextArea textDescription;
	@UiField
	MaterialLink url;
	@UiField
	MaterialTooltip identifiertooltip;
	
	GCSBlobFileInformation info;
	String identifierRoot;
	String identifier;
	String typeClass;
	String typeInstance;
	String linkUrl;
	GCSBlobContent content;
	
	public UploadedElementCollapsible(GCSBlobContent content) {
		initWidget(uiBinder.createAndBindUi(this));
		this.content = content;
		init();
		fill(content);
	}
	
	void init() {
		typeClass = null;
		typeInstance = null;
		identifier = null;
		textDescription.setLabel("Description");
	}
	
	void fill(GCSBlobContent content) {
		this.content = content;
		info = content.getInfo();
		linkUrl = content.getUrl();
		url.setText(linkUrl);
		
		if(info != null) {
			parseType(info.getFiletype());
			type.setText(info.getFiletype());
			identifiertooltip.setText(info.getGSFilename());
			path.setText(info.getFilename());
			textDescription.setText(info.getDescription());
			setContentVisual();
		}
		
	}

	
	public void setContentVisual() {
			if(isImage()) {
				MaterialImage image = new MaterialImage(linkUrl);				
				imagepanel.add(image);
			} else if(isText()) {
				Window.alert("UploadedElementCollapsible: Text object");
				UploadedTextObject textobject = new UploadedTextObject(this.content);
				imagepanel.add(textobject);
			}
	}
	
	public boolean isImage() {
		boolean ans = false;
		if(typeClass != null) {
			if(typeClass.compareTo(imageClassS) == 0) {
				ans = true;
			}
		}
		return ans;
	}
	public boolean isText() {
		boolean ans = false;
		if(typeClass != null) {
			if(typeClass.compareTo(textClassS) == 0) {
				ans = true;
			}
		}
		return ans;
	}
	
	void parseType(String type) {
		int pos = type.indexOf("/");
		if(pos >= 0) {
			typeClass = type.substring(0, pos);
			typeInstance = type.substring(pos+1);
		}
	}
	@UiHandler("delete")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	public String setIdentifier(String identifierRoot) {
		this.identifierRoot = identifierRoot;
		this.identifier = this.identifierRoot;
		if(info != null) {
			this.identifier = this.identifierRoot + "-" + info.getFilename();
		}
		return this.identifier;
	}
}
