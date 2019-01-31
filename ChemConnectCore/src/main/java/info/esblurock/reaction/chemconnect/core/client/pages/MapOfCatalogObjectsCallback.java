package info.esblurock.reaction.chemconnect.core.client.pages;

import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class MapOfCatalogObjectsCallback implements AsyncCallback<Map<String, DatabaseObject>> {
	
	public MapOfCatalogObjectsCallback() {
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		MaterialLoader.loading(false);
		}

	@Override
	public void onSuccess(Map<String, DatabaseObject> map) {
		MaterialLoader.loading(false);
		Set<String> keys = map.keySet();
		Window.alert("MapOfCatalogObjectsCallback\n" +  keys.toString());
		for(String key : keys) {
			DatabaseObject obj = map.get(key);
			Window.alert("MapOfCatalogObjectsCallback\n" + obj.toString());
		}
	}

}
