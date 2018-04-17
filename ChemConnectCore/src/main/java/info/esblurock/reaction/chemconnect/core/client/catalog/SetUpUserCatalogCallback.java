package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;

public class SetUpUserCatalogCallback implements AsyncCallback<TransferDatabaseCatalogHierarchy> {

	
	ManageCatalogHierarchy top;
	
	public SetUpUserCatalogCallback(ManageCatalogHierarchy top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(TransferDatabaseCatalogHierarchy transfer) {
		top.insertCatalog(transfer);
		
	}

}
