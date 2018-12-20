package info.esblurock.reaction.chemconnect.core.client.pages.primitive.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.catalog.reference.AuthorInformationHeader;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

public class ChoiceOfNamesModal extends Composite {

	private static ChoiceOfNamesModalUiBinder uiBinder = GWT.create(ChoiceOfNamesModalUiBinder.class);

	interface ChoiceOfNamesModalUiBinder extends UiBinder<Widget, ChoiceOfNamesModal> {
	}

	public ChoiceOfNamesModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDropDown dropdown;
	@UiField
	MaterialDialog modal;
	@UiField
	MaterialButton close;
	
	Map<String,NameOfPerson> map = new HashMap<String,NameOfPerson>();
	AuthorInformationHeader header;
	
	public ChoiceOfNamesModal(ArrayList<NameOfPerson> names, AuthorInformationHeader header) {
		initWidget(uiBinder.createAndBindUi(this));
		this.header = header;
		for(NameOfPerson person : names) {
			String personname = person.getGivenName() + " " + person.getFamilyName();
			MaterialLink link = new MaterialLink(personname);
			link.setTextColor(Color.BLACK);
			link.setBackgroundColor(Color.GREY_LIGHTEN_4);
			link.setShadow(3);
			map.put(personname, person);
			dropdown.add(link);
		}
	}
	
	@UiHandler("dropdown")
	void onDropdown(SelectionEvent<Widget> callback) {
		String name = ((MaterialLink)callback.getSelectedItem()).getText();
		header.setFamilyName(map.get(name));
		close();
	 }

	@UiHandler("close")
	void onClose(ClickEvent event) {
		close();
	}
	public void open() {
		modal.open();
	}
	public void close() {
		modal.close();
	}
}
