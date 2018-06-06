package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.catalog.hierarchy.StandardDatasetCatalogHierarchyHeader;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SubCatagoryHierarchyCallback implements AsyncCallback<DatabaseObjectHierarchy> {

	StandardDatasetCatalogHierarchyHeader top;
	
	public SubCatagoryHierarchyCallback(StandardDatasetCatalogHierarchyHeader top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy subs) {
		top.insertSubCatalog(subs);
	}

}
