package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;

public class SpreadSheetBlockTitle extends Composite  {

	private static SpreadSheetBlockTitleUiBinder uiBinder = GWT.create(SpreadSheetBlockTitleUiBinder.class);

	interface SpreadSheetBlockTitleUiBinder extends UiBinder<Widget, SpreadSheetBlockTitle> {
	}

	public SpreadSheetBlockTitle() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink attribute;
	@UiField
	MaterialLink title;
	@UiField
	MaterialRow titlerow;

	PrimitiveObservationVauesWithSpecificationRow top;
	
	public SpreadSheetBlockTitle(SpreadSheetBlockInformation block, PrimitiveObservationVauesWithSpecificationRow top) {
		initWidget(uiBinder.createAndBindUi(this));
		this.top = top;
		init();
		this.title.setText(block.getTitle());
	}
	
	void init() {
		attribute.setText("Attribute");
	}

	@UiHandler("attribute")
	void onClickAttribute(ClickEvent e) {
		Window.alert("Attribute");
	}
	@UiHandler("title")
	void onClickTitle(ClickEvent e) {
		Window.alert("Title");
	}


}
