package info.esblurock.reaction.chemconnect.core.client.catalog.reference;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTextBox;

public class NewAuthorInformationModal extends Composite  {

	private static NewAuthorInformationModalUiBinder uiBinder = GWT.create(NewAuthorInformationModalUiBinder.class);

	interface NewAuthorInformationModalUiBinder extends UiBinder<Widget, NewAuthorInformationModal> {
	}

	public NewAuthorInformationModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialDialog authorModal;
	@UiField
	MaterialTextBox nameText;
	@UiField
	MaterialTextBox lastNameText;
	@UiField
	MaterialTextBox link;
	
	StandardDatasetDataSetReferenceHeader reference;

	public NewAuthorInformationModal(StandardDatasetDataSetReferenceHeader reference) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("newAuthorButton")
	void onClickDone(ClickEvent e) {
		reference.addNewAuthor("",nameText.getText(),lastNameText.getText(),link.getText());
	}
	@UiHandler("authorCloseButton")
	void onClickClose(ClickEvent e) {
		authorModal.close();
	}
}
