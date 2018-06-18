package info.esblurock.reaction.chemconnect.core.client.pages.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class WriteDatasetObjectHierarchyCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	StandardDatasetObjectHierarchyItem item;
	public WriteDatasetObjectHierarchyCallback(StandardDatasetObjectHierarchyItem item) {
		this.item = item;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		Window.alert("Successfull Write");
		item.updateHierarchy(hierarchy);
	}

}
