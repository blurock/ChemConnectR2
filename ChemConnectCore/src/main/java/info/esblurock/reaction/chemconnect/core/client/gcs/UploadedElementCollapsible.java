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
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.gcs.objects.UploadedTextObject;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public class UploadedElementCollapsible extends Composite implements VisualizationOfBlobStorage {

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
	@UiField
	MaterialTooltip urltooltip;
	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialLink add;
	
	GCSBlobFileInformation info;
	String identifierRoot;
	String identifier;
	String typeClass;
	String typeInstance;
	String linkUrl;
	GCSBlobContent content;
	MaterialPanel modalpanel;
	String visualType;
	
	public UploadedElementCollapsible(GCSBlobContent content,MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		this.content = content;
		init();
		fill(content);
	}
	
	void init() {
		typeClass = null;
		typeInstance = null;
		identifier = null;
		textDescription.setLabel("Description");
		urltooltip.setText("Download File");
	}
	
	void fill(GCSBlobContent content) {
		this.content = content;
		info = content.getInfo();
		linkUrl = content.getUrl();
		if(linkUrl != null) {
			urltooltip.setText(linkUrl);
		} else {
			urltooltip.setText("");
		}
		
		if(info != null) {
			parseType(info.getFiletype());
			type.setText(info.getFiletype());
			typetooltip.setText(info.getFiletype());
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
				UploadedTextObject textobject = new UploadedTextObject(this.content,modalpanel);
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
		visualType = "SpreadSheet";
		if(typeClass.compareTo("image") == 0) {
			visualType = "Image";
		} else {
			
		}
	}
	
	@UiHandler("type")
	void onClickType(ClickEvent e) {
		Window.alert("Type=" + visualType);
	}
	
	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete");
	}
	
	@UiHandler("add")
	void onClick(ClickEvent e) {
		Window.alert("onClick:  getInterpretedBlob "+ visualType);
		VisualizeMedia visual = VisualizeMedia.valueOf(visualType);
		String type = SpreadSheetInputInformation.TabDelimited;
		String sourceType = SpreadSheetInputInformation.BLOBSOURCE;
		String source = info.getGSFilename();
		SpreadSheetInputInformation spread = new SpreadSheetInputInformation(info,type,sourceType,source);
		if(visual != null) {
			Window.alert("onClick:  getInterpretedBlob "+ spread.toString());
			visual.getInterpretedBlob(info, spread, this);
		}
	}
	
	@UiHandler("url")
	void onClickUrl(ClickEvent e) {
		Window.open(linkUrl, "Download", "");
	}
	
	public String setIdentifier(String identifierRoot) {
		this.identifierRoot = identifierRoot;
		this.identifier = this.identifierRoot;
		if(info != null) {
			this.identifier = this.identifierRoot + "-" + info.getFilename();
		}
		return this.identifier;
	}

	@Override
	public void insertVisualization(Widget panel) {
		imagepanel.add(panel);
	}
}
