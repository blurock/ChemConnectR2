package info.esblurock.reaction.chemconnect.core.client.concepts;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class ConceptHierarchyCallback implements AsyncCallback<HierarchyNode> {

	ChooseFromConceptHierarchies top;
	
	public ConceptHierarchyCallback(ChooseFromConceptHierarchies top) {
		super();
		this.top = top;
	}

	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error: \n" + arg0);
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		top.setupTree(hierarchy);
	}

}
