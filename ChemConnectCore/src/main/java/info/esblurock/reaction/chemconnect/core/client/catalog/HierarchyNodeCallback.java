package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class HierarchyNodeCallback implements AsyncCallback<HierarchyNode>{
	HierarchyNodeCallbackInterface top;
	public HierarchyNodeCallback(HierarchyNodeCallbackInterface top) {
		this.top = top;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(HierarchyNode topnode) {
		top.insertTree(topnode);
	}
}
