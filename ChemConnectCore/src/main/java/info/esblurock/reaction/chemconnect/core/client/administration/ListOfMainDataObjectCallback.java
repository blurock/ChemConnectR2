package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureInstanceCollapsible;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;

public class ListOfMainDataObjectCallback implements AsyncCallback<SingleQueryResult> {

	MainDataStructureCollapsible main;
	
	public ListOfMainDataObjectCallback(MainDataStructureCollapsible main) {
		this.main = main;
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("ERROR: Query result\n" + arg0.toString());
		MaterialLoader.loading(false);
	}

	@Override
	public void onSuccess(SingleQueryResult result) {
		for(DatabaseObject obj : result.getResults()) {
			MainDataStructureInstanceCollapsible collapsible 
			= new MainDataStructureInstanceCollapsible(obj, main.getClsinfo(), main.getSubelements());
			main.addObjectCollapsible(collapsible);
		}
		MaterialLoader.loading(false);
	}

}
