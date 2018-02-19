package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitivePersonNameInformation;

public class PrimitivePersonNameRow extends Composite implements 	PersonNameInterface {

	private static PrimitivePersonNameRowUiBinder uiBinder = GWT.create(PrimitivePersonNameRowUiBinder.class);

	interface PrimitivePersonNameRowUiBinder extends UiBinder<Widget, PrimitivePersonNameRow> {
	}

	@UiField
	MaterialPanel names;
	@UiField
	MaterialIcon add;
	@UiField
	MaterialModal personmodal;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialTextBox personTitle;
	@UiField
	MaterialTextBox personName;
	@UiField
	MaterialTextBox personeFamilyName;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink ok;

	String identifier;
	PrimitivePersonNameChip chip;
	PrimitivePersonNameInformation info;
	
	public PrimitivePersonNameRow() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		identifier = null;
		this.chip = new PrimitivePersonNameChip(this);
		names.add(chip);			
	}

	public PrimitivePersonNameRow(PrimitivePersonNameInformation info) {
		initWidget(uiBinder.createAndBindUi(this));
		identifier = info.getIdentifier();
		addChip(info);
	}
	void init() {
		personTitle.setText("M.");
		personName.setText("Name");
		personeFamilyName.setText("LastName");
		personTitle.setLabel("title");
		personName.setLabel("Given name");
		personeFamilyName.setLabel("Family name");
		identifiertip.setText("id");
		personTitle.setTextColor(Color.BLACK);
		personName.setTextColor(Color.BLACK);
		personeFamilyName.setTextColor(Color.BLACK);
		close.setText("Close");
		ok.setText("OK");
		close.setTextColor(Color.BLACK);
		ok.setTextColor(Color.BLACK);
		personmodal.setBackgroundColor(Color.GREY_LIGHTEN_4);
	}
	public void fillModal(PrimitivePersonNameInformation info, PrimitivePersonNameChip chip) {
		if(info.getPersonTitle() != null) {
			personTitle.setText(info.getPersonTitle());
		} else {
			personTitle.setText("");
		}
		if(info.getPersonGivenName() != null) {
			personName.setText(info.getPersonGivenName());
		} else {
			personName.setText("");
		}
		if(info.getPersonFamiltyName() != null) {
			personeFamilyName.setText(info.getPersonFamiltyName());
		} else {
			personeFamilyName.setText("LastName");
		}
		identifiertip.setText(info.getIdentifier());
		this.chip = chip;
		this.info = info;
	}

	public void fill(PrimitivePersonNameInformation info) {
		addChip(info);
	}
	public PrimitivePersonNameChip addChip(PrimitivePersonNameInformation info) {
		PrimitivePersonNameChip chip = new PrimitivePersonNameChip(info,this);
		names.add(chip);		
		return chip;
	}
	public PrimitivePersonNameChip addChip() {
		PrimitivePersonNameInformation info = new PrimitivePersonNameInformation();
		return addChip(info);
	}
	public String getIdentifier() {
		return identifier;
	}
	@UiHandler("add")
	void onAddClick(ClickEvent ev) {
		PrimitivePersonNameChip chip = addChip();
		openModal();
	}
	public void openModal() {
		personmodal.open();
	}
	@UiHandler("ok")
	void onClickOK(ClickEvent e) {
		info.setPersonTitle(personTitle.getText());
		info.setPersonGivenName(personName.getText());
		info.setValue(personeFamilyName.getText());
		chip.setInfo(info);
		personmodal.close();
		MaterialToast.fireToast("Updated");
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		personmodal.close();
	}

}
