package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
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
	@UiField
	MaterialTooltip basetooltip;
	
	StandardDatasetObjectHierarchyItem item;
	DataCatalogID object;
	String defaultSimpleName;
	String defaultcatalogName;
	String defaultBaseName;
	String catalogName;
	String fullBaseName;

	public DatasetStandardDataCatalogIDHeader(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
		this.object = (DataCatalogID) item.getObject();
		initWidget(uiBinder.createAndBindUi(this));
		init();
		fullBaseName = object.getCatalogBaseName();
		if(object.getCatalogBaseName() != null) {
			basetooltip.setText(fullBaseName);
		} else {
			basetooltip.setText(defaultBaseName);
		}
		
		TextUtilities.setText(catalogBase, object.getCatalogBaseName(), defaultBaseName);
		if(object.getPath() != null) {
			int sze = object.getPath().size();
			if(sze > 0) {
				String name = object.getPath().get(sze-1);
				catalogBase.setText(name);
			}
		}
		catalogName = object.getDataCatalog();
		TextUtilities.setText(catalog, TextUtilities.removeNamespace(catalogName), defaultcatalogName);
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
			object.setCatalogBaseName(fullBaseName);
		}
		if(catalog.getText().compareTo(defaultcatalogName) == 0) {
			object.setCatalogBaseName("");
		} else {
			object.setDataCatalog(catalogName);
		}
		if(simpleName.getText().compareTo(defaultSimpleName) == 0) {
			object.setCatalogBaseName("");
		} else {
			object.setSimpleCatalogName(simpleName.getText());
		}
		
		
		
	}
}
