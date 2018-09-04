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

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
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
	String defaultSimpleName;
	String defaultcatalogName;
	String defaultBaseName;

	public DatasetStandardDataCatalogIDHeader(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
		this.object = (DataCatalogID) item.getObject();
		initWidget(uiBinder.createAndBindUi(this));
		init();
		TextUtilities.setText(catalogBase, object.getCatalogBaseName(), defaultBaseName);
		TextUtilities.setText(catalog, object.getDataCatalog(), defaultcatalogName);
		TextUtilities.setText(simpleName, object.getSimpleCatalogName(), defaultSimpleName);
	}
	
	public void init() {
		defaultSimpleName = "no simple name";
		defaultBaseName = "set catalog base name";
		defaultcatalogName = "no catalog name";
	}

	public void updateData() {
		if(catalogBase.getText().compareTo(defaultBaseName) == 0) {
			object.setCatalogBaseName("");
		} else {
			object.setCatalogBaseName(catalogBase.getText());
		}
		if(catalog.getText().compareTo(defaultcatalogName) == 0) {
			object.setCatalogBaseName("");
		} else {
			object.setDataCatalog(catalog.getText());
		}
		if(simpleName.getText().compareTo(defaultSimpleName) == 0) {
			object.setCatalogBaseName("");
		} else {
			object.setSimpleCatalogName(simpleName.getText());
		}
		
		
		
	}
}
