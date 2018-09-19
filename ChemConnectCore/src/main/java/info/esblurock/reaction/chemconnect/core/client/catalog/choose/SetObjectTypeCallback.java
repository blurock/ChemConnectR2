package info.esblurock.reaction.chemconnect.core.client.catalog.choose;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SetObjectTypeCallback  implements AsyncCallback<String>  {

	ChooseFullNameFromCatagoryRow choose;
	
	public SetObjectTypeCallback(ChooseFullNameFromCatagoryRow choose) {
		this.choose = choose;
	}
	
	@Override
	public void onFailure(Throwable err) {
		Window.alert(err.toString());
		
	}

	@Override
	public void onSuccess(String objectType) {
		choose.setObjectType(objectType);
		
	}

}
