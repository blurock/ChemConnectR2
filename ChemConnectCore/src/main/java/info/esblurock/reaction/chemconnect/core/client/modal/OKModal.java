package info.esblurock.reaction.chemconnect.core.client.modal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;

public class OKModal extends Composite {

	private static OKModalUiBinder uiBinder = GWT.create(OKModalUiBinder.class);

	interface OKModalUiBinder extends UiBinder<Widget, OKModal> {
	}


	@UiField
	MaterialDialog modal;
	@UiField
	MaterialLink ok;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink text;

	OKAnswerInterface okinterface;
	String concept;
	
	
	public OKModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public OKModal(String concept, String text, OKAnswerInterface okinterface) {
		initWidget(uiBinder.createAndBindUi(this));
		this.concept = concept;
		this.okinterface = okinterface;
		this.text.setText(text);
	}
	
	public OKModal(String concept, String text, String oktext, OKAnswerInterface okinterface) {
		initWidget(uiBinder.createAndBindUi(this));
		this.okinterface = okinterface;
		this.text.setText(text);
		ok.setText(oktext);
		this.concept = concept;
	}

	@UiHandler("ok")
	void onClickOK(ClickEvent e) {
		okinterface.answeredOK(concept);
		modal.close();
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		modal.close();
	}
	public void openModal() {
		modal.open();
	}
}
