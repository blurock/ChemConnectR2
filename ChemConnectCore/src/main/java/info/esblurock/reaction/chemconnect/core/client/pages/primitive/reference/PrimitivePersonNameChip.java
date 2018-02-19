package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitivePersonNameInformation;

public class PrimitivePersonNameChip extends Composite {

	private static PrimitivePersonNameChipUiBinder uiBinder = GWT.create(PrimitivePersonNameChipUiBinder.class);

	interface PrimitivePersonNameChipUiBinder extends UiBinder<Widget, PrimitivePersonNameChip> {
	}

	@UiField
	MaterialChip lastname;
	
	PrimitivePersonNameInformation info;
	PersonNameInterface modal;
	
	public PrimitivePersonNameChip(PersonNameInterface modal) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modal = modal;
		init();
		info = new PrimitivePersonNameInformation();
		setInfo(info);
		modal.fillModal(info,this);
	}
	public PrimitivePersonNameChip(PrimitivePersonNameInformation info,PersonNameInterface modal) {
		initWidget(uiBinder.createAndBindUi(this));
		lastname.setText(info.getPersonFamiltyName());
		setInfo(info);
		init();
		this.modal = modal;
		modal.fillModal(info,this);
	}
	void init() {
	}

	public void setLastName(String name) {
		lastname.setText(name);
	}

	public MaterialChip getLastname() {
		return lastname;
	}
	public void setLastname(MaterialChip lastname) {
		this.lastname = lastname;
	}
	public PrimitivePersonNameInformation getInfo() {
		return info;
	}
	public void setInfo(PrimitivePersonNameInformation info) {
		this.info = info;
		lastname.setText(info.getPersonFamiltyName());
	}
	@UiHandler("lastname")
	void onClickChip(ClickEvent e) {
		modal.fillModal(info,this);
		modal.openModal();
	}
}
