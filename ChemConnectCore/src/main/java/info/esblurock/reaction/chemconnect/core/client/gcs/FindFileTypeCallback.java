package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class FindFileTypeCallback implements AsyncCallback<HierarchyNode> {

	UploadedElementCollapsible top;
	
	public FindFileTypeCallback(UploadedElementCollapsible top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: file type:\n" + arg0.toString());
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		MaterialLoader.loading(false);
		top.askForInterpretationType(hierarchy);
	}

}
