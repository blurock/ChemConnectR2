package info.esblurock.reaction.chemconnect.core.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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
	MaterialTooltip devicetooltip;
	@UiField
	MaterialLink contacthead;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	StandardDatasetObjectHierarchyItem item;
	ContactInfoData descr;
	InputLineModal line;
	
	public StandardDatasetContactInfoHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		descr = (ContactInfoData) item.getObject();
		TextUtilities.setText(contacthead,descr.getEmail(), "Email");
		devicetooltip.setText(descr.getIdentifier());
		save.setEnabled(true);
	}
	
	@UiHandler("save")
	void onClickSave(ClickEvent event) {
		Window.alert("Save Object");
		item.writeDatabaseObjectHierarchy();
	}
	@UiHandler("delete")
	void onClickDelete(ClickEvent event) {
		Window.alert("Delete Object not implemented");
	}
	@UiHandler("contacthead")
	void onClickHead(ClickEvent event) {
		line = new InputLineModal("primary email address", "info@email.edu", this);
		item.getModalpanel().add(line);
		line.openModal();
	}

	public boolean updateData() {
		descr.setEmail(contacthead.getText());
		return true;
	}

	@Override
	public void setLineContent(String line) {
		contacthead.setText(line);
	}
	
}
