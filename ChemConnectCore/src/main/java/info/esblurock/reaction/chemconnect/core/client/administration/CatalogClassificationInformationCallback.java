package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureVisualization;
import info.esblurock.reaction.chemconnect.core.data.transfer.ClassificationInformation;

public class CatalogClassificationInformationCallback implements AsyncCallback<ArrayList<ClassificationInformation>>{
	MainDataStructureVisualization window;
	public CatalogClassificationInformationCallback(MainDataStructureVisualization window) {
		this.window = window;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		
	}

	@Override
	public void onSuccess(ArrayList<ClassificationInformation> clslst) {
		window.addMainStructures(clslst);
	}
}
