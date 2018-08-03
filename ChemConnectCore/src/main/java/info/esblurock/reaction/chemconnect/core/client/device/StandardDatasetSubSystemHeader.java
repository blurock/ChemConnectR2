package info.esblurock.reaction.chemconnect.core.client.device;

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
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.dataset.device.SubSystemDescription;

public class StandardDatasetSubSystemHeader extends Composite {

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
		Window.alert("Save Object");
		item.writeDatabaseObjectHierarchy();
		
		
	}
	@UiHandler("delete")
	void onClickDelete(ClickEvent event) {
		Window.alert("Delete Object not implemented");
	}
}