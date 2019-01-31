package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class HierarchyNodeCallback implements AsyncCallback<HierarchyNode>{
	HierarchyNodeCallbackInterface top;
	public HierarchyNodeCallback(HierarchyNodeCallbackInterface top) {
		this.top = top;
		MaterialLoader.loading(false);	}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Hierarchy\n" + arg0.toString());
	}

	@Override
	public void onSuccess(HierarchyNode topnode) {
		MaterialLoader.loading(false);
		top.insertTree(topnode);
	}
}
