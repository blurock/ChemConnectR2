package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.Map;
import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

public class ParameterSetInfoCallback  implements AsyncCallback<ArrayList<PrimitiveParameterValueInformation>> {

	Map<String, PrimitiveDataStructureBase> structuremap;
	
	
	public ParameterSetInfoCallback(Map<String, PrimitiveDataStructureBase> structuremap) {
		Window.alert("ParameterSetInfoCallback");
		this.structuremap = structuremap;
	}

	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}
	
	@Override
	public void onSuccess(ArrayList<PrimitiveParameterValueInformation> set) {
		for(PrimitiveParameterValueInformation info : set) {
			String elementName = info.getPropertyType();
			PrimitiveDataStructureBase base = structuremap.get(elementName);
			PrimitiveParameterValue parametervalue = (PrimitiveParameterValue) base;
			base.fill(info);
		}
	}

}
