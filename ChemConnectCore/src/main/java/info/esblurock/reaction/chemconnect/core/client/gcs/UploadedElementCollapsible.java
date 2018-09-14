package info.esblurock.reaction.chemconnect.core.client.gcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.gcs.objects.UploadedTextObject;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet.DeleteObjectCallback;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;

public class UploadedElementCollapsible extends Composite implements VisualizationOfBlobStorage, ChooseFromConceptHeirarchy, InsertBlobContentInterface {

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
	MaterialLink filetype;
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
	Map<String, ClassificationInformation> interpretmap;
	ClassificationInformation interpretinfo;
	String identifierRoot;
	String identifier;
	String typeClass;
	String typeInstance;
	String linkUrl;
	GCSBlobContent content;
	MaterialPanel modalpanel;
	String visualType;
	
	DataCatalogID catid;
	
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
		Window.alert("fill(GCSBlobContent content):  url" + content.getUrl());
		this.content = content;
		info = content.getInfo();
		linkUrl = content.getUrl();
		if(linkUrl != null) {
			urltooltip.setText(linkUrl);
		} else {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			GCSContentCallback callback = new GCSContentCallback(this);
			async.getBlobContent(info,callback);

			
			
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
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		FindFileTypeCallback callback = new FindFileTypeCallback(this);
		async.getFileInterpretionChoices(info,callback);
	}
	
	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete");
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		DeleteObjectCallback callback = new DeleteObjectCallback(path.getText());
		async.deleteTransaction(info.getSourceID(), callback);
		this.removeFromParent();
	}
	
	@UiHandler("add")
	void onClick(ClickEvent e) {
		VisualizeMedia visual = VisualizeMedia.valueOf(visualType);
		String sourceType = SpreadSheetInputInformation.BLOBSOURCE;
		String source = info.getGSFilename();
		boolean titleGiven = false;
		DatabaseObject obj = new DatabaseObject(info);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		SpreadSheetInputInformation spread = new SpreadSheetInputInformation(structure," ",sourceType,source,titleGiven);
		if(visual != null) {
			visual.getInterpretedBlob(info, spread, catid,true,this);
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
	public String getPath() {
		return path.getText();
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		visualType = concept;
		interpretinfo = interpretmap.get(concept);
		filetype.setText(concept);
	}

	public void askForInterpretationType(HierarchyNode hierarchy) {
		interpretmap = new HashMap<String, ClassificationInformation>();
		findClassifications(hierarchy,interpretmap);
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(this);
		choose.setupTree(hierarchy);
		modalpanel.add(choose);
		choose.open();
	}

	// dataset:   ....  16 characters
	private void findClassifications(HierarchyNode hierarchy, Map<String, ClassificationInformation> interpretmap) {
		String name = hierarchy.getIdentifier();
		String shortname = name;
		if(name.startsWith("dataset:")) {
			shortname = name.substring(8);
		}
		if(shortname.startsWith("FileType")) {
			shortname = shortname.substring(8);
		}
		hierarchy.setIdentifier(shortname);
		if(hierarchy.getInfo() != null) {
			interpretmap.put(shortname,hierarchy.getInfo());
		}
		if(hierarchy.getSubNodes() != null) {
			for(HierarchyNode node : hierarchy.getSubNodes()) {
				findClassifications(node,interpretmap);
			}
		}
	}

	@Override
	public void insertBlobInformation(GCSBlobContent content) {
	if(isImage()) {
		Window.alert("insertBlobInformation: " + content.getUrl());
		linkUrl = content.getUrl();
		imagepanel.clear();
		MaterialImage image = new MaterialImage(linkUrl);				
		imagepanel.add(image);
		
	}
	}
}
