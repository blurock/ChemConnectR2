package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetUpDatabaseObjectHierarchyCallback implements AsyncCallback<DatabaseObjectHierarchy> {

	MaterialCollapsible panel;
	MaterialPanel modalpanel;
	public SetUpDatabaseObjectHierarchyCallback(MaterialCollapsible panel,MaterialPanel modalpanel) {
		this.panel = panel;
		this.modalpanel = modalpanel;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy transfer) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,transfer,modalpanel);		
		panel.add(item);
	}

}
