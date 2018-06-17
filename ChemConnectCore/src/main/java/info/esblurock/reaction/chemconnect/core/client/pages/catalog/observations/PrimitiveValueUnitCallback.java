package info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;

public class PrimitiveValueUnitCallback implements AsyncCallback<SetOfUnitProperties> {

	PrimitiveValueUnitCallInterface top;
	
	public PrimitiveValueUnitCallback(PrimitiveValueUnitCallInterface top) {
		this.top = top;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error: " + arg0.toString());
		
	}

	@Override
	public void onSuccess(SetOfUnitProperties set) {
		top.setUpUnitList(set);
	}

}
