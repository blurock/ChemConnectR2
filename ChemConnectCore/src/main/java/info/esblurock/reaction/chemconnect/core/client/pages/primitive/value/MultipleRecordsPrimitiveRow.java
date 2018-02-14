package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class MultipleRecordsPrimitiveRow extends Composite implements HasText {

	private static MultipleRecordsPrimitiveRowUiBinder uiBinder = GWT.create(MultipleRecordsPrimitiveRowUiBinder.class);

	interface MultipleRecordsPrimitiveRowUiBinder extends UiBinder<Widget, MultipleRecordsPrimitiveRow> {
	}

	@UiField
	MaterialLink parameterType;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink clear;
	@UiField
	MaterialLink add;
	@UiField
	MaterialPanel content;

	CreatePrimitiveStructure create;
	//DataElementInformation record;
	
	public MultipleRecordsPrimitiveRow() {
		initWidget(uiBinder.createAndBindUi(this));
		parameterType.setText("Parameter");
		init();
	}

	public MultipleRecordsPrimitiveRow(String structure, CreatePrimitiveStructure create) {
		initWidget(uiBinder.createAndBindUi(this));
		parameterType.setText(structure);
		this.create = create;
		init();
	}

	void init() {
		info.setIconColor(Color.BLACK);
		clear.setIconColor(Color.BLACK);
		add.setIconColor(Color.BLACK);
		parameterType.setTextColor(Color.BLACK);
	}
	
	@UiHandler("clear")
	void clearClick(ClickEvent ev) {
		MaterialToast.fireToast("clear");
	}
	@UiHandler("info")
	void infoClick(ClickEvent ev) {
		MaterialToast.fireToast("info");
	}
	@UiHandler("add")
	void addClick(ClickEvent ev) {
		MaterialToast.fireToast("add");
		PrimitiveDataStructureBase base = create.createEmptyStructure();
		content.add(base);
	}
	
	public PrimitiveDataStructureBase addStructure(PrimitiveDataStructureInformation info) {
		PrimitiveDataStructureBase base = create.createStructure(info);
		content.add(base);
		return base;
	}
	public void setText(String text) {
		parameterType.setText(text);
	}

	public String getText() {
		return parameterType.getText();
	}

}
