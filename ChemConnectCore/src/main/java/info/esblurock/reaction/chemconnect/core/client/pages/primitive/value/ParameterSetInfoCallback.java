package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;

public class ParameterSetInfoCallback  implements AsyncCallback<ArrayList<PrimitiveParameterValueInformation>> {

	Map<String, PrimitiveDataStructureBase> structuremap;
	DatabaseObject obj;
	
	public ParameterSetInfoCallback(DatabaseObject obj, Map<String, PrimitiveDataStructureBase> structuremap) {
		this.structuremap = structuremap;
		this.obj = obj;
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
			info.setIdentifier(obj.getIdentifier());
			info.setAccess(obj.getAccess());
			Window.alert("ParameterSetInfoCallback:  " + obj.getIdentifier());
			Window.alert("ParameterSetInfoCallback  " + info.toString());
			base.fill(info);
		}
	}

}
