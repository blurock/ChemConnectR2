package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;

public class CatalogHierarchyNode extends Composite  {

	private static CatalogHierarchyNodeUiBinder uiBinder = GWT.create(CatalogHierarchyNodeUiBinder.class);

	interface CatalogHierarchyNodeUiBinder extends UiBinder<Widget, CatalogHierarchyNode> {
	}

	public CatalogHierarchyNode() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink cataloghead;
	@UiField
	MaterialCollapsibleBody bodycollapsible;
	@UiField
	MaterialCollapsible contentcollapsible;

	public CatalogHierarchyNode(DatabaseObjectHierarchy top, TransferDatabaseCatalogHierarchy transfer) {
		initWidget(uiBinder.createAndBindUi(this));
		DatasetCatalogHierarchy hierarchy = (DatasetCatalogHierarchy) top.getObject();
		cataloghead.setText(hierarchy.getIdentifier());
		for(DatabaseObjectHierarchy obj: top.getSubobjects()) {
			//DatasetCatalogHierarchy sub = (DatasetCatalogHierarchy) obj.getObject();
			CatalogHierarchyNode subnode = new CatalogHierarchyNode(obj,transfer);
			contentcollapsible.add(subnode);
		}
	}


}
