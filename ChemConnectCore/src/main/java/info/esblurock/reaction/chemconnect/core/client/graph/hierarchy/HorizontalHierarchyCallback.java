package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class HorizontalHierarchyCallback implements AsyncCallback<HierarchyNode> {

	HorizontalHierarchyPanel content;
	
	
	public HorizontalHierarchyCallback(HorizontalHierarchyPanel content) {
		this.content = content;
	}

	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error in Graph: " + arg0);
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		JSONObject root = ConvertToJSONObject.HierarchyNodeToJSON(hierarchy);
		
		HorizontalHierarchyInformation info = new HorizontalHierarchyInformation(root, 1100, 600);
		HorizontalHierarchy graph = new HorizontalHierarchy(info);
		content.setPanel(graph);
		graph.start();
	}

}
