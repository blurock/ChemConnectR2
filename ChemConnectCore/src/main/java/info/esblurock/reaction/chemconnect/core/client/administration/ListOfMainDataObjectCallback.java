package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;

public class ListOfMainDataObjectCallback implements AsyncCallback<SingleQueryResult> {

	MainDataStructureCollapsible main;
	
	public ListOfMainDataObjectCallback(MainDataStructureCollapsible main) {
		this.main = main;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(SingleQueryResult result) {
		Window.alert("ListOfMainDataObjectCallback: " + result.getResults().size());
		for(DatabaseObject obj : result.getResults()) {
			Window.alert(obj.getIdentifier());
		}
	}

}
