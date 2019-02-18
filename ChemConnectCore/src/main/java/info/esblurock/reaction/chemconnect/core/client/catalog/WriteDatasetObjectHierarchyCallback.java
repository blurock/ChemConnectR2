package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class WriteDatasetObjectHierarchyCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	StandardDatasetObjectHierarchyItem item;
	public WriteDatasetObjectHierarchyCallback(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("Error in Writing dataset\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		MaterialLoader.loading(false);
		item.updateHierarchy(hierarchy);
	}

}
