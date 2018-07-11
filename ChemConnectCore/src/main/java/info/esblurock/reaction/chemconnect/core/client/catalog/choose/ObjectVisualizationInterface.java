package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;

public interface ObjectVisualizationInterface {
	public void createCatalogObject(DatabaseObject obj,DataCatalogID catid);
}
