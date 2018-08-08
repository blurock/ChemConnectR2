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
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;

public class InputLineModal extends Composite  {

	private static InputLineModalUiBinder uiBinder = GWT.create(InputLineModalUiBinder.class);

	interface InputLineModalUiBinder extends UiBinder<Widget, InputLineModal> {
	}

	public InputLineModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTextBox textbox;
	@UiField
	MaterialModal modal;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;
	
	SetLineContentInterface setline;
	
	public InputLineModal(String label, String placeholder, SetLineContentInterface setline) {
		initWidget(uiBinder.createAndBindUi(this));
		textbox.setPlaceholder(placeholder);
		textbox.setLabel(label);
		textbox.getLabel().setTextColor(Color.BLACK);
		this.setline = setline;
	}

	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		modal.close();
		setline.setLineContent(textbox.getText());
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}

	
	public void openModal() {
		modal.open();
	}

}
