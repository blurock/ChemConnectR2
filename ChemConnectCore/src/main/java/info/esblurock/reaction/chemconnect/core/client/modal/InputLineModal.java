package info.esblurock.reaction.chemconnect.core.client.modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialFloatBox;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class InputLineModal extends Composite  {

	private static InputLineModalUiBinder uiBinder = GWT.create(InputLineModalUiBinder.class);

	interface InputLineModalUiBinder extends UiBinder<Widget, InputLineModal> {
	}

	public InputLineModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public static String textField = "textField";
	public static String integerField = "integerField";
	public static String floatField = "floatField";

	@UiField
	MaterialTextBox textbox;
	@UiField
	MaterialIntegerBox integerbox;
	@UiField
	MaterialFloatBox floatbox;
	@UiField
	MaterialTooltip undefinedtooltip;
	@UiField
	MaterialCheckBox undefined;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;
	
	SetLineContentInterface setline;
	
	
	public InputLineModal(String label, String placeholder, SetLineContentInterface setline) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		textbox.setPlaceholder(placeholder);
		textbox.setLabel(label);
		this.setline = setline;
		textbox.setVisible(true);
	}
	public InputLineModal(String label, String placeholder, String ftype, boolean allowundefined, SetLineContentInterface setline) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setline = setline;
		init();
		if(ftype.compareTo(textField) == 0) {
			textbox.setPlaceholder(placeholder);
			textbox.getLabel().setTextColor(Color.BLACK);
			textbox.setLabel(label);
			textbox.setVisible(true);
		} else if(ftype.compareTo(integerField) == 0) {
			integerbox.setPlaceholder(placeholder);
			integerbox.getLabel().setTextColor(Color.BLACK);
			integerbox.setLabel(label);
			integerbox.setVisible(true);
		} else if(ftype.compareTo(floatField) == 0) {
			floatbox.setPlaceholder(placeholder);
			floatbox.getLabel().setTextColor(Color.BLACK);
			floatbox.setLabel(label);
			floatbox.setVisible(true);
		};
		if(allowundefined) {
			undefined.setVisible(true);
			undefinedtooltip.setText("Check box if value should be labeled as undefined");
		}
	}
	void init() {
		textbox.setText("   ");
		integerbox.setText("0");
		floatbox.setText("0.0");
		textbox.setVisible(false);
		integerbox.setVisible(false);
		floatbox.setVisible(false);
		undefined.setVisible(false);
	}

	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		modal.close();
		String line = "";
		if(undefined.getValue().booleanValue()) {
			line = MetaDataKeywords.undefined;
		} else {
			boolean valid = textbox.validate() && integerbox.validate() && floatbox.validate();
			if(valid) {
				if(textbox.isVisible()) {
					line = textbox.getText();
				} else if(integerbox.isVisible()) {
					line = integerbox.getText();
				} else if(floatbox.isVisible()) {
					line = floatbox.getText();
				}
			} else {
				MaterialToast.fireToast("Invalid input");
			}
		}
		setline.setLineContent(line);
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}

	
	public void openModal() {
		modal.open();
	}

}
