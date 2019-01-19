package info.esblurock.reaction.chemconnect.core.client.resources.info.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.ui.view.ContactInformationView;

public class ContactInformation extends Composite implements ContactInformationView {

	Presenter listener;

	private static ContactInformationUiBinder uiBinder = GWT.create(ContactInformationUiBinder.class);

	interface ContactInformationUiBinder extends UiBinder<Widget, ContactInformation> {
	}

	@UiField
	MaterialTitle title;

	public ContactInformation() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setTitle("Contact Information");
	}
	

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
