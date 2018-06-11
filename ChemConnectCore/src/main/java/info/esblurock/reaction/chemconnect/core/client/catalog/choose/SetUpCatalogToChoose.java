package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetUpCatalogToChoose implements AsyncCallback<DatabaseObjectHierarchy> {

	
	ChooseCatalogHiearchyModal modal;
	
	public SetUpCatalogToChoose(ChooseCatalogHiearchyModal modal) {
		this.modal = modal;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		modal.setInHierarchy(hierarchy);
	}

}
