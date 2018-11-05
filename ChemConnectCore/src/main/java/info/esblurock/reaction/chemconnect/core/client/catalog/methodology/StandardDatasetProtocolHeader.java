package info.esblurock.reaction.chemconnect.core.client.catalog.methodology;

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
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectProtocol;

public class StandardDatasetProtocolHeader extends Composite {

	private static StandardDatasetProtocolHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetProtocolHeaderUiBinder.class);

	interface StandardDatasetProtocolHeaderUiBinder extends UiBinder<Widget, StandardDatasetProtocolHeader> {
	}

	public StandardDatasetProtocolHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip devicetooltip;
	@UiField
	MaterialLink title;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	StandardDatasetObjectHierarchyItem item;

	public StandardDatasetProtocolHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		title.setText(TextUtilities.removeNamespace(methodology.getProtocolType()));
		devicetooltip.setText(methodology.getIdentifier());
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

	public boolean updateProtocol() {
		ChemConnectProtocol methodology = (ChemConnectProtocol) item.getObject();
		methodology.setProtocolType(title.getText());
		return true;
	}
}
