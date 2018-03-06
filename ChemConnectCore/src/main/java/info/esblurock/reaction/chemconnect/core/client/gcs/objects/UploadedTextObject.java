package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;

public class UploadedTextObject extends Composite implements InsertBlobTextContentInterface {

	private static UploadedTextObjectUiBinder uiBinder = GWT.create(UploadedTextObjectUiBinder.class);

	interface UploadedTextObjectUiBinder extends UiBinder<Widget, UploadedTextObject> {
	}
	
	@UiField
	MaterialLink dropdown;
	@UiField	
	MaterialLink parse;
	@UiField
	MaterialLink total;
	@UiField
	MaterialTooltip totaltooltip;
	@UiField
	MaterialTooltip parsetooltip;
	@UiField
	MaterialTextBox beginline;
	//@UiField
	//MaterialTooltip dropnote;
	@UiField
	MaterialDropDown numlinesdrop;
	@UiField
	MaterialTextArea textFile;

	GCSBlobContent blobContent;
	ArrayList<String> totaltext;
	int showlines;
	int totalNumberOfLines;
	public UploadedTextObject() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public UploadedTextObject(GCSBlobContent content) {
		Window.alert("UploadedTextObject");
		initWidget(uiBinder.createAndBindUi(this));
		Window.alert("UploadedTextObject");
		init();
		this.blobContent = content;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		InsertTextCallback callback = new InsertTextCallback(this);
		Window.alert("UploadedTextObject");
		async.getBlobAsLines(content, callback);
	}

	void init() {
		showlines = 10;
		dropdown.setText("10");
		parse.setText("Parse");
		parsetooltip.setText("Parse text as spreadsheet");
		totaltooltip.setText("Total number of lines");
		total.setText("0");
		beginline.setLabel("First line (starting 1)");
		beginline.setText("0");
	}
	@UiHandler("numlinesdrop")
	void onDropdown(SelectionEvent<Widget> callback) {
		MaterialLink selected = (MaterialLink) callback.getSelectedItem();
		String text = selected.getText();
		dropdown.setText(text);
		showlines = Integer.parseInt(text);
		setText();
		}
	
	@UiHandler("beginline")
	void begintype(KeyDownEvent event) {
		int key = KeyCodes.KEY_ENTER;
		if(event.getNativeKeyCode() == key) {
			setText();
		}
	}
	@Override
	public void insertBlobContent(ArrayList<String> text) {
		totalNumberOfLines = text.size();
		String totalS = Integer.toString(totalNumberOfLines);
		total.setText(totalS);
		totaltext = text;
		Window.alert("insertBlobContent" + totalNumberOfLines);
		setText();
	}
	void setText() {
		int first = Integer.parseInt(beginline.getText());
		//int first = 0;
		if(first <= 0 || first > totalNumberOfLines) {
			first = 0;
		}
		int last = first + showlines;
		if(last > totalNumberOfLines) {
			last = totalNumberOfLines;
		}
		Window.alert("setText: " + first + ", " + last);
		StringBuilder build = new StringBuilder();
		for(int i=first ; i< last; i++) {
			build.append(i + "   ");
			build.append(totaltext.get(i));
			build.append("\n");
		}
		textFile.setText(build.toString());
	}
}
