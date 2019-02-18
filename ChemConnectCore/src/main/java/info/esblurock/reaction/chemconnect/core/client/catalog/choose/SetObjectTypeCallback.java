package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;

public class SetObjectTypeCallback  implements AsyncCallback<String>  {

	ChooseFullNameFromCatagoryRow choose;
	
	public SetObjectTypeCallback(ChooseFullNameFromCatagoryRow choose) {
		this.choose = choose;
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable err) {
		Window.alert("ERROR: Set object type\n" + err.toString());
		MaterialLoader.loading(false);		
	}

	@Override
	public void onSuccess(String objectType) {
		choose.setObjectType(objectType);
		MaterialLoader.loading(false);		
	}

}
