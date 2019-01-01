package info.esblurock.reaction.chemconnect.core.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

public class QueryNameOfPersonModal extends Composite {

	private static QueryNameOfPersonModalUiBinder uiBinder = GWT.create(QueryNameOfPersonModalUiBinder.class);

	interface QueryNameOfPersonModalUiBinder extends UiBinder<Widget, QueryNameOfPersonModal> {
	}

	public QueryNameOfPersonModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDialog personmodal;
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

	QueryNameOfPersonInterface queryInterface;
	NameOfPerson person;
	
	public QueryNameOfPersonModal(NameOfPerson person, QueryNameOfPersonInterface queryInterface) {
		initWidget(uiBinder.createAndBindUi(this));
		this.queryInterface = queryInterface;
		init();
		this.person = person;
		fill(person);
	}
	void init() {
		personTitle.setText("M.");
		personName.setText("Name");
		personeFamilyName.setText("LastName");
		personTitle.setLabel("title");
		personName.setLabel("Given name");
		personeFamilyName.setLabel("Family name");
		personTitle.setTextColor(Color.BLACK);
		personName.setTextColor(Color.BLACK);
		personeFamilyName.setTextColor(Color.BLACK);
		close.setText("Close");
		ok.setText("OK");
		close.setTextColor(Color.BLACK);
		ok.setTextColor(Color.BLACK);
		personmodal.setBackgroundColor(Color.GREY_LIGHTEN_4);
	}
	public void fill(NameOfPerson person) {
		if(person != null) {
		if(person.getTitle() != null) {
			personTitle.setText(person.getTitle());
		} else {
			personTitle.setText("");
		}
		if(person.getGivenName() != null) {
			personName.setText(person.getGivenName());
		} else {
			personName.setText("");
		}
		if(person.getFamilyName() != null) {
			personeFamilyName.setText(person.getFamilyName());
		} else {
			personeFamilyName.setText("LastName");
		}
		}
	}
	public void open() {
		personmodal.open();
	}
	@UiHandler("ok")
	void onClickOK(ClickEvent e) {
		NameOfPerson changedperson = new NameOfPerson(person,personTitle.getText(),personName.getText(),personeFamilyName.getText());
		queryInterface.insertNameOfPerson(changedperson);
		personmodal.close();
	}
	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		personmodal.close();
	}

}
