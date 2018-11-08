package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class FindFileTypeCallback implements AsyncCallback<HierarchyNode> {

	UploadedElementCollapsible top;
	
	public FindFileTypeCallback(UploadedElementCollapsible top) {
		this.top = top;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		Window.alert("FindFileTypeCallback\n" + hierarchy);
		top.askForInterpretationType(hierarchy);
	}

}
