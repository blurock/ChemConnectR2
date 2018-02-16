package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.concepts.SetOfUnitProperties;

public class PrimitiveValueUnitCallback implements AsyncCallback<SetOfUnitProperties> {

	PrimitiveParameterValueRow top;
	
	public PrimitiveValueUnitCallback(PrimitiveParameterValueRow top) {
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
