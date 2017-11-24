package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureVisualization;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class CatalogClassificationInformationCallback implements AsyncCallback<HierarchyNode>{
	MainDataStructureVisualization window;
	public CatalogClassificationInformationCallback(MainDataStructureVisualization window) {
		this.window = window;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(HierarchyNode clslst) {
		window.addMainStructures(clslst);
	}
}
