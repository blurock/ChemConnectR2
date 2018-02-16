package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;

public class KeywordChip extends Composite {

	private static KeywordChipUiBinder uiBinder = GWT.create(KeywordChipUiBinder.class);

	interface KeywordChipUiBinder extends UiBinder<Widget, KeywordChip> {
	}

	KeywordChipInterface top; 
	

	@UiField
	MaterialChip keyword;

	public KeywordChip() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public KeywordChip(String keyword, KeywordChipInterface top) {
		initWidget(uiBinder.createAndBindUi(this));
		this.top = top;
		this.keyword.setText(keyword);
	}

	@UiHandler("keyword")
	void onClickKeyword(ClickEvent event) {
		top.chipClicked(keyword.getText());
	}
}
