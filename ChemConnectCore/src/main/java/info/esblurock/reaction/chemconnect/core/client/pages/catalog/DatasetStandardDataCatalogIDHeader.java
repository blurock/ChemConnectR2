package info.esblurock.reaction.chemconnect.core.client.pages.catalog;

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

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;

public class DatasetStandardDataCatalogIDHeader extends Composite {

	private static DatasetStandardDataCatalogIDHeaderUiBinder uiBinder = GWT
			.create(DatasetStandardDataCatalogIDHeaderUiBinder.class);

	interface DatasetStandardDataCatalogIDHeaderUiBinder
			extends UiBinder<Widget, DatasetStandardDataCatalogIDHeader> {
	}

	@UiField
	MaterialLink catalogBase;
	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink simpleName;
	
	StandardDatasetObjectHierarchyItem item;
	DataCatalogID object;

	public DatasetStandardDataCatalogIDHeader(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
		this.object = (DataCatalogID) item.getObject();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void updateData() {
		object.setCatalogBaseName(catalogBase.getText());
		object.setDataCatalog(catalog.getText());
		object.setSimpleCatalogName(simpleName.getText());
	}
}
