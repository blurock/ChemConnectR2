package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;

public class PrimitiveValueUnitCallback implements AsyncCallback<SetOfUnitProperties> {

	PrimitiveValueUnitCallInterface top;
	
	public PrimitiveValueUnitCallback(PrimitiveValueUnitCallInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("Error: Primitive Value Units:\n " + arg0.toString());
		
	}

	@Override
	public void onSuccess(SetOfUnitProperties set) {
		MaterialLoader.loading(false);
		top.setUpUnitList(set);
	}

}
