package info.esblurock.reaction.chemconnect.core.client.catalog.multiple;

import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public interface CreateMultipleItemCallback {
	public StandardDatasetObjectHierarchyItem addMultipleObject(DatabaseObjectHierarchy obj);
}
