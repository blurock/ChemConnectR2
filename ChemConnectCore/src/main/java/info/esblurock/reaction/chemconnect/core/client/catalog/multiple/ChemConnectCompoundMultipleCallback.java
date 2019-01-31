package info.esblurock.reaction.chemconnect.core.client.catalog.multiple;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ChemConnectCompoundMultipleCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	CreateMultipleItemCallback multiple;
	public ChemConnectCompoundMultipleCallback(CreateMultipleItemCallback multiple) {
		this.multiple = multiple;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Catalog Object Read\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy obj) {
		multiple.addMultipleObject(obj);
		MaterialLoader.loading(false);
	}

}
