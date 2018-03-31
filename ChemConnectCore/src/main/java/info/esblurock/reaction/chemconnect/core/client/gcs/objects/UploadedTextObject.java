package info.esblurock.reaction.chemconnect.core.client.gcs.objects;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobContent;

public class UploadedTextObject extends Composite implements InsertBlobTextContentInterface,SetLineContentInterface {

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
	MaterialLink beginline;
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
	MaterialPanel modalpanel;
	
	public UploadedTextObject() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public UploadedTextObject(GCSBlobContent content,MaterialPanel modalpanel) {
		this.modalpanel = modalpanel;
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.blobContent = content;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		InsertTextCallback callback = new InsertTextCallback(this);
		async.getBlobAsLines(content, callback);
	}

	void init() {
		showlines = 10;
		dropdown.setText("10");
		parse.setText("Parse");
		parsetooltip.setText("Parse text as spreadsheet");
		totaltooltip.setText("Total number of lines");
		total.setText("0");
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
		InputLineModal modal = new InputLineModal("Starting line", "0",this);
		modalpanel.clear();
		modalpanel.add(modal);
		modal.openModal();
	}
	@Override
	public void insertBlobContent(ArrayList<String> text) {
		totalNumberOfLines = text.size();
		String totalS = Integer.toString(totalNumberOfLines);
		total.setText(totalS);
		totaltext = text;
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
		StringBuilder build = new StringBuilder();
		for(int i=first ; i< last; i++) {
			build.append(i + "   ");
			build.append(totaltext.get(i));
			build.append("\n");
		}
		textFile.setText(build.toString());
	}
	@Override
	public void setLineContent(String line) {
		try {
			int first = Integer.parseInt(beginline.getText());
			if(first <= 0 || first > totalNumberOfLines) {
				MaterialToast.fireToast("Begin line out of bounds");
			}
			
		} catch(Exception ex) {
			MaterialToast.fireToast("Illegal number for begin line");
		}
		
		beginline.setText(line);
		
	}
}
