package info.esblurock.reaction.chemconnect.core.client.catalog;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class SetOfObjectsCallback implements AsyncCallback<ArrayList<DatabaseObjectHierarchy>> {

	SetOfObjectsCallbackInterface calling;
	
	public SetOfObjectsCallback(SetOfObjectsCallbackInterface calling) {
		this.calling = calling;
	}
	
	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<DatabaseObjectHierarchy> objects) {
		calling.setInOjbects(objects);
	}

}
