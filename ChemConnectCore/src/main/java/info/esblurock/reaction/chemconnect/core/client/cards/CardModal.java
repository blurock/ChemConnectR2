package info.esblurock.reaction.chemconnect.core.client.cards;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;

public class CardModal extends Composite {

	private static CardModalUiBinder uiBinder = GWT.create(CardModalUiBinder.class);

	interface CardModalUiBinder extends UiBinder<Widget, CardModal> {
	}
	@UiField
	MaterialLink ok;
	@UiField
	MaterialLink close;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialDialogContent modalcontent;
	
	CardModalInterface card;
	
	public CardModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setContent(CardModalInterface card, boolean showOK) {
		ok.setVisible(showOK);
		this.card = card;
		modalcontent.clear();
		modalcontent.add(card);
		modal.setDismissible(true);
	}
	@UiHandler("close")
	public void onClose(ClickEvent event) {
		modal.close();
	}
	@UiHandler("ok")
	public void onOK(ClickEvent event) {
		card.actionOnOK();
	}

	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}

}
