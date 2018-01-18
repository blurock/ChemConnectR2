package info.esblurock.reaction.chemconnect.core.client.graph.pages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConceptHierarchyCallback  implements AsyncCallback<HierarchyNode> {
	VisualizeGraphicalObjects top;
	
	public ConceptHierarchyCallback(VisualizeGraphicalObjects top) {
		this.top = top;
	}

	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error: \n" + arg0);
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		top.addHierarchialModal(hierarchy);
	}

}
