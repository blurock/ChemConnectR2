package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

public class ParameterSetInfoCallback  implements AsyncCallback<ArrayList<PrimitiveParameterValueInformation>> {

	Map<String, PrimitiveDataStructureBase> structuremap;
	String id;
	
	public ParameterSetInfoCallback(String id, Map<String, PrimitiveDataStructureBase> structuremap) {
		this.structuremap = structuremap;
		this.id = id;
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
			PrimitiveParameterValue value = (PrimitiveParameterValue) base;
			info.setIdentifier(value.getIdentifier());
			base.fill(info);
		}
	}

}
