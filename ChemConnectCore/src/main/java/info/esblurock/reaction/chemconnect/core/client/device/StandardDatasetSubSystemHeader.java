package info.esblurock.reaction.chemconnect.core.client.device;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.GeneralVoidReturnCallback;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class StandardDatasetSubSystemHeader extends Composite implements OKAnswerInterface {

	private static StandardDatasetSubSystemHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetSubSystemHeaderUiBinder.class);

	interface StandardDatasetSubSystemHeaderUiBinder extends UiBinder<Widget, StandardDatasetSubSystemHeader> {
	}

	public StandardDatasetSubSystemHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip devicetooltip;
	@UiField
	MaterialLink devicehead;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	StandardDatasetObjectHierarchyItem item;

	public StandardDatasetSubSystemHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		SubSystemDescription descr = (SubSystemDescription) item.getObject();
		devicehead.setText(TextUtilities.removeNamespace(descr.getSubSystemType()));
		devicetooltip.setText(descr.getIdentifier());
		save.setEnabled(true);
	}
	@UiHandler("save")
	void onClickSave(ClickEvent event) {
		item.writeDatabaseObjectHierarchy();
		
		
	}
	@UiHandler("delete")
	void deleteClick(ClickEvent event) {
		OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
		item.getModalpanel().clear();
		item.getModalpanel().add(askifok);
		askifok.openModal();
	}
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		GeneralVoidReturnCallback callback = new GeneralVoidReturnCallback("Subsystem deletion successful");
		async.deleteObject(item.getObject().getIdentifier(),
				MetaDataKeywords.subSystemDescription,
				callback);
	}

}
