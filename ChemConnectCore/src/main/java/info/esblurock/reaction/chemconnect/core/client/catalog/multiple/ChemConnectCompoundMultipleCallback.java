package info.esblurock.reaction.chemconnect.core.client.catalog.multiple;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ChemConnectCompoundMultipleCallback  implements AsyncCallback<DatabaseObjectHierarchy> {

	ChemConnectCompoundMultipleHeader multiple;
	public ChemConnectCompoundMultipleCallback(ChemConnectCompoundMultipleHeader multiple) {
		this.multiple = multiple;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy obj) {
		multiple.addMultipleObject(obj);
	}

}
