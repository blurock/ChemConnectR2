package info.esblurock.reaction.chemconnect.core.client.pages.primitive.text;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class PrimitiveSetOfKeywordsRow extends Composite implements SetLineContentInterface, KeywordChipInterface {

	private static PrimitiveSetOfKeywordsRowUiBinder uiBinder = GWT.create(PrimitiveSetOfKeywordsRowUiBinder.class);

	interface PrimitiveSetOfKeywordsRowUiBinder extends UiBinder<Widget, PrimitiveSetOfKeywordsRow> {
	}

	String enterkeyS;
	String keynameS;
	
	@UiField
	MaterialTooltip tip;
	@UiField
	MaterialTooltip addtip;
	@UiField
	MaterialPanel toppanel;
	@UiField
	MaterialIcon info;
	@UiField
	MaterialIcon add;
	@UiField
	MaterialPanel keys;
	
	String identifier;
	InputLineModal line;
	Set<String> keywords;
	
	public PrimitiveSetOfKeywordsRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public PrimitiveSetOfKeywordsRow(PrimitiveDataStructureInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fill(info);
	}

	void fill(PrimitiveDataStructureInformation info) {
		identifier  = info.getIdentifier();
		tip.setText(identifier);
		
	}
	
	void init() {
		keywords = new HashSet<String>();
		identifier = "id";
		addtip.setText("Click to enter keyword");
		tip.setText(identifier);
		add.setIconColor(Color.BLACK);
		info.setIconColor(Color.BLACK);
		enterkeyS = "Enter Key";
		keynameS = "Key";
	}
	
	@UiHandler("info")
	void onClickInfo(ClickEvent e) {
		MaterialToast.fireToast(identifier);
	}
	@UiHandler("add")
	void onClickAdd(ClickEvent e) {
		line = new InputLineModal(enterkeyS,keynameS,this);
		toppanel.add(line);
		line.openModal();
		
	}
	@Override
	public void setLineContent(String line) {
		if(keywords.contains(line)) {
			MaterialToast.fireToast("Keyword already in set");
		} else {
		keywords.add(line);
		KeywordChip chip = new KeywordChip(line,this);
		keys.add(chip);
		}
	}

	@Override
	public void chipClicked(String key) {
		keywords.remove(key);
		MaterialToast.fireToast("Removed: " + key);
		MaterialToast.fireToast(keywords.toString());
	}


}
