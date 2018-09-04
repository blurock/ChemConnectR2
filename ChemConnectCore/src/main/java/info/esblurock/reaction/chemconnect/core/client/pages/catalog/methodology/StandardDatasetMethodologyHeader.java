package info.esblurock.reaction.chemconnect.core.client.pages.catalog.methodology;

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
import info.esblurock.reaction.chemconnect.core.data.methodology.ChemConnectMethodology;

public class StandardDatasetMethodologyHeader extends Composite {

	private static StandardDatasetMethodologyHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetMethodologyHeaderUiBinder.class);

	interface StandardDatasetMethodologyHeaderUiBinder extends UiBinder<Widget, StandardDatasetMethodologyHeader> {
	}

	public StandardDatasetMethodologyHeader() {
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

	public StandardDatasetMethodologyHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		ChemConnectMethodology methodology = (ChemConnectMethodology) item.getObject();
		title.setText(TextUtilities.removeNamespace(methodology.getMethodologyType()));
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

	public boolean updateMethodology() {
		ChemConnectMethodology methodology = (ChemConnectMethodology) item.getObject();
		methodology.setMethodologyType(title.getText());
		return true;
	}
}
