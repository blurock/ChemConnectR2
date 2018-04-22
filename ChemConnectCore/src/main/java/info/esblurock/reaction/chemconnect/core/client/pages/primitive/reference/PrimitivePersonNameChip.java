package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;

public class PrimitivePersonNameChip extends Composite {

	private static PrimitivePersonNameChipUiBinder uiBinder = GWT.create(PrimitivePersonNameChipUiBinder.class);

	interface PrimitivePersonNameChipUiBinder extends UiBinder<Widget, PrimitivePersonNameChip> {
	}

	@UiField
	MaterialChip lastname;
	
	PrimitiveInterpretedInformation info;
	NameOfPerson person; 
	PersonNameInterface modal;
	
	public PrimitivePersonNameChip(PersonNameInterface modal) {
		initWidget(uiBinder.createAndBindUi(this));
		Window.alert("PrimitivePersonNameChip:");
		this.modal = modal;
		init();
		info = new PrimitiveInterpretedInformation();
		setInfo(info);
		modal.fillModal(info,this);
	}
	public PrimitivePersonNameChip(PrimitiveInterpretedInformation info,PersonNameInterface modal) {
		initWidget(uiBinder.createAndBindUi(this));
		Window.alert("PrimitivePersonNameChip:");
		init();
		setInfo(info);
		lastname.setText(person.getFamilyName());
		this.modal = modal;
		Window.alert("PrimitivePersonNameChip: now fill modal");
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
	public PrimitiveInterpretedInformation getInfo() {
		return info;
	}
	public void setInfo(PrimitiveInterpretedInformation info) {
		this.info = info;
		person = (NameOfPerson) info.getObj();
		lastname.setText(person.getFamilyName());
	}
	@UiHandler("lastname")
	void onClickChip(ClickEvent e) {
		modal.fillModal(info,this);
		modal.openModal();
	}
}
