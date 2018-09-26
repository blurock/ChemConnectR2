package info.esblurock.reaction.chemconnect.core.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;

public class StandardDatasetContactInfoHeader extends Composite implements SetLineContentInterface {

	private static StandardDatasetContactInfoHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetContactInfoHeaderUiBinder.class);

	interface StandardDatasetContactInfoHeaderUiBinder extends UiBinder<Widget, StandardDatasetContactInfoHeader> {
	}

	public StandardDatasetContactInfoHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip contacttypetooltip;
	@UiField
	MaterialLink contacttype;
	@UiField
	MaterialTooltip contactkeytooltip;
	@UiField
	MaterialLink contactkey;
	
	StandardDatasetObjectHierarchyItem item;
	ContactInfoData contact;
	InputLineModal line;
	String contactType;
	
	public StandardDatasetContactInfoHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		init();
		contact = (ContactInfoData) item.getObject();
		contactType = contact.getContactType();
		contacttype.setText(TextUtilities.removeNamespace(contact.getContactType()));
		contactkey.setText(contact.getContact());
	}
	void init() {
		contacttypetooltip.setText("Type of contact information");
		contactkeytooltip.setText("Contact information");
	}
	
	@UiHandler("contactkey")
	void onClickHead(ClickEvent event) {
		line = new InputLineModal("HTTP address", "https://", this);
		item.getModalpanel().add(line);
		line.openModal();
	}

	public boolean updateData() {
		contact.setContact(contactkey.getText());
		contact.setContactType(contactType);
		return false;
	}

	@Override
	public void setLineContent(String line) {
		contactkey.setText(line);
	}
	
}
